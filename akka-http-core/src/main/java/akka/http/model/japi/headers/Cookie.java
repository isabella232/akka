package akka.http.model.japi.headers;



/**
 *  Model for the `Cookie` header.
 *  Specification: https://tools.ietf.org/html/rfc6265#section-4.2
 */
public interface Cookie {
    Iterable<HttpCookie> getCookies();
}
