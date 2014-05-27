package akka.http.model.japi.headers;

/**
 *  Model for the `Accept-Language` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p2-semantics-26#section-5.3.5
 */
public abstract class Accept_Language extends akka.http.model.HttpHeader {
    public abstract Iterable<LanguageRange> getLanguages();

    public static Accept_Language create(LanguageRange... languages) {
        return new akka.http.model.headers.Accept$minusLanguage(akka.http.model.japi.Util.<LanguageRange, akka.http.model.headers.LanguageRange>convertArray(languages));
    }
}
