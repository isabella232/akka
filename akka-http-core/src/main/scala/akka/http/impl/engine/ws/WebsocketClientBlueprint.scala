/*
 * Copyright (C) 2009-2015 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.impl.engine.ws

import java.util.Random

import akka.http.impl.engine.parsing.HttpMessageParser.StateResult
import akka.http.impl.engine.parsing.ParserOutput.{ RemainingBytes, ResponseStart, NeedMoreData }
import akka.http.impl.engine.parsing.{ ParserOutput, HttpHeaderParser, HttpResponseParser }
import akka.http.impl.engine.rendering.{ HttpRequestRendererFactory, RequestRenderingContext }
import akka.http.impl.engine.ws.Handshake.Client.NegotiatedWebsocketSettings
import akka.http.scaladsl.model.headers.Host
import akka.stream.stage._
import akka.util.ByteString
import akka.event.LoggingAdapter

import scala.concurrent.Promise

import akka.stream.BidiShape
import akka.stream.io.{ SessionBytes, SendBytes, SslTlsInbound }
import akka.stream.scaladsl._

import akka.http.ClientConnectionSettings
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ HttpResponse, HttpMethods, Uri }

trait OneTimeValve {
  def source[T]: Source[T, Unit]
  def open(): Unit
}
object OneTimeValve {
  def apply(): OneTimeValve = new OneTimeValve {
    val promise = Promise[Unit]()
    val _source = Source(promise.future).drop(1) // we are only interested in the completion event

    def source[T]: Source[T, Unit] = _source.asInstanceOf[Source[T, Unit]] // safe, because source won't generate any elements
    def open(): Unit = promise.success(())
  }
}

object WebsocketClientBlueprint {
  def apply(uri: Uri,
            settings: ClientConnectionSettings,
            random: Random,
            log: LoggingAdapter): Http.WebsocketClientLayer =
    (simpleTls atop
      handshake(uri, settings, random, log) atop
      Websocket.framing atop
      Websocket.stack(serverSide = false)).reversed

  /** A bidi flow that injects and inspects the WS handshake and then goes out of the way */
  def handshake(uri: Uri,
                settings: ClientConnectionSettings,
                random: Random,
                log: LoggingAdapter): BidiFlow[ByteString, ByteString, ByteString, ByteString, Unit] = {
    val valve = OneTimeValve()

    val (initialRequest, key) = Handshake.Client.buildRequest(uri, Nil, random)
    val hostHeader = Host(uri.authority)
    val renderedInitialRequest =
      HttpRequestRendererFactory.renderStrict(RequestRenderingContext(initialRequest, hostHeader), settings, log)

    class UpgradeStage extends StatefulStage[ByteString, ByteString] {
      type State = StageState[ByteString, ByteString]

      def initial: State = parsingResponse

      // a special version of the parser which only parses one message and then reports the remaining data
      // if some is available
      val parser = new HttpResponseParser(settings.parserSettings, HttpHeaderParser(settings.parserSettings)()) {
        var first = true
        override protected def parseMessage(input: ByteString, offset: Int): StateResult = {
          if (first) {
            first = false
            super.parseMessage(input, offset)
          } else {
            emit(RemainingBytes(input.drop(offset)))
            terminate()
          }
        }
      }
      parser.setRequestMethodForNextResponse(HttpMethods.GET)

      def parsingResponse: State = new State {
        def onPush(elem: ByteString, ctx: Context[ByteString]): SyncDirective = {
          parser.onPush(elem) match {
            case NeedMoreData ⇒ ctx.pull()
            case ResponseStart(status, protocol, headers, entity, close) ⇒
              val response = HttpResponse(status, headers, protocol = protocol)
              Handshake.Client.validateResponse(response, key) match {
                case Some(NegotiatedWebsocketSettings(protocol)) ⇒
              }

              become(transparent)
              valve.open()

              val parseResult = parser.onPull()
              require(parseResult == ParserOutput.MessageEnd, s"parseResult should be MessageEnd but was $parseResult")
              parser.onPull() match {
                case NeedMoreData          ⇒ ctx.pull()
                case RemainingBytes(bytes) ⇒ ctx.push(bytes)
              }
          }
        }
      }

      def transparent: State = new State {
        def onPush(elem: ByteString, ctx: Context[ByteString]): SyncDirective = ctx.push(elem)
      }
    }

    BidiFlow() { implicit b ⇒
      import FlowGraph.Implicits._

      val networkIn = b.add(Flow[ByteString].transform(() ⇒ new UpgradeStage))
      val wsIn = b.add(Flow[ByteString])

      val handshakeRequestSource = b.add(Source.single(renderedInitialRequest) ++ valve.source /* FIXME: ++ valve to delay ws frames*/ )
      val httpRequestBytesAndThenWSBytes = b.add(Concat[ByteString]())

      handshakeRequestSource ~> httpRequestBytesAndThenWSBytes
      wsIn.outlet ~> httpRequestBytesAndThenWSBytes

      BidiShape(
        networkIn.inlet,
        networkIn.outlet, // FIXME: actually check handshake before relaying
        wsIn.inlet,
        httpRequestBytesAndThenWSBytes.out)
    }
  }

  def simpleTls: BidiFlow[SslTlsInbound, ByteString, ByteString, SendBytes, Unit] =
    BidiFlow.wrap(
      Flow[SslTlsInbound].collect { case SessionBytes(_, bytes) ⇒ bytes },
      Flow[ByteString].map(SendBytes))(Keep.none)
}
