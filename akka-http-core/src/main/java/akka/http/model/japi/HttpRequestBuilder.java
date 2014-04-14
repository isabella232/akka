package akka.http.model.japi;

public interface HttpRequestBuilder {
    HttpRequest build();

    HttpRequestBuilder protocol(HttpProtocol protocol);
    HttpRequestBuilder method(HttpMethod method);
    HttpRequestBuilder uri(Uri relativeUri);
    HttpRequestBuilder uri(String path);
    HttpRequestBuilder entity(HttpEntity entity);
    HttpRequestBuilder addHeader(HttpHeader header);
    HttpRequestBuilder addHeaders(Iterable<HttpHeader> headers);
}
