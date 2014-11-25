/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import akka.http.util._
import akka.http.model.japi.JavaMapping.Implicits._

import akka.http.model.HttpResponse
import akka.http.server.{ RouteResult, RoutingSettings, ScalaRoutingDSL }
import akka.http.server.japi.impl.RouteImplementation

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

import akka.actor.ActorSystem
import akka.event.NoLogging
import akka.http.server

import akka.http.model.japi.HttpRequest
import akka.stream.FlowMaterializer

abstract class RouteTest {
  implicit val system: ActorSystem = createSystem()
  implicit val materializer: FlowMaterializer = createMaterializer()
  implicit def executionContext: ExecutionContext = system.dispatcher

  protected def createSystem(): ActorSystem = ActorSystem()
  protected def createMaterializer(): FlowMaterializer = FlowMaterializer()(system)

  protected def awaitDuration: FiniteDuration = 500.millis

  def runRoute(request: HttpRequest, route: Route): TestResponse = {
    val scalaRoute = ScalaRoutingDSL.sealRoute(RouteImplementation(route))
    val result = scalaRoute(new server.RequestContextImpl(request.asScala, NoLogging, RoutingSettings(system)))

    result.awaitResult(awaitDuration) match {
      case RouteResult.Complete(response) ⇒ createTestResponse(response)
    }
  }

  protected def createTestResponse(response: HttpResponse): TestResponse
}
