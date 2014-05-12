package akka.http.model.japi.headers;



/**
 *  Model for the `Proxy-Authenticate` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p7-auth-26#section-4.3
 */
public abstract class Proxy_Authenticate extends akka.http.model.HttpHeader {
    public abstract Iterable<HttpChallenge> getChallenges();
}
