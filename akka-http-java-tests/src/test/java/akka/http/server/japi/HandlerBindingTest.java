/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi;

import akka.http.model.HttpRequest;
import org.junit.Test;

import static akka.http.server.japi.Directives.*;

public class HandlerBindingTest extends JUnitRouteTest {
    @Test
    public void testHandlerWithoutExtractions() {
        Route route = handleWith(
            new Handler() {
                @Override
                public RouteResult handle(RequestContext ctx) {
                    return ctx.complete("Ok");
                }
            }
        );
        TestResponse response = runRoute(HttpRequest.GET, route);
        response.assertEntity("Ok");
    }
    @Test
    public void testHandlerWithSomeExtractions() {
        final Parameter<Integer> a = Parameters.integer("a");
        final Parameter<Integer> b = Parameters.integer("b");

        Route route = handleWith(
            new Handler() {
                @Override
                public RouteResult handle(RequestContext ctx) {
                    return ctx.complete("Ok a:" + a.get(ctx) +" b:" + b.get(ctx));
                }
            }, a, b
        );
        runRoute(HttpRequest.GET.withUri("?a=23&b=42"), route)
            .assertEntity("Ok a:23 b:42");
    }
    @Test
    public void testHandlerIfExtractionFails() {
        final Parameter<Integer> a = Parameters.integer("a");

        Route route = handleWith(
            new Handler() {
                @Override
                public RouteResult handle(RequestContext ctx) {
                    return ctx.complete("Ok " + a.get(ctx));
                }
            }, a
        );
        TestResponse response = runRoute(HttpRequest.GET, route);
        response.assertStatusCode(404);
        response.assertEntity("Request is missing required query parameter 'a'");
    }
    @Test
    public void testHandler1() {
        final Parameter<Integer> a = Parameters.integer("a");

        Route route = handleWith(
            a,
            new Handler1<Integer>() {
                @Override
                public RouteResult handle(RequestContext ctx, Integer a) {
                    return ctx.complete("Ok " + a);
                }
            }
        );
        TestResponse response = runRoute(HttpRequest.GET.withUri("?a=23"), route);
        response.assertStatusCode(200);
        response.assertEntity("Ok 23");
    }
    @Test
    public void testHandler2() {
        Route route = handleWith(
                Parameters.integer("a"),
                Parameters.integer("b"),
                new Handler2<Integer, Integer>() {
                    @Override
                    public RouteResult handle(RequestContext ctx, Integer a, Integer b) {
                        return ctx.complete("Sum: " + (a + b));
                    }
                }
        );
        TestResponse response = runRoute(HttpRequest.GET.withUri("?a=23&b=42"), route);
        response.assertStatusCode(200);
        response.assertEntity("Sum: 65");
    }
    @Test
    public void testHandler3() {
        Route route = handleWith(
                Parameters.integer("a"),
                Parameters.integer("b"),
                Parameters.integer("c"),
                new Handler3<Integer, Integer, Integer>() {
                    @Override
                    public RouteResult handle(RequestContext ctx, Integer a, Integer b, Integer c) {
                        return ctx.complete("Sum: " + (a + b + c));
                    }
                }
        );
        TestResponse response = runRoute(HttpRequest.GET.withUri("?a=23&b=42&c=30"), route);
        response.assertStatusCode(200);
        response.assertEntity("Sum: 95");
    }
    @Test
    public void testHandler4() {
        Route route = handleWith(
                Parameters.integer("a"),
                Parameters.integer("b"),
                Parameters.integer("c"),
                Parameters.integer("d"),
                new Handler4<Integer, Integer, Integer, Integer>() {
                    @Override
                    public RouteResult handle(RequestContext ctx, Integer a, Integer b, Integer c, Integer d) {
                        return ctx.complete("Sum: " + (a + b + c + d));
                    }
                }
        );
        TestResponse response = runRoute(HttpRequest.GET.withUri("?a=23&b=42&c=30&d=45"), route);
        response.assertStatusCode(200);
        response.assertEntity("Sum: 140");
    }
    @Test
    public void testReflectiveInstanceHandler() {
        class Test {
            public RouteResult negate(RequestContext ctx, int a) {
                return ctx.complete("Negated: " + (- a));
            }
        }
        Route route = handleWith(new Test(), "negate", Parameters.integer("a"));
        TestResponse response = runRoute(HttpRequest.GET.withUri("?a=23"), route);
        response.assertStatusCode(200);
        response.assertEntity("Negated: -23");
    }

    public static RouteResult squared(RequestContext ctx, int a) {
        return ctx.complete("Squared: " + (a * a));
    }
    @Test
    public void testStaticReflectiveHandler() {
        Route route = handleWith(HandlerBindingTest.class, "squared", Parameters.integer("a"));
        TestResponse response = runRoute(HttpRequest.GET.withUri("?a=23"), route);
        response.assertStatusCode(200);
        response.assertEntity("Squared: 529");
    }
}
