package akka.http.model.japi;

public abstract class HttpMethod {
    public abstract String value();

    public abstract boolean isSafe();
    public abstract boolean isIdempotent();
    public abstract boolean isEntityAccepted();
}
