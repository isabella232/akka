/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi;

import akka.http.marshallers.jackson.Jackson;
import akka.http.model.japi.HttpRequest;
import akka.http.model.japi.MediaTypes;
import org.junit.Test;

import static akka.http.server.japi.Directives.*;
import static org.junit.Assert.*;

public class CompleteTest extends JUnitRouteTest {
    @Test
    public void completeWithString() {
        Route route = complete("Everything OK!");

        HttpRequest request = HttpRequest.create();
        TestResponse response = runRoute(request, route);

        response.assertStatusCode(200);
        response.assertMediaType(MediaTypes.TEXT_PLAIN);
        response.assertEntity("Everything OK!");
    }

    @Test
    public void completeAsJacksonJson() {
        class Person {
            public String getFirstName() { return "Peter"; }
            public String getLastName() { return "Parker"; }
            public int getAge() { return 138; }
        }
        Route route = completeAs(Jackson.json(), new Person());

        HttpRequest request = HttpRequest.create();
        TestResponse response = runRoute(request, route);

        response.assertStatusCode(200);
        response.assertMediaType("application/json");
        response.assertEntity("{\"age\":138,\"firstName\":\"Peter\",\"lastName\":\"Parker\"}");
    }
}
