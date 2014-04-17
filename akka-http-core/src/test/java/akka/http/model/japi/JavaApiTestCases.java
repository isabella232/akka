package akka.http.model.japi;

import static akka.http.model.japi.Http.*;

public class JavaApiTestCases {
    /** Builds a request for use on the client side */
    public static HttpRequest buildRequest() {
        return
            HttpRequest()
                .method(HttpMethod.POST)
                .uri("/send")
                .build();
    }
    /** A simple handler for an Http server */
    public static HttpResponse handleRequest(HttpRequest request) {
        if (request.method() == HttpMethod.GET) {
            Uri uri = request.getUri();
            if (uri.path().equals("/hello")) {
                String name = uri.parameter("name").getOrElse("Mister X");

                return
                    HttpResponse()
                        .entity("Hello " + name + "!")
                        .build();
            } else
                return
                    HttpResponse()
                        .entity("Not found")
                        .status(404)
                        .build();
        } else
            return
                HttpResponse()
                    .entity("Unsupported method")
                    .status(405)
                    .build();
    }
    /** Adds authentication to an existing request */
    public static HttpRequest addAuthentication(HttpRequest request) {
        return
            HttpRequest(request)
                //.addHeader(Http.Headers.Authorization.basic("username", "password"))
                .build();
    }

    /** Removes cookies from an existing request */
    public static HttpRequest removeCookies(HttpRequest request) {
        return
            HttpRequest(request)
                .removeHeader("Cookie")
                .build();
    }

    /** Build a uri to send a form */
    public static Uri createUriForOrder(String orderId, String price, String amount) {
        return
            UriBuilder()
                .path("/order")
                .addParameter("orderId", orderId)
                .addParameter("price", price)
                .addParameter("amount", amount)
                .build();
    }
}
