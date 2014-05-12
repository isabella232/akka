package akka.http.model.japi.headers;



/**
 *  Model for the `Access-Control-Max-Age` header.
 *  Specification: http://www.w3.org/TR/cors/#access-control-max-age-response-header
 */
public abstract class Access_Control_Max_Age extends akka.http.model.HttpHeader {
    public abstract long deltaSeconds();
}
