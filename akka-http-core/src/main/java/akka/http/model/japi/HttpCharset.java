package akka.http.model.japi;

public abstract class HttpCharset {
    public abstract String value();
    public abstract HttpCharsetRange withQValue(float qValue);

    public abstract Iterable<String> getAliases();
}
