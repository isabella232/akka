package akka.http.model.japi;

public interface StatusCode {
    int intValue();
    String reason();
    String defaultMessage();
    boolean isSuccess();
    boolean isFailure();
    boolean allowsEntity();
}
