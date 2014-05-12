package akka.http.model.japi.headers;



/**
 *  Model for the `Proxy-Authorization` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p7-auth-26#section-4.4
 */
public abstract class Proxy_Authorization extends akka.http.model.HttpHeader {
    public abstract HttpCredentials credentials();
}
