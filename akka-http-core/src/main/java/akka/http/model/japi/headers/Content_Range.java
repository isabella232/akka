package akka.http.model.japi.headers;



/**
 *  Model for the `Content-Range` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p5-range-26#section-4.2
 */
public abstract class Content_Range extends akka.http.model.HttpHeader {
    public abstract RangeUnit rangeUnit();
    public abstract ContentRange contentRange();
}
