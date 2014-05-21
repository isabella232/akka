package akka.http.model.japi;

import akka.japi.Option;

import java.util.Map;

/**
 * The data model for a Uri.
 */
public abstract class Uri {
    public abstract boolean isAbsolute();
    public abstract boolean isRelative();
    public abstract boolean isEmpty();

    public abstract String scheme();

    public abstract Host host();
    public abstract int port();
    public abstract String userInfo();

    public abstract String path();
    public abstract Iterable<String> pathSegments();

    public abstract String queryString();

    public abstract Option<String> parameter(String key);
    public abstract boolean containsParameter(String key);
    public abstract Iterable<Parameter> parameters();
    public abstract Map<String, String> parameterMap();

    public static interface Parameter {
        String key();
        String value();
    }

    public abstract Option<String> fragment();

    // Modification methods
    public abstract Uri scheme(String scheme);

    public abstract Uri host(Host host);
    public abstract Uri host(String host);
    public abstract Uri port(int port);
    public abstract Uri userInfo(String userInfo);

    public abstract Uri path(String path);
    public abstract Uri addPathSegment(String segment);
    public abstract Uri query(String query);

    public abstract Uri toRelative();

    public abstract Uri addParameter(String key, String value);

    public abstract Uri fragment(String fragment);
    public abstract Uri fragment(Option<String> fragment);

    public static Uri create() {
        return Http.Uri(akka.http.model.Uri.Empty$.MODULE$);
    }
    public static Uri create(String uri) {
        return Http.Uri(akka.http.model.Uri.apply(uri));
    }
}
