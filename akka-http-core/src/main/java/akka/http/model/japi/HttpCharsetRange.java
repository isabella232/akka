package akka.http.model.japi;

public interface HttpCharsetRange {
    float qValue();
    boolean matches(HttpCharset charset);

    public static final HttpCharsetRange ALL = akka.http.model.HttpCharsetRange.$times$.MODULE$;
}
