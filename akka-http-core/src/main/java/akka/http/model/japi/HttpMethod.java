package akka.http.model.japi;

public interface HttpMethod {
    String value();

    boolean isSafe();
    boolean isIdempotent();
    boolean isEntityAccepted();
}
