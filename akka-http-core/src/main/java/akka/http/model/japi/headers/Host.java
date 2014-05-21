package akka.http.model.japi.headers;

public abstract class Host extends akka.http.model.HttpHeader {
    public abstract akka.http.model.japi.Host host();
    public abstract int port();
}
