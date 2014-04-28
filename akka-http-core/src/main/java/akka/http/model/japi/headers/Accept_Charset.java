package akka.http.model.japi.headers;

import akka.http.model.japi.HttpCharsetRange;

/**
 *  Model for the `Accept-Charset` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-5.3.3
 */
public interface Accept_Charset {
    Iterable<HttpCharsetRange> getCharsetRanges();
}
