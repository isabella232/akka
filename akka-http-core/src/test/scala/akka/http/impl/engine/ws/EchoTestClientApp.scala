/*
 * Copyright (C) 2009-2015 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.impl.engine.ws

import scala.concurrent.duration._

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{ TextMessage, BinaryMessage, Message }
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.Future
import scala.util.{ Failure, Success }

/**
 * An example App that runs a quick test against the websocket server at wss://echo.websocket.org
 */
object EchoTestClientApp extends App {
  implicit val system = ActorSystem()
  import system.dispatcher
  implicit val mat = ActorMaterializer()

  def delayedCompletion[T]: Source[Nothing, Unit] =
    Source.single(1)
      .mapAsync(1)(_ ⇒ akka.pattern.after(1.second, system.scheduler)(Future(1)))
      .drop(1).asInstanceOf[Source[Nothing, Unit]]

  def messages: List[Message] =
    List(
      TextMessage("Test 1"),
      BinaryMessage(ByteString("abc")),
      TextMessage("Test 2"),
      BinaryMessage(ByteString("def")))

  def source: Source[Message, Unit] =
    // FIXME: fails sometimes without the delay because server doesn't allow the first message
    // before the handshake was accepted
    delayedCompletion[Message] ++
      Source(messages)

  def sink: Sink[Message, Future[Seq[String]]] =
    Flow[Message]
      .mapAsync(1) {
        case tm: TextMessage ⇒
          tm.textStream.runWith(Sink.fold("")(_ + _)).map(str ⇒ s"TextMessage: '$str'")
        case bm: BinaryMessage ⇒
          bm.dataStream.runWith(Sink.fold(ByteString.empty)(_ ++ _)).map(bs ⇒ s"BinaryMessage: '${bs.utf8String}'")
      }
      .grouped(10000)
      .toMat(Sink.head)(Keep.right)

  def echoClient =
    Flow.wrap(sink, source)(Keep.left)

  val result = Http().singleWebsocketRequest("ws://echo.websocket.org", echoClient)
  result onComplete {
    case Success(res) ⇒
      println("Run successful. Got these elements:")
      res.foreach(println)
      system.shutdown()
    case Failure(e) ⇒
      println("Run failed.")
      e.printStackTrace()
      system.shutdown()
  }

  system.scheduler.scheduleOnce(10.seconds)(system.shutdown())
}
