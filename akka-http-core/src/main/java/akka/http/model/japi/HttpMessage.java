package akka.http.model.japi;

import akka.japi.Option;

public interface HttpMessage {
    boolean isRequest();
    boolean isResponse();

    HttpProtocol protocol();
    Iterable<HttpHeader> getHeaders();
    Option<HttpHeader> getHeader(String headerName);
    <T extends HttpHeader> akka.japi.Option<T> getHeader(Class<T> headerClass);

    HttpEntity entity();
}
