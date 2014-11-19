/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import akka.http.server.Directive1
import akka.http.server.directives.ParameterDirectives.ParamMagnet
import akka.http.common.ToNameReceptacleEnhancements
import akka.http.server.japi.impl.ParameterImpl

trait Parameter[T] extends RequestVal[T]
object Parameters {
  import ToNameReceptacleEnhancements._
  def integer(name: String): Parameter[java.lang.Integer] =
    new ParameterImpl[Integer](implicit ec ⇒ ParamMagnet(name.as[Int]).asInstanceOf[ParamMagnet { type Out = Directive1[Integer] }]).asInstanceOf[Parameter[Integer]]
}

