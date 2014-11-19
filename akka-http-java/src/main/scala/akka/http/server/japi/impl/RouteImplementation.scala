/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi
package impl

import scala.annotation.tailrec
import scala.collection.immutable

import akka.http.model.{ HttpHeader, HttpRequest }
import akka.http.model.headers.CustomHeader
import akka.http.server.japi.Route
import akka.http.server.{ Route ⇒ ScalaRoute, Directive ⇒ ScalaDirective, _ }

import scala.concurrent.Future

trait ExtractionMap extends CustomHeader {
  def get[T](key: RequestVal[T]): Option[T]
  def set[T](key: RequestVal[T], value: T): ExtractionMap
}
object ExtractionMap {
  implicit def apply(map: Map[RequestVal[_], Any]): ExtractionMap =
    new ExtractionMap {
      def get[T](key: RequestVal[T]): Option[T] =
        map.get(key).asInstanceOf[Option[T]]

      def set[T](key: RequestVal[T], value: T): ExtractionMap =
        ExtractionMap(map.updated(key, value))

      // CustomHeader methods
      override def suppressRendering: Boolean = true
      def name(): String = "ExtractedValues"
      def value(): String = "<empty>"
    }
}

object RouteImplementation extends Directives {
  def apply(route: Route): ScalaRoute = route match {
    case RouteConcatenation(children) ⇒
      val converted = children.map(RouteImplementation.apply)
      converted.reduce(_ ~ _)
    case PathPrefix(elements, children) ⇒
      val inner = apply(RouteConcatenation(children))

      def one[T](matcher: PathMatcherImpl[T]): Directive0 =
        rawPathPrefix(matcher.matcher) flatMap { value ⇒
          addExtraction(matcher, value)
        }
      elements.map(one(_)).reduce(_ & _).apply(inner)

    case GetFromResource(path) ⇒ getFromResource(path)

    case MethodFilter(m, children) ⇒
      val inner = apply(RouteConcatenation(children))
      method(m).apply(inner)

    case Extract(extractions, children) ⇒
      val inner = apply(RouteConcatenation(children))
      extractRequestContext.flatMap { ctx ⇒
        extractions.map { e ⇒
          e.directive.flatMap(addExtraction(e.asInstanceOf[RequestVal[Any]], _))
        }.reduce(_ & _)
      }.apply(inner)

    case o: OpaqueRoute ⇒
      (ctx ⇒ o.handle(new RequestContextImpl(ctx)).asInstanceOf[RouteResultImpl].underlying)

    case p: Product ⇒ extractExecutionContext { implicit ec ⇒ complete(s"Not implemented: ${p.productPrefix}") }
  }

  def addExtraction[T](key: RequestVal[T], value: T): Directive0 = {
    @tailrec def addToExtractionMap(headers: immutable.Seq[HttpHeader], prefix: Vector[HttpHeader] = Vector.empty): immutable.Seq[HttpHeader] =
      headers match {
        case (m: ExtractionMap) +: rest ⇒ m.set(key, value) +: (prefix ++ rest)
        case other +: rest              ⇒ addToExtractionMap(rest, prefix :+ other)
        case Nil                        ⇒ ExtractionMap(Map(key -> value)) +: prefix
      }
    mapRequest(_.mapHeaders(addToExtractionMap(_)))
  }
}
