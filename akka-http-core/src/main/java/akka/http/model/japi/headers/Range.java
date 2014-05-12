package akka.http.model.japi.headers;



/**
 *  Model for the `Range` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p5-range-26#section-3.1
 */
public abstract class Range extends akka.http.model.HttpHeader {
    public abstract RangeUnit rangeUnit();
    public abstract Iterable<ByteRange> getRanges();
}
