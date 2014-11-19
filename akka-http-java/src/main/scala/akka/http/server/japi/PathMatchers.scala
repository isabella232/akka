/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import impl.PathMatcherImpl
import akka.http.server.{ PathMatchers ⇒ ScalaPathMatchers, PathMatcher1 }

trait PathMatcher[T] extends RequestVal[T]
object PathMatchers {
  def integer: PathMatcher[java.lang.Integer] = new PathMatcherImpl(ScalaPathMatchers.IntNumber.asInstanceOf[PathMatcher1[java.lang.Integer]])
  def segment(name: String): PathMatcher[String] = new PathMatcherImpl(name -> name)
}
