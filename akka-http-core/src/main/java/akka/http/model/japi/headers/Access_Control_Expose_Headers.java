package akka.http.model.japi.headers;



/**
 *  Model for the `Access-Control-Expose-Headers` header.
 *  Specification: http://www.w3.org/TR/cors/#access-control-expose-headers-response-header
 */
public interface Access_Control_Expose_Headers {
    Iterable<String> getHeaders();
}
