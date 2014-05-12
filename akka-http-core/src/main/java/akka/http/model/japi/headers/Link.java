package akka.http.model.japi.headers;



/**
 *  Model for the `Link` header.
 *  Specification: http://tools.ietf.org/html/rfc5988#section-5
 */
public abstract class Link extends akka.http.model.HttpHeader {
    public abstract Iterable<LinkValue> getValues();
}
