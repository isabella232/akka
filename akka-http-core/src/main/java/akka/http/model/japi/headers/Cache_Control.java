package akka.http.model.japi.headers;



/**
 *  Model for the `Cache-Control` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p6-cache-26#section-5.2
 */
public abstract class Cache_Control extends akka.http.model.HttpHeader {
    public abstract Iterable<CacheDirective> getDirectives();

    public static Cache_Control create(CacheDirective... directives) {
        return new akka.http.model.headers.Cache$minusControl(akka.http.model.japi.Util.<CacheDirective, akka.http.model.headers.CacheDirective>convertArray(directives));
    }
}
