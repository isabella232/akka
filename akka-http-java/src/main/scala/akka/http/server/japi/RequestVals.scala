/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import java.{ util ⇒ ju }

object RequestVals {
  def lookupInMap[T, U](key: RequestVal[T], clazz: Class[U], map: ju.Map[T, U]): RequestVal[U] =
    ???

  /**
   * Creates an extraction that extracts the request body using the supplied Unmarshaller.
   */
  def entityAs[T](unmarshaller: Unmarshaller[T]): RequestVal[T] =
    ???
}
