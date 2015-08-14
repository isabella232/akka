/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.scaladsl.server

import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport
import akka.http.scaladsl.model.ws.Message
import akka.http.scaladsl.server.directives.UserCredentials
import akka.stream.scaladsl.{ Keep, Source, Sink, Flow }
import com.typesafe.config.{ ConfigFactory, Config }
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http

import scala.concurrent.Promise

object TestServer extends App {
  val testConf: Config = ConfigFactory.parseString("""
    akka.loglevel = INFO
    akka.log-dead-letters = off""")
  implicit val system = ActorSystem("ServerTest", testConf)
  import system.dispatcher
  implicit val materializer = ActorMaterializer()

  import ScalaXmlSupport._
  import Directives._

  val pipePromise = Promise[Flow[Message, Message, Unit]]()

  def auth: AuthenticatorPF[String] = {
    case p @ UserCredentials.Provided(name) if p.verifySecret(name + "-password") ⇒ name
  }

  val bindingFuture = Http().bindAndHandle({
    get {
      path("") {
        complete(index)
      } ~
        path("secure") {
          authenticateBasicPF("My very secure site", auth) { user ⇒
            complete(<html><body>Hello <b>{ user }</b>. Access has been granted!</body></html>)
          }
        } ~
        path("ping") {
          complete("PONG!")
        } ~
        path("crash") {
          complete(sys.error("BOOM!"))
        } ~
        path("ws-relay-1") {
          val (first, second) = activeBidiPair[Message]()
          pipePromise.success(second)

          handleWebsocketMessages(first)
        } ~
        path("ws-relay-2") {
          onSuccess(pipePromise.future) { second ⇒
            handleWebsocketMessages(second)
          }
        }
    } ~ pathPrefix("inner")(getFromResourceDirectory("someDir"))
  }, interface = "localhost", port = 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  Console.readLine()

  bindingFuture.flatMap(_.unbind()).onComplete(_ ⇒ system.shutdown())

  lazy val index =
    <html>
      <body>
        <h1>Say hello to <i>akka-http-core</i>!</h1>
        <p>Defined resources:</p>
        <ul>
          <li><a href="/ping">/ping</a></li>
          <li><a href="/secure">/secure</a> Use any username and '&lt;username&gt;-password' as credentials</li>
          <li><a href="/crash">/crash</a></li>
        </ul>
      </body>
    </html>

  def activePipe[T](): (Sink[T, Unit], Source[T, Unit]) = {
    val (sub, pub) =
      Source.subscriber[T].toMat(Sink.publisher[T])(Keep.both).run()

    (Sink(sub), Source(pub))
  }
  def activeBidiPair[T](): (Flow[T, T, Unit], Flow[T, T, Unit]) = {
    val (inSink, inSource) = activePipe[T]()
    val (outSink, outSource) = activePipe[T]()

    // cross the links
    (Flow.wrap(inSink, outSource)(Keep.none),
      Flow.wrap(outSink, inSource)(Keep.none))
  }
}
