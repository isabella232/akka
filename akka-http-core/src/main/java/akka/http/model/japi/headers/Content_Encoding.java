package akka.http.model.japi.headers;

/**
 *  Model for the `Content-Encoding` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-3.1.2.2
 */
public abstract class Content_Encoding extends akka.http.model.HttpHeader {
    public abstract Iterable<HttpEncoding> getEncodings();

    public static Content_Encoding create(HttpEncoding... encodings) {
        return new akka.http.model.headers.Content$minusEncoding(akka.http.model.japi.Util.<HttpEncoding, akka.http.model.headers.HttpEncoding>convertArray(encodings));
    }
}
