package akka.http.model.japi.headers;



/**
 *  Model for the `Accept-Encoding` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-5.3.4
 */
public interface Accept_Encoding {
    Iterable<HttpEncodingRange> getEncodings();
}
