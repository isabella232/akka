package akka.http.model.japi.headers;

import akka.http.model.japi.ContentType;

/**
 *  Model for the `Content-Type` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-3.1.1.5
 */
public abstract class Content_Type extends akka.http.model.HttpHeader {
    public abstract ContentType contentType();
}
