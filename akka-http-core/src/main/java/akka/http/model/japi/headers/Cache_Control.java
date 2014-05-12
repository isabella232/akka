package akka.http.model.japi.headers;



/**
 *  Model for the `Cache-Control` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p6-cache-26#section-5.2
 */
public abstract class Cache_Control extends akka.http.model.HttpHeader {
    public abstract Iterable<CacheDirective> getDirectives();
}
