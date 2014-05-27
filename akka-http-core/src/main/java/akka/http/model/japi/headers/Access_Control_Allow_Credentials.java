package akka.http.model.japi.headers;

/**
 *  Model for the `Access-Control-Allow-Credentials` header.
 *  Specification: http://www.w3.org/TR/cors/#access-control-allow-credentials-response-header
 */
public abstract class Access_Control_Allow_Credentials extends akka.http.model.HttpHeader {
    public abstract boolean allow();

    public static Access_Control_Allow_Credentials create(boolean allow) {
        return new akka.http.model.headers.Access$minusControl$minusAllow$minusCredentials(allow);
    }
}
