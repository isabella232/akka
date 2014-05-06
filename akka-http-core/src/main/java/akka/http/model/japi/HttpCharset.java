package akka.http.model.japi;

public interface HttpCharset {
    String value();
    HttpCharsetRange withQValue(float qValue);

    Iterable<String> getAliases();
}
