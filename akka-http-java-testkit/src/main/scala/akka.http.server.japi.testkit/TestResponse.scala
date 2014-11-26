/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import akka.http.util._
import akka.http.model.japi.JavaMapping.Implicits._

import akka.http.model.HttpResponse
import akka.http.model.japi.{ StatusCode, MediaType, HttpEntityStrict }
import akka.stream.FlowMaterializer

import scala.concurrent.duration.FiniteDuration

/**
 * A wrapper for responses
 */
abstract class TestResponse(response: HttpResponse, awaitAtMost: FiniteDuration)(implicit materializer: FlowMaterializer) {
  lazy val entity: HttpEntityStrict = {
    val originalEntity = response.entity
    originalEntity.toStrict(awaitAtMost).awaitResult(awaitAtMost)
  }

  // FIXME: add header getters / assertions

  def mediaType: MediaType = extractFromResponse(_.entity.contentType.mediaType)
  def mediaTypeString: String = mediaType.toString
  def entityAs[T](unmarshaller: Unmarshaller[T]): T =
    ???
  def entityAsString: String = entity.data().utf8String
  def status: StatusCode = response.status.asJava
  def statusCode: Int = response.status.intValue

  def assertStatusCode(expected: Int): Unit =
    ???
  def assertMediaType(expected: String): Unit =
    ???
  def assertMediaType(expected: MediaType): Unit =
    ???
  def assertEntity(expected: String): Unit =
    ???
  def assertEntityAs[T](unmarshaller: Unmarshaller[T], expected: T): Unit =
    ???

  private[this] def extractFromResponse[T](f: HttpResponse ⇒ T): T =
    if (response eq null) throw new IllegalStateException("Request didn't complete with response")
    else f(response)
}