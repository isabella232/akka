package akka.http.model.japi.headers;



/**
 *  Model for the `Access-Control-Allow-Origin` header.
 *  Specification: http://www.w3.org/TR/cors/#access-control-allow-origin-response-header
 */
public abstract class Access_Control_Allow_Origin extends akka.http.model.HttpHeader {
    public abstract HttpOriginRange range();

    public static Access_Control_Allow_Origin create(HttpOriginRange range) {
        return new akka.http.model.headers.Access$minusControl$minusAllow$minusOrigin(((akka.http.model.headers.HttpOriginRange) range));
    }
}
