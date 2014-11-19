/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi
package impl

import akka.http.model.HttpMethod
import akka.http.server
import akka.http.server.{ PathMatchers, PathMatcher1 }

import scala.collection.immutable
import scala.concurrent.Future

trait WrappingRoute extends Route {
  def children: immutable.Seq[Route]

  require(children.nonEmpty)
}
case class MethodFilter(method: HttpMethod, children: immutable.Seq[Route]) extends WrappingRoute {
  def filter(ctx: RequestContext): Boolean = ctx.request.method == method
}
case class GetFromResource(resourcePath: String) extends Route
/*case class Parameters(params: immutable.Seq[ParameterImpl[_]], children: immutable.Seq[Route]) extends WrappingRoute {
  require(params.nonEmpty)
}*/
case class Path(pathElements: immutable.Seq[PathMatcherImpl[_]], children: immutable.Seq[Route]) extends WrappingRoute {
  require(pathElements.nonEmpty)
}
case class PathPrefix(pathElements: immutable.Seq[PathMatcherImpl[_]], children: immutable.Seq[Route]) extends WrappingRoute {
  def elements: immutable.Seq[PathMatcherImpl[_]] =
    if (pathElements.isEmpty) List(new PathMatcherImpl(PathMatchers.Neutral.tmap(_ ⇒ Tuple1(null)), isLast = true))
    else pathElements
}
case class RouteConcatenation(children: immutable.Seq[Route]) extends WrappingRoute
case class Extract(extractions: Seq[StandaloneExtractionImpl[_]], children: immutable.Seq[Route])()
  extends WrappingRoute

abstract class OpaqueRoute(extractions: RequestVal[_]*) extends Route {
  def handle(ctx: RequestContext): RouteResult
}