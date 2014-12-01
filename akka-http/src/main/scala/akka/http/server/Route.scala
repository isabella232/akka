/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server

import scala.concurrent.{ ExecutionContext, Future }
import akka.stream.scaladsl._
import akka.stream.FlowMaterializer
import akka.http.util.FastFuture
import akka.http.model.{ HttpRequest, HttpResponse }
import akka.http.Http
import FastFuture._

trait Routing {
  def runner(route: Route, setup: RoutingSetup): HttpRequest ⇒ Future[HttpResponse] = {
    import setup._
    val sealedRoute = sealRoute(route)(setup)
    request ⇒
      sealedRoute(new RequestContextImpl(request, routingLog.requestLog(request), setup.settings)).fast.map {
        case RouteResult.Complete(response) ⇒ response
        case RouteResult.Rejected(rejected) ⇒ throw new IllegalStateException(s"Unhandled rejections '$rejected', unsealed RejectionHandler?!")
      }
  }

  /**
   * "Seals" a route by wrapping it with exception handling and rejection conversion.
   */
  def sealRoute(route: Route)(implicit setup: RoutingSetup): Route = {
    import directives.ExecutionDirectives._
    import setup._
    val sealedExceptionHandler =
      if (exceptionHandler.isDefault) exceptionHandler
      else exceptionHandler orElse ExceptionHandler.default(settings)
    val sealedRejectionHandler =
      if (rejectionHandler.isDefault) rejectionHandler
      else rejectionHandler orElse RejectionHandler.default
    handleExceptions(sealedExceptionHandler) {
      handleRejections(sealedRejectionHandler) {
        route
      }
    }
  }
}
object Routing extends Routing