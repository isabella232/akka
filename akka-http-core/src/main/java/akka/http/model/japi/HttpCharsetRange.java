package akka.http.model.japi;

public abstract class HttpCharsetRange {
    public abstract float qValue();
    public abstract boolean matches(HttpCharset charset);

    public static final HttpCharsetRange ALL = akka.http.model.HttpCharsetRange.$times$.MODULE$;
}
