package akka.http.model.japi;

public abstract class StatusCode {
    public abstract int intValue();
    public abstract String reason();
    public abstract String defaultMessage();
    public abstract boolean isSuccess();
    public abstract boolean isFailure();
    public abstract boolean allowsEntity();
}
