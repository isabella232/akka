/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import akka.http.model.HttpResponse

/**
 * A RouteTest that uses JUnit assertions.
 */
abstract class JUnitRouteTest extends RouteTest {
  protected def createTestResponse(response: HttpResponse): TestResponse =
    ???
}