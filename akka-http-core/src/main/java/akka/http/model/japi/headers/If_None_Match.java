package akka.http.model.japi.headers;



/**
 *  Model for the `If-None-Match` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p4-conditional-26#section-3.2
 */
public interface If_None_Match {
    EntityTagRange m();
}
