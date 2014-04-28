package akka.http.model.japi.headers;



/**
 *  Model for the `X-Forwarded-For` header.
 *  Specification: http://en.wikipedia.org/wiki/X-Forwarded-For
 */
public interface X_Forwarded_For {
    Iterable<RemoteAddress> getAddresses();
}
