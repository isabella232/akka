package akka.http.model.japi.headers;



/**
 *  Model for the `Access-Control-Allow-Headers` header.
 *  Specification: http://www.w3.org/TR/cors/#access-control-allow-headers-response-header
 */
public interface Access_Control_Allow_Headers {
    Iterable<String> getHeaders();
}
