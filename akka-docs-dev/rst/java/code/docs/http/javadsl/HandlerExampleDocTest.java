/*
 * Copyright (C) 2009-2015 Typesafe Inc. <http://www.typesafe.com>
 */

package docs.http.javadsl;

import akka.dispatch.Futures;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.server.*;
import akka.http.javadsl.server.values.Parameters;
import akka.http.javadsl.server.values.PathMatchers;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Test;

public class HandlerExampleDocTest extends JUnitRouteTest {
    @Test
    public void testSimpleHandler() {
        //#simple-handler-example-full
        class TestHandler extends akka.http.javadsl.server.AllDirectives {
            //#simple-handler
            Handler handler = new Handler() {
                @Override
                public RouteResult apply(RequestContext ctx) {
                    return ctx.complete("This was a " + ctx.request().method().value()  +
                            " request to "+ctx.request().getUri());
                }
            };
            //#simple-handler

            Route createRoute() {
                return route(
                    get(
                        handleWith(handler)
                    ),
                    post(
                        path("abc").route(
                            handleWith(handler)
                        )
                    )
                );
            }
        }

        // actual testing code
        TestRoute r = testRoute(new TestHandler().createRoute());
        r.run(HttpRequest.GET("/test"))
            .assertStatusCode(200)
            .assertEntity("This was a GET request to /test");

        r.run(HttpRequest.POST("/test"))
            .assertStatusCode(404);

        r.run(HttpRequest.POST("/abc"))
            .assertStatusCode(200)
            .assertEntity("This was a POST request to /abc");
        //#simple-handler-example-full
    }

    @Test
    public void testCalculator() {
        //#handler2-example-full
        class TestHandler extends akka.http.javadsl.server.AllDirectives {
            final RequestVal<Integer> xParam = Parameters.intValue("x");
            final RequestVal<Integer> yParam = Parameters.intValue("y");

            final RequestVal<Integer> xSegment = PathMatchers.integerNumber();
            final RequestVal<Integer> ySegment = PathMatchers.integerNumber();

            //#handler2
            final Handler2<Integer, Integer> multiply =
                new Handler2<Integer, Integer>() {
                    @Override
                    public RouteResult apply(RequestContext ctx, Integer x, Integer y) {
                        int result = x * y;
                        return ctx.complete("x * y = " + result);
                    }
                };

            final Route multiplyXAndYParam = handleWith2(xParam, yParam, multiply);
            //#handler2

            Route createRoute() {
                return route(
                    get(
                        pathPrefix("calculator").route(
                            path("multiply").route(
                                multiplyXAndYParam
                            ),
                            path("path-multiply", xSegment, ySegment).route(
                                handleWith2(xSegment, ySegment, multiply)
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

        r.run(HttpRequest.GET("/calculator/path-multiply/23/5"))
            .assertStatusCode(200)
            .assertEntity("x * y = 115");
        //#handler2-example-full
    }

    @Test
    public void testCalculatorReflective() {
        //#reflective-example-full
        class TestHandler extends akka.http.javadsl.server.AllDirectives {
            RequestVal<Integer> xParam = Parameters.intValue("x");
            RequestVal<Integer> yParam = Parameters.intValue("y");

            RequestVal<Integer> xSegment = PathMatchers.integerNumber();
            RequestVal<Integer> ySegment = PathMatchers.integerNumber();


            //#reflective
            public RouteResult multiply(RequestContext ctx, Integer x, Integer y) {
                int result = x * y;
                return ctx.complete("x * y = " + result);
            }

            Route multiplyXAndYParam = handleWith2(xParam, yParam, this::multiply);
            //#reflective

            Route createRoute() {
                return route(
                    get(
                        pathPrefix("calculator").route(
                            path("multiply").route(
                                multiplyXAndYParam
                            ),
                            path("path-multiply", xSegment, ySegment).route(
                                handleWith2(xSegment, ySegment, this::multiply)
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

        r.run(HttpRequest.GET("/calculator/path-multiply/23/5"))
            .assertStatusCode(200)
            .assertEntity("x * y = 115");
        //#reflective-example-full
    }
}
