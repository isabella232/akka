package akka.http.model.japi.headers;



/**
 *  Model for the `WWW-Authenticate` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p7-auth-26#section-4.1
 */
public interface WWW_Authenticate {
    Iterable<HttpChallenge> getChallenges();
}
