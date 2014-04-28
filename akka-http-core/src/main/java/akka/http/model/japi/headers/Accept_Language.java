package akka.http.model.japi.headers;



/**
 *  Model for the `Accept-Language` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-5.3.5
 */
public interface Accept_Language {
    Iterable<LanguageRange> getLanguages();
}
