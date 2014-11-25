/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import akka.http.model.HttpResponse
import org.junit.Assert

/**
 * A RouteTest that uses JUnit assertions.
 */
abstract class JUnitRouteTest extends RouteTest {
  protected def createTestResponse(response: HttpResponse): TestResponse =
    new TestResponse(response, awaitDuration)(system.dispatcher, materializer) {
      protected def assertEquals(expected: AnyRef, actual: AnyRef, message: String): Unit =
        Assert.assertEquals(message, expected, actual)

      protected def assertEquals(expected: Int, actual: Int, message: String): Unit =
        Assert.assertEquals(message, expected, actual)
    }
}