/*
 * Copyright (C) 2009-2015 Typesafe Inc. <http://www.typesafe.com>
 */

package docs.http.javadsl.server;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.server.*;
import akka.http.javadsl.server.values.Parameters;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Test;

public class HandlerExampleTest extends JUnitRouteTest {
    @Test
    public void testCalculator() {
        //#handler2-example-full
        class TestHandler extends akka.http.javadsl.server.AllDirectives {
            final RequestVal<Integer> xParam = Parameters.intValue("x");
            final RequestVal<Integer> yParam = Parameters.intValue("y");

            //#handler2
            final Handler2<Integer, Integer> multiply =
                (ctx, x, y) -> ctx.complete("x * y = " + (x * y));

            final Route multiplyXAndYParam = handleWith2(xParam, yParam, multiply);
            //#handler2

            RouteResult subtract(RequestContext ctx, int x, int y) {
                return ctx.complete("x - y = " + (x - y));
            }

            Route createRoute() {
                return route(
                    get(
                        pathPrefix("calculator").route(
                            path("multiply").route(
                                // use Handler explicitly
                                multiplyXAndYParam
                            ),
                            path("add").route(
                                // create Handler as lambda expression
                                handleWith2(xParam, yParam,
                                    (ctx, x, y) -> ctx.complete("x + y = " + (x + y)))
                            ),
                            path("subtract").route(
                                // create handler by lifting method
                                handleWith2(xParam, yParam, this::subtract)
                            )
                        )
                    )
                );
            }
        }

        // actual testing code
        TestRoute r = testRoute(new TestHandler().createRoute());
        r.run(HttpRequest.GET("/calculator/multiply?x=12&y=42"))
            .assertStatusCode(200)
            .assertEntity("x * y = 504");

        r.run(HttpRequest.GET("/calculator/add?x=12&y=42"))
            .assertStatusCode(200)
            .assertEntity("x + y = 54");

        r.run(HttpRequest.GET("/calculator/subtract?x=42&y=12"))
            .assertStatusCode(200)
            .assertEntity("x - y = 30");
        //#handler2-example-full
    }
}