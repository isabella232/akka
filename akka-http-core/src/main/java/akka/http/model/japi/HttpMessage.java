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

    public static interface MessageTransformations<Self> extends HttpEntityRegular.Builder<Self> {
        Self withEntity(HttpEntity entity);
        Self withProtocol(HttpProtocol protocol);

        Self addHeader(HttpHeader header);
        Self addHeaders(Iterable<HttpHeader> headers);
        Self removeHeader(String headerName);
    }
}
