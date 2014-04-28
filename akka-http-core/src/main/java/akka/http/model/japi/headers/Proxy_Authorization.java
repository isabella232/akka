package akka.http.model.japi.headers;



/**
 *  Model for the `Proxy-Authorization` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p7-auth-26#section-4.4
 */
public interface Proxy_Authorization {
    HttpCredentials credentials();
}
