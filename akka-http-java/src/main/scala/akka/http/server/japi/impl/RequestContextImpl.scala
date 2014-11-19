/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi
package impl

import akka.http.model.japi.StatusCodes
import akka.http.model.japi.JavaMapping.Implicits._
import akka.http.model.{ StatusCode, japi, HttpRequest, Uri }
import akka.http.server.{ RequestContext ⇒ ScalaRequestContext }

final case class RequestContextImpl(underlying: ScalaRequestContext) extends RequestContext {
  // provides auto-conversion to japi.RouteResult
  import RouteResultImpl._

  def request: japi.HttpRequest = underlying.request
  def unmatchedPath: String = underlying.unmatchedPath.toString
  def complete(text: String): RouteResult = {
    import underlying.executionContext
    underlying.complete(text)
  }
  def completeWithStatus(statusCode: Int): RouteResult =
    completeWithStatus(StatusCodes.get(statusCode))
  def completeWithStatus(statusCode: japi.StatusCode): RouteResult = {
    import underlying.executionContext
    underlying.complete(statusCode.asScala)
  }
  def completeAs[T](marshaller: Marshaller[T], value: T): RouteResult = marshaller match {
    case MarshallerImpl(m) ⇒
      import underlying.executionContext
      implicit val marshaller = m(underlying.executionContext)
      underlying.complete(value)
    case _ ⇒ throw new IllegalArgumentException("Unsupported marshaller: $marshaller")
  }

  def notFound(): RouteResult = underlying.reject()
  //def reject(statusCode: Int): Future[RouteResult] = underlying.reject()
}
