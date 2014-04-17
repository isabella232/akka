package akka.http.model.japi;

import akka.http.model.HttpMethods;

public interface HttpMethod {
    String value();

    boolean isSafe();
    boolean isIdempotent();
    boolean isEntityAccepted();

    public static final HttpMethod CONNECT = HttpMethods.CONNECT();
    public static final HttpMethod DELETE  = HttpMethods.DELETE();
    public static final HttpMethod GET     = HttpMethods.GET();
    public static final HttpMethod HEAD    = HttpMethods.HEAD();
    public static final HttpMethod OPTIONS = HttpMethods.OPTIONS();
    public static final HttpMethod PATCH   = HttpMethods.PATCH();
    public static final HttpMethod POST    = HttpMethods.POST();
    public static final HttpMethod PUT     = HttpMethods.PUT();
    public static final HttpMethod TRACE   = HttpMethods.TRACE();
}
