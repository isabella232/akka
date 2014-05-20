package akka.http.model.japi.headers;



/**
 *  Model for the `Access-Control-Expose-Headers` header.
 *  Specification: http://www.w3.org/TR/cors/#access-control-expose-headers-response-header
 */
public abstract class Access_Control_Expose_Headers extends akka.http.model.HttpHeader {
    public abstract Iterable<String> getHeaders();

    public static Access_Control_Expose_Headers create(String... headers) {
        return new akka.http.model.headers.Access$minusControl$minusExpose$minusHeaders(akka.http.model.japi.Util.<String, String>convertArray(headers));
    }
}
