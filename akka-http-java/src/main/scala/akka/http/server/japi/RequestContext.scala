/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import akka.http.model.japi.{ StatusCode, Uri, HttpRequest }

trait RequestContext {
  def request: HttpRequest
  def unmatchedPath: String

  def completeAs[T](marshaller: Marshaller[T], value: T): RouteResult
  def complete(text: String): RouteResult
  def completeWithStatus(statusCode: StatusCode): RouteResult
  def completeWithStatus(statusCode: Int): RouteResult

  def notFound(): RouteResult
}