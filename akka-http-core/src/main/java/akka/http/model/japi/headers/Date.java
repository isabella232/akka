package akka.http.model.japi.headers;

import akka.http.model.japi.DateTime;

/**
 *  Model for the `Date` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-7.1.1.2
 */
public abstract class Date extends akka.http.model.HttpHeader {
    public abstract DateTime date();
}
