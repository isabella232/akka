package akka.http.model.japi.headers;



/**
 *  Model for the `User-Agent` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-5.5.3
 */
public interface User_Agent {
    Iterable<ProductVersion> getProducts();
}
