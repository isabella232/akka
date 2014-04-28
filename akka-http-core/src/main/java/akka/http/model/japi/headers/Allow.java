package akka.http.model.japi.headers;

import akka.http.model.japi.HttpMethod;

/**
 *  Model for the `Allow` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-7.4.1
 */
public interface Allow {
    Iterable<HttpMethod> getMethods();
}
