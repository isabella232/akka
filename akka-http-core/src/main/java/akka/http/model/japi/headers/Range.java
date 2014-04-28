package akka.http.model.japi.headers;



/**
 *  Model for the `Range` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p5-range-26#section-3.1
 */
public interface Range {
    RangeUnit rangeUnit();
    Iterable<ByteRange> getRanges();
}
