/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

/** Represents a value that can be extracted from a request */
trait RequestVal[T] { outer ⇒
  def get(ctx: RequestContext): T
  def resultClass: Class[T]
}
