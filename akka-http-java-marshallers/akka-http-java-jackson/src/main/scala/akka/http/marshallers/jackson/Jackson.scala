/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.marshallers.jackson

import akka.http.server.japi.{ Unmarshaller, Marshaller }

object Jackson {
  def json[T <: AnyRef]: Marshaller[T] = ???
  def jsonAs[T](clazz: Class[T]): Unmarshaller[T] = ???
}
