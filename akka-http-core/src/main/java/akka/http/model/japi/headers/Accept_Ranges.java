package akka.http.model.japi.headers;



/**
 *  Model for the `Accept-Ranges` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p5-range-26#section-2.3
 */
public interface Accept_Ranges {
    Iterable<RangeUnit> getRangeUnits();
}
