/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi
package impl

import akka.http.server

import scala.concurrent.Future

class RouteResultImpl(val underlying: Future[server.RouteResult]) extends RouteResult
object RouteResultImpl {
  implicit def autoConvert(result: Future[server.RouteResult]): RouteResult =
    new RouteResultImpl(result)
}