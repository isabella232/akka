/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import scala.reflect.ClassTag

trait Unmarshaller[T] {
  def classTag: ClassTag[T]
}
