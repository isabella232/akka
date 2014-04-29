package akka.http.model.japi.headers;

public interface HttpOriginRange {
    boolean matches(HttpOrigin origin);
}
