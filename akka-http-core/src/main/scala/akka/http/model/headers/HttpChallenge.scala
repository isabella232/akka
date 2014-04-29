/**
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.model
package headers

import akka.http.util._

case class HttpChallenge(scheme: String, realm: String,
                         parameters: Map[String, String] = Map.empty) extends ValueRenderable with japi.headers.HttpChallenge {

  def render[R <: Rendering](r: R): r.type = {
    r ~~ scheme ~~ " realm=" ~~# realm
    if (parameters.nonEmpty) parameters.foreach { case (k, v) ⇒ r ~~ ',' ~~ k ~~ '=' ~~# v }
    r
  }
}
