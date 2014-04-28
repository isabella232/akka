package akka.http.model.japi.headers;



/**
 *  Model for the `Access-Control-Max-Age` header.
 *  Specification: http://www.w3.org/TR/cors/#access-control-max-age-response-header
 */
public interface Access_Control_Max_Age {
    long deltaSeconds();
}
