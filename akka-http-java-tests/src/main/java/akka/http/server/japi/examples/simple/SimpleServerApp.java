/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi.examples.simple;

import akka.actor.ActorSystem;
import akka.http.server.japi.*;

import java.io.IOException;

import static akka.http.server.japi.Directives.*;

public class SimpleServerApp {
    static Parameter<Integer> x = Parameters.integer("x");
    static Parameter<Integer> y = Parameters.integer("y");

    static PathMatcher<Integer> xSegment = PathMatchers.integer();
    static PathMatcher<Integer> ySegment = PathMatchers.integer();

    public static RouteResult multiply(RequestContext ctx, int x, int y) {
        int result = x * y;
        return ctx.complete(String.format("%d * %d = %d", x, y, result));
    }

    public static void main(String[] args) throws IOException {
        Handler addHandler = new Handler() {
            @Override
            public RouteResult handle(RequestContext ctx) {
                int xVal = x.get(ctx);
                int yVal = y.get(ctx);
                int result = xVal + yVal;
                return ctx.complete(String.format("%d + %d = %d", xVal, yVal, result));
            }
        };
        Handler2<Integer, Integer> subtractHandler = new Handler2<Integer, Integer>() {
            public RouteResult handle(RequestContext ctx, Integer xVal, Integer yVal) {
                int result = xVal - yVal;
                return ctx.complete(String.format("%d - %d = %d", xVal, yVal, result));
            }
        };
        Route route =
            route(
                // matches the empty path
                path().route(
                    getFromResource("web/index.html")
                ),
                // matches paths like this: /add?x=42&y=23
                path("add").route(
                    handleWith(addHandler, x, y)
                ),
                path("subtract").route(
                    handleWith(x, y, subtractHandler)
                ),
                // matches paths like this: /multiply/{x}/{y}
                path("multiply", xSegment, ySegment).route(
                    // bind handler by reflection
                    handleWith(SimpleServerApp.class, "multiply", xSegment, ySegment)
                )
            );

        ActorSystem system = ActorSystem.create();
        try {
            HttpApp.bind(system, "localhost", 8080, route);
            System.out.println("Type RETURN to exit");
            System.in.read();
        } finally {
            system.shutdown();
        }
    }
}