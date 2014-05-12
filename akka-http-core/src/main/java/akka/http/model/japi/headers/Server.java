package akka.http.model.japi.headers;



/**
 *  Model for the `Server` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-7.4.2
 */
public abstract class Server extends akka.http.model.HttpHeader {
    public abstract Iterable<ProductVersion> getProducts();
}
