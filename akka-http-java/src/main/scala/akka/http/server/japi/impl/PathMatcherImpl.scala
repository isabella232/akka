/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi
package impl

import akka.http.server.{ PathMatcher ⇒ ScalaPathMatcher, PathMatchers }

import scala.reflect.ClassTag

class PathMatcherImpl[T <: AnyRef: ClassTag](val underlying: ScalaPathMatcher[Tuple1[T]], isLast: Boolean = false) extends ExtractionImpl[T] with PathMatcher[T] {
  def matcher: ScalaPathMatcher[Tuple1[T]] =
    if (isLast) underlying else PathMatchers.Slash ~ underlying
}