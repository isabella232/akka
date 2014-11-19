/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

trait Parameter[T] extends RequestVal[T]
object Parameters {
  def integer(name: String): Parameter[java.lang.Integer] = ???
}

