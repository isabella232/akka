package akka.http.model.japi.headers;

public interface HttpOrigin {
    String scheme();
    Host host();
}
