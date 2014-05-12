package akka.http.model.japi.headers;



/**
 *  Model for the `Accept-Ranges` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p5-range-26#section-2.3
 */
public abstract class Accept_Ranges extends akka.http.model.HttpHeader {
    public abstract Iterable<RangeUnit> getRangeUnits();
}
