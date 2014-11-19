/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

trait Handler {
  def handle(ctx: RequestContext): RouteResult
}
trait Handler1[T1] {
  def handle(ctx: RequestContext, t1: T1): RouteResult
}
trait Handler2[T1, T2] {
  def handle(ctx: RequestContext, t1: T1, t2: T2): RouteResult
}
trait Handler3[T1, T2, T3] {
  def handle(ctx: RequestContext, t1: T1, t2: T2, t3: T3): RouteResult
}
trait Handler4[T1, T2, T3, T4] {
  def handle(ctx: RequestContext, t1: T1, t2: T2, t3: T3, t4: T4): RouteResult
}