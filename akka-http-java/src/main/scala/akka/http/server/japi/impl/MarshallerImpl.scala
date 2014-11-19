/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi
package impl

import akka.http.marshalling

import scala.concurrent.ExecutionContext

case class MarshallerImpl[T](scalaMarshaller: ExecutionContext ⇒ marshalling.ToResponseMarshaller[T]) extends Marshaller[T]
