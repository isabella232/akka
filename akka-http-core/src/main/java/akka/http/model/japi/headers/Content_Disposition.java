package akka.http.model.japi.headers;



/**
 *  Model for the `Content-Disposition` header.
 *  Specification: http://tools.ietf.org/html/rfc6266
 */
public abstract class Content_Disposition extends akka.http.model.HttpHeader {
    public abstract ContentDispositionType dispositionType();
    public abstract java.util.Map<String, String> getParameters();
}
