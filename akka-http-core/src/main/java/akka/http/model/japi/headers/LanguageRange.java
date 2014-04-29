package akka.http.model.japi.headers;

public interface LanguageRange {
    String primaryTag();
    float qValue();

    Iterable<String> getSubTags();
    boolean matches(Language language);
}
