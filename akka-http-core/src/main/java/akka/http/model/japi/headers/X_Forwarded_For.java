package akka.http.model.japi.headers;



/**
 *  Model for the `X-Forwarded-For` header.
 *  Specification: http://en.wikipedia.org/wiki/X-Forwarded-For
 */
public abstract class X_Forwarded_For extends akka.http.model.HttpHeader {
    public abstract Iterable<RemoteAddress> getAddresses();
}
