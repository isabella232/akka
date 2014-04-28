package akka.http.model.japi.headers;



/**
 *  Model for the `Content-Range` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p5-range-26#section-4.2
 */
public interface Content_Range {
    RangeUnit rangeUnit();
    ContentRange contentRange();
}
