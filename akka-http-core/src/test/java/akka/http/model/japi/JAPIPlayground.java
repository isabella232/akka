package akka.http.model.japi;

import static akka.http.model.japi.Http.HttpRequest;
import static akka.http.model.japi.Http.HttpResponse;

public class JAPIPlayground {
    public static void main(String[] args) {
    }

    public HttpRequest buildRequest() {
        return
            HttpRequest()
                .method(HttpMethod.POST)
                .uri("/send")
                .build();
    }

    public HttpResponse handleRequest(HttpRequest request) {
        return
            HttpResponse()
                .status(404)
                .build();
    }
}
