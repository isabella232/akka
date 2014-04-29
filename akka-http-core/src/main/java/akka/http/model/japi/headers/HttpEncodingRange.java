package akka.http.model.japi.headers;

public interface HttpEncodingRange {
    float qValue();
    boolean matches(HttpEncoding encoding);
}
