package akka.http.model.japi.headers;



/**
 *  Model for the `Access-Control-Request-Headers` header.
 *  Specification: http://www.w3.org/TR/cors/#access-control-request-headers-request-header
 */
public abstract class Access_Control_Request_Headers extends akka.http.model.HttpHeader {
    public abstract Iterable<String> getHeaders();
}
