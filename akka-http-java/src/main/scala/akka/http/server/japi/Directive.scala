/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import scala.collection.immutable
import scala.annotation.varargs

/**
 * A route wrapper that when given inner routes produces a route itself.
 */
class Directive private (f: immutable.Seq[Route] ⇒ Route) {
  // FIXME: This should be a trait actually, but that's not possible because of SI-1459.

  @varargs
  def route(innerRoutes: Route*): Route = f(innerRoutes.toVector)
}

object Directive {
  def apply(f: immutable.Seq[Route] ⇒ Route): Directive = new Directive(f)
}