package akka.http.model.japi.headers;



/**
 *  Model for the `Access-Control-Request-Headers` header.
 *  Specification: http://www.w3.org/TR/cors/#access-control-request-headers-request-header
 */
public interface Access_Control_Request_Headers {
    Iterable<String> getHeaders();
}
