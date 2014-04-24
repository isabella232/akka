package akka.http.model.japi;

public interface HttpCharsetRange {
    float qValue();
    boolean matches(HttpCharset charset);
}
