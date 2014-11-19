/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import java.{ util ⇒ ju }

import akka.http.server
import akka.http.server._
import akka.http.server.directives.{ RouteDirectives, BasicDirectives }
import akka.http.server.japi.impl.{ UnmarshallerImpl, ExtractingStandaloneExtractionImpl, RequestContextImpl, StandaloneExtractionImpl }

import scala.concurrent.Future
import scala.reflect.ClassTag

object RequestVals {
  def lookupInMap[T, U](key: RequestVal[T], clazz: Class[U], map: ju.Map[T, U]): RequestVal[U] =
    new StandaloneExtractionImpl[U]()(ClassTag(clazz)) {
      import BasicDirectives._
      import RouteDirectives._

      def directive: Directive1[U] =
        extract(ctx ⇒ key.get(RequestContextImpl(ctx))).flatMap {
          case key if map.containsKey(key) ⇒ provide(map.get(key))
          case _                           ⇒ reject()
        }
    }

  /**
   * Creates an extraction that extracts the request body using the supplied Unmarshaller.
   */
  def entityAs[T](unmarshaller: Unmarshaller[T]): RequestVal[T] =
    new ExtractingStandaloneExtractionImpl[T]()(unmarshaller.classTag) {
      def extract(ctx: server.RequestContext): Future[T] = {
        val u = unmarshaller.asInstanceOf[UnmarshallerImpl[T]].scalaUnmarshaller(ctx.executionContext, ctx.flowMaterializer)
        u(ctx.request)
      }
    }
}
