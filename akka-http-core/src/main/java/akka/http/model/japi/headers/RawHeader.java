package akka.http.model.japi.headers;

public abstract class RawHeader extends akka.http.model.HttpHeader {
    public abstract String name();
    public abstract String value();
}
