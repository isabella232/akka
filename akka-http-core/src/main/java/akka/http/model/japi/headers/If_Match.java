package akka.http.model.japi.headers;



/**
 *  Model for the `If-Match` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p4-conditional-26#section-3.1
 */
public interface If_Match {
    EntityTagRange m();
}
