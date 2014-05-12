package akka.http.model.japi.headers;

import akka.http.model.japi.Uri;

/**
 *  Model for the `Location` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-7.1.2
 */
public abstract class Location extends akka.http.model.HttpHeader {
    public abstract Uri getUri();
}
