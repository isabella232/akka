package akka.http.model.japi.headers;



/**
 *  Model for the `Set-Cookie` header.
 *  Specification: https://tools.ietf.org/html/rfc6265
 */
public interface Set_Cookie {
    HttpCookie cookie();
}
