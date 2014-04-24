package akka.http.model.japi;

public interface HttpCharset {
    String value();

    Iterable<String> getAliases();
}
