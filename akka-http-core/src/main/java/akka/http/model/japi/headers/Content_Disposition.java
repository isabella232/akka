package akka.http.model.japi.headers;



/**
 *  Model for the `Content-Disposition` header.
 *  Specification: http://tools.ietf.org/html/rfc6266
 */
public interface Content_Disposition {
    ContentDispositionType dispositionType();
    java.util.Map<String, String> getParameters();
}
