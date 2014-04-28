package akka.http.model.japi.headers;



/**
 *  Model for the `ETag` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p4-conditional-26#section-2.3
 */
public interface ETag {
    EntityTag etag();
}
