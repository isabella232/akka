package akka.http.model.japi;

public interface HttpMessage {
    boolean isRequest();
    boolean isResponse();

    HttpProtocol protocol();
    Iterable<HttpHeader> getHeaders();
    <T extends HttpHeader> akka.japi.Option<T> getHeader(Class<T> headerClass);

    HttpEntity entity();
}
