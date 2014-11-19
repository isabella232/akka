/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import scala.concurrent.duration._

import akka.actor.ActorSystem
import akka.http.util.FastFuture._
import akka.http.model.{ HttpResponse, HttpRequest }
import akka.http.server.{ ScalaRoutingDSL, RouteResult, RequestContext ⇒ ScalaRequestContext, Route ⇒ ScalaRoute }
import akka.pattern.ask
import akka.io.IO
import akka.http.Http
import akka.http.server.japi.impl.{ RequestContextImpl, RouteImplementation }
import akka.stream.FlowMaterializer
import akka.stream.scaladsl.{ Sink, Source }
import akka.util.Timeout

import scala.concurrent.Future

object HttpApp {
  def bind(_system: ActorSystem, interface: String, port: Int, route: Route): Future[Unit] = {
    implicit val system = _system
    implicit val materializer = FlowMaterializer()
    import system.dispatcher

    implicit val askTimeout: Timeout = 1000.millis

    val binding = (IO(Http) ? Http.Bind(interface, port)).mapTo[Http.ServerBinding]

    val impl = RouteImplementation(route)
    ScalaRoutingDSL.handleConnections(binding).withRoute(impl)
  }
}
