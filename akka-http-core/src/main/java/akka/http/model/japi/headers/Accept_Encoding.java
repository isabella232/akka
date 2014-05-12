package akka.http.model.japi.headers;



/**
 *  Model for the `Accept-Encoding` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-5.3.4
 */
public abstract class Accept_Encoding extends akka.http.model.HttpHeader {
    public abstract Iterable<HttpEncodingRange> getEncodings();
}
