package akka.http.model.japi.headers;

import akka.http.model.japi.HttpMethod;

/**
 *  Model for the `Access-Control-Allow-Methods` header.
 *  Specification: http://www.w3.org/TR/cors/#access-control-allow-methods-response-header
 */
public interface Access_Control_Allow_Methods {
    Iterable<HttpMethod> getMethods();
}
