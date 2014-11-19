/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import scala.concurrent.Future

import akka.actor.ActorSystem

object HttpApp {
  def bind(_system: ActorSystem, interface: String, port: Int, route: Route): Future[Unit] = ???
}
