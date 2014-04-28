package akka.http.model.japi.headers;



/**
 *  Model for the `Server` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-7.4.2
 */
public interface Server {
    Iterable<ProductVersion> getProducts();
}
