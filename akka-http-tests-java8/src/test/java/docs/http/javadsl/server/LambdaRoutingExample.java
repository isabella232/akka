/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package docs.http.javadsl.server;

import akka.dispatch.Futures;
import akka.http.javadsl.server.*;
import akka.http.javadsl.server.values.Parameter;
import akka.http.javadsl.server.values.Parameters;
import akka.http.javadsl.server.values.PathMatcher;
import akka.http.javadsl.server.values.PathMatchers;
import scala.concurrent.Future;

public class LambdaRoutingExample extends HttpApp {
    static Parameter<Integer> x = Parameters.intValue("x");
    static Parameter<Integer> y = Parameters.intValue("y");

    static PathMatcher<Integer> xSegment = PathMatchers.intValue();
    static PathMatcher<Integer> ySegment = PathMatchers.intValue();

    public static RouteResult multiply(RequestContext ctx, int x, int y) {
        int result = x * y;
        return ctx.complete(String.format("%d * %d = %d", x, y, result));
    }

    public static Future<RouteResult> multiplyAsync(final RequestContext ctx, final int x, final int y) {
        return Futures.future(() -> multiply(ctx, x, y), ctx.executionContext());
    }

    @Override
    public Route createRoute() {
        return
            route(
                // matches the empty path
                pathSingleSlash().route(
                    getFromResource("web/calculator.html")
                ),
                // matches paths like this: /add?x=42&y=23
                path("add").route(
                    handleWith2(x, y, LambdaRoutingExample::add)
                ),
                path("subtract").route(
                    handleWith2(x, y, this::subtract)
                ),
                // matches paths like this: /multiply/{x}/{y}
                path("multiply", xSegment, ySegment).route(
                    // bind handler by reflection
                    handleWith2(xSegment, ySegment, LambdaRoutingExample::multiply)
                ),
                path("multiplyAsync", xSegment, ySegment).route(
                    // bind async handler
                    handleWithAsync2(xSegment, ySegment, LambdaRoutingExample::multiplyAsync)
                )
            );
    }

    public RouteResult add(RequestContext ctx, Integer xVal, Integer yVal) {
        int result = xVal + yVal;
        return ctx.complete(String.format("%d + %d = %d", xVal, yVal, result));
    }

    public RouteResult subtract(RequestContext ctx, Integer xVal, Integer yVal) {
        int result = xVal - yVal;
        return ctx.complete(String.format("%d - %d = %d", xVal, yVal, result));
    }

}