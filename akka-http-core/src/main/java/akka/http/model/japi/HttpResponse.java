package akka.http.model.japi;

public abstract class HttpResponse implements HttpMessage, HttpMessage.MessageTransformations<HttpResponse> {
    public abstract StatusCode status();

    public abstract HttpResponse withStatus(StatusCode statusCode);
    public abstract HttpResponse withStatus(int statusCode);

    public static HttpResponse create() {
        return Http.HttpResponse();
    }
}
