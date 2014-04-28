package akka.http.model.japi.headers;



/**
 *  Model for the `Proxy-Authenticate` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p7-auth-26#section-4.3
 */
public interface Proxy_Authenticate {
    Iterable<HttpChallenge> getChallenges();
}
