package akka.http.model.japi.headers;



/**
 *  Model for the `Access-Control-Allow-Credentials` header.
 *  Specification: http://www.w3.org/TR/cors/#access-control-allow-credentials-response-header
 */
public interface Access_Control_Allow_Credentials {
    boolean allow();
}
