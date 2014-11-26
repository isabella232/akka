/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import akka.http.model.HttpResponse

import scala.concurrent.duration._

import akka.actor.ActorSystem

import akka.http.model.japi.HttpRequest
import akka.stream.FlowMaterializer

abstract class RouteTest {
  protected def createSystem(): ActorSystem =
    ???
  protected def createMaterializer(): FlowMaterializer =
    ???

  implicit val system: ActorSystem = createSystem()
  implicit val materializer: FlowMaterializer = createMaterializer()

  protected def awaitDuration: FiniteDuration = 500.millis

  def runRoute(request: HttpRequest, route: Route): TestResponse =
    ???

  protected def createTestResponse(response: HttpResponse): TestResponse
}
