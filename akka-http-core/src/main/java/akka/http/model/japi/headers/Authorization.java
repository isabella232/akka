package akka.http.model.japi.headers;



/**
 *  Model for the `Authorization` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p7-auth-26#section-4.2
 */
public abstract class Authorization extends akka.http.model.HttpHeader {
    public abstract HttpCredentials credentials();
}
