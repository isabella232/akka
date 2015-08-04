/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.scaladsl

import com.typesafe.config.{ Config, ConfigFactory }
import scala.util._
import akka.actor.{ UnhandledMessage, ActorSystem }
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{ Sink, Source }
import akka.http.scaladsl.model._
import akka.http.impl.util._
import scala.concurrent._
import scala.concurrent.duration._

object TestClient extends App {
  val testConf: Config = ConfigFactory.parseString("""
    akka.loglevel = DEBUG
    akka.log-dead-letters = off
    akka.io.tcp.trace-logging = off""")
  implicit val system = ActorSystem("ServerTest", testConf)
  implicit val fm = ActorMaterializer()
  import system.dispatcher
  val host = "github.com"

  try {
    installEventStreamLoggerFor[UnhandledMessage]

    val request =
      HttpRequest(uri = "https://www.google.com:80/")
    def runOne(): Future[Try[HttpResponse]] =
      Source.single((request, ()))
        .via(Http(system).cachedHostConnectionPoolTls[Unit]("www.google.com", 80))
        .runWith(Sink.head).map(_._1)
    //Http().singleRequest(request).map(r ⇒ Try(r))

    Await.result(runOne(), 10.seconds)

    fetchServerVersion1()
  } catch {
    case e ⇒
      e.printStackTrace()
      shutdown()
  }

  //  Console.readLine()
  //  system.shutdown()

  def fetchServerVersion1(): Unit = {
    println(s"Fetching HTTPS server version of host `$host` via a direct low-level connection ...")

    val connection = Http().outgoingConnectionTls(host)
    val result = Source.single(HttpRequest()).via(connection).runWith(Sink.head)
    result.map(_.header[headers.Server]) onComplete {
      case Success(res) ⇒
        println(s"$host is running ${res mkString ", "}")
        println()
        fetchServerVersion2()

      case Failure(error) ⇒
        println(s"Error: $error")
        println()
        fetchServerVersion2()
    }
  }

  def fetchServerVersion2(): Unit = {
    println(s"Fetching HTTP server version of host `$host` via the high-level API ...")
    val result = Http().singleRequest(HttpRequest(uri = s"https://$host/"))
    result.map(_.header[headers.Server]) onComplete {
      case Success(res) ⇒
        println(s"$host is running ${res mkString ", "}")
        Http().shutdownAllConnectionPools().onComplete { _ ⇒ system.log.info("STOPPED"); shutdown() }

      case Failure(error) ⇒
        println(s"Error: $error")
        shutdown()
    }
  }

  def shutdown(): Unit = system.shutdown()
}