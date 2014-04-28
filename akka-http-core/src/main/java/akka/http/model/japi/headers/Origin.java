package akka.http.model.japi.headers;



/**
 *  Model for the `Origin` header.
 *  Specification: http://tools.ietf.org/html/rfc6454#section-7
 */
public interface Origin {
    Iterable<HttpOrigin> getOrigins();
}
