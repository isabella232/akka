package akka.http.model.japi.headers;



/**
 *  Model for the `Content-Encoding` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-3.1.2.2
 */
public interface Content_Encoding {
    Iterable<HttpEncoding> getEncodings();
}
