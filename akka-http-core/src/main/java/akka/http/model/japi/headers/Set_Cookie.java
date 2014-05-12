package akka.http.model.japi.headers;



/**
 *  Model for the `Set-Cookie` header.
 *  Specification: https://tools.ietf.org/html/rfc6265
 */
public abstract class Set_Cookie extends akka.http.model.HttpHeader {
    public abstract HttpCookie cookie();
}
