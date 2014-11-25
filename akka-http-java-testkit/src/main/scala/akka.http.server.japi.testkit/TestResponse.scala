/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration

import akka.stream.FlowMaterializer

import akka.http.util._
import akka.http.model.japi.JavaMapping.Implicits._

import akka.http.server.japi.impl.UnmarshallerImpl
import akka.http.unmarshalling.Unmarshal

import akka.http.model.HttpResponse
import akka.http.model.japi.{ StatusCodes, StatusCode, MediaType, HttpEntityStrict }

/**
 * A wrapper for responses
 */
abstract class TestResponse(_response: HttpResponse, awaitAtMost: FiniteDuration)(implicit ec: ExecutionContext, materializer: FlowMaterializer) {
  lazy val entity: HttpEntityStrict =
    _response.entity.toStrict(awaitAtMost).awaitResult(awaitAtMost)
  lazy val response: HttpResponse = _response.withEntity(entity)

  // FIXME: add header getters / assertions

  def mediaType: MediaType = extractFromResponse(_.entity.contentType.mediaType)
  def mediaTypeString: String = mediaType.toString
  def entityAs[T](unmarshaller: Unmarshaller[T]): T =
    Unmarshal(response)
      .to(unmarshaller.asInstanceOf[UnmarshallerImpl[T]].scalaUnmarshaller(ec, materializer))
      .awaitResult(awaitAtMost)
  def entityAsString: String = entity.data().utf8String
  def status: StatusCode = response.status.asJava
  def statusCode: Int = response.status.intValue

  def assertStatusCode(expected: Int): Unit =
    assertStatusCode(StatusCodes.get(expected))
  def assertStatusCode(expected: StatusCode): Unit =
    assertEqualsKind(expected, status, "status code")
  def assertMediaType(expected: String): Unit =
    assertEqualsKind(expected, mediaTypeString, "media type")
  def assertMediaType(expected: MediaType): Unit =
    assertEqualsKind(expected, mediaType, "media type")
  def assertEntity(expected: String): Unit =
    assertEqualsKind(expected, entityAsString, "entity")
  def assertEntityAs[T <: AnyRef](unmarshaller: Unmarshaller[T], expected: T): Unit =
    assertEqualsKind(expected, entityAs(unmarshaller), "entity")

  private[this] def extractFromResponse[T](f: HttpResponse ⇒ T): T =
    if (response eq null) throw new IllegalStateException("Request didn't complete with response")
    else f(response)

  protected def assertEqualsKind(expected: AnyRef, actual: AnyRef, kind: String): Unit =
    assertEquals(expected, actual, s"Unexpected $kind!")
  protected def assertEqualsKind(expected: Int, actual: Int, kind: String): Unit =
    assertEquals(expected, actual, s"Unexpected $kind!")

  protected def assertEquals(expected: AnyRef, actual: AnyRef, message: String): Unit
  protected def assertEquals(expected: Int, actual: Int, message: String): Unit
}