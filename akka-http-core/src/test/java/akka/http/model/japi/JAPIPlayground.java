package akka.http.model.japi;

import static akka.http.model.japi.Http.HttpRequest;

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
}
