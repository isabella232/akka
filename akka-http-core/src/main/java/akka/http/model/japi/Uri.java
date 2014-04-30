package akka.http.model.japi;

import akka.japi.Option;

import java.util.Map;

/**
 * The data model for a Uri.
 */
public interface Uri {
    boolean isAbsolute();
    boolean isRelative();
    boolean isEmpty();

    String scheme();

    Host host();
    int port();
    String userInfo();

    String path();
    Iterable<String> pathSegments();

    String queryString();

    Option<String> parameter(String key);
    boolean containsParameter(String key);
    Iterable<Parameter> parameters();
    Map<String, String> parameterMap();

    public static interface Parameter {
        String key();
        String value();
    }

    Option<String> fragment();
}
