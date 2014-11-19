/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

trait PathMatcher[T] extends RequestVal[T]
object PathMatchers {
  def integer: PathMatcher[java.lang.Integer] = ???
  def segment(name: String): PathMatcher[String] = ???
}
