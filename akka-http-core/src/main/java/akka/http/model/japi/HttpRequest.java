package akka.http.model.japi;

public abstract class HttpRequest implements HttpMessage, HttpMessage.MessageTransformations<HttpRequest> {
    public abstract HttpMethod method();
    public abstract Uri getUri();

    public abstract HttpEntityRegular entity();

    public abstract HttpRequest withMethod(HttpMethod method);
    public abstract HttpRequest withUri(Uri relativeUri);
    public abstract HttpRequest withUri(String path);

    public static HttpRequest create() {
        return Http.HttpRequest();
    }
}
