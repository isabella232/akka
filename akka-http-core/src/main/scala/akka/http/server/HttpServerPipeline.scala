/**
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server

import org.reactivestreams.api.{ Consumer, Producer }
import scala.concurrent.ExecutionContext
import akka.event.LoggingAdapter
import akka.util.ByteString
import akka.stream.io.StreamTcp
import akka.stream.{ FlattenStrategy, Transformer, FlowMaterializer }
import akka.stream.scaladsl.{ Flow, Duct }
import akka.http.parsing.HttpRequestParser
import akka.http.rendering.{ ResponseRenderingContext, HttpResponseRendererFactory }
import akka.http.model.{ StatusCode, ErrorInfo, HttpRequest, HttpResponse }
import akka.http.parsing.ParserOutput._
import akka.http.Http
import scala.collection.immutable.Seq
import java.util.concurrent.atomic.{ AtomicInteger, AtomicReference }
import org.reactivestreams.spi.{ Subscriber, Subscription }

private[http] class HttpServerPipeline(settings: ServerSettings,
                                       materializer: FlowMaterializer,
                                       log: LoggingAdapter)(implicit ec: ExecutionContext)
  extends (StreamTcp.IncomingTcpConnection ⇒ Http.IncomingConnection) {
  import HttpServerPipeline._

  val rootParser = new HttpRequestParser(settings.parserSettings, settings.rawRequestUriHeader, materializer)()
  val warnOnIllegalHeader: ErrorInfo ⇒ Unit = errorInfo ⇒
    if (settings.parserSettings.illegalHeaderWarnings)
      log.warning(errorInfo.withSummaryPrepended("Illegal request header").formatPretty)

  val responseRendererFactory = new HttpResponseRendererFactory(settings.serverHeader, settings.chunklessStreaming,
    settings.responseHeaderSizeHint, materializer, log)

  def apply(tcpConn: StreamTcp.IncomingTcpConnection): Http.IncomingConnection = {
    val (applicationBypassConsumer, applicationBypassProducer) =
      Duct[(RequestOutput, Producer[RequestOutput])]
        .collect[MessageStart with RequestOutput] { case (x: MessageStart, _) ⇒ x }
        .build(materializer)

    import akka.stream.extra.Implicits._
    implicit val x = materializer

    val requestProducer =
      Flow(tcpConn.inputStream)
        .onEvent(println)
        .transform(rootParser.copyWith(warnOnIllegalHeader))
        .onEvent(println)
        .splitWhen(_.isInstanceOf[MessageStart])
        .headAndTail(materializer)
        .tee(applicationBypassConsumer)
        .collect { case (x: RequestStart, entityParts) ⇒ HttpServerPipeline.constructRequest(x, entityParts) }
        .toProducer(materializer)

    val responseConsumer =
      Duct[HttpResponse]
        .merge(applicationBypassProducer)
        .transform(applyApplicationBypass)
        .transform(responseRendererFactory.newRenderer)
        .flatten(FlattenStrategy.Concat())
        .transform {
          new Transformer[ByteString, ByteString] {
            def onNext(element: ByteString) = element :: Nil
            override def onError(cause: Throwable): Unit = log.error(cause, "Response stream error")
          }
        }.produceTo(materializer, tcpConn.outputStream)

    Http.IncomingConnection(tcpConn.remoteAddress, requestProducer, responseConsumer)
  }

  /**
   * Combines the HttpResponse coming in from the application with the ParserOutput.RequestStart
   * produced by the request parser into a ResponseRenderingContext.
   * If the parser produced a ParserOutput.ParseError the error response is immediately dispatched to downstream.
   */
  def applyApplicationBypass =
    new Transformer[Any, ResponseRenderingContext] {
      var applicationResponse: HttpResponse = _
      var requestStart: RequestStart = _

      def onNext(elem: Any) = elem match {
        case response: HttpResponse ⇒
          requestStart match {
            case null ⇒
              applicationResponse = response
              Nil
            case x: RequestStart ⇒
              requestStart = null
              dispatch(x, response)
          }

        case requestStart: RequestStart ⇒
          applicationResponse match {
            case null ⇒
              this.requestStart = requestStart
              Nil
            case response ⇒
              applicationResponse = null
              dispatch(requestStart, response)
          }

        case ParseError(status, info) ⇒ errorResponse(status, info) :: Nil
      }

      def dispatch(requestStart: RequestStart, response: HttpResponse): List[ResponseRenderingContext] = {
        import requestStart._
        ResponseRenderingContext(response, method, protocol, closeAfterResponseCompletion) :: Nil
      }

      def errorResponse(status: StatusCode, info: ErrorInfo): ResponseRenderingContext = {
        log.warning("Illegal request, responding with status '{}': {}", status, info.formatPretty)
        val msg = if (settings.verboseErrorMessages) info.formatPretty else info.summary
        ResponseRenderingContext(HttpResponse(status, msg), closeAfterResponseCompletion = true)
      }
    }
}

private[http] object HttpServerPipeline {
  def constructRequest(requestStart: RequestStart, entityParts: Producer[RequestOutput]): HttpRequest = {
    import requestStart._
    HttpRequest(method, uri, headers, createEntity(entityParts), protocol)
  }

  implicit class FlowWithHeadAndTail[T](val underlying: Flow[Producer[T]]) {
    def headAndTail(materializer: FlowMaterializer): Flow[(T, Producer[T])] = {
      underlying.map(p ⇒ Flow(p).prefixAndTail(1).map(t ⇒ (t._1.head, t._2))
        .toProducer(materializer)).flatten(FlattenStrategy.Concat())
    }
  }

  implicit class FlowWithOnEvent[T](val underlying: Flow[T])(implicit materializer: FlowMaterializer) {
    import collection.immutable
    def onEvent(f: StreamEvent[T] ⇒ Unit): Flow[T] = {

      val trafo = new Transformer[T, Nothing] {
        def onNext(element: T): immutable.Seq[Nothing] = { f(OnNext(element)); Nil }

        override def onTermination(e: Option[Throwable]): immutable.Seq[Nothing] = { f(OnComplete); Nil }

        override def onError(cause: scala.Throwable): Unit = f(OnError(cause))
      }

      underlying.tee(Duct[T].transform(trafo).consume(materializer))
    }

  }
}

sealed trait StreamEvent[+T]
final case class OnNext[T](value: T) extends StreamEvent[T]
case object OnComplete extends StreamEvent[Nothing]
final case class OnError(cause: Throwable) extends StreamEvent[Nothing]
