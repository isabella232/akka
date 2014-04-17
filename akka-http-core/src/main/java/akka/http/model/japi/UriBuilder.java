package akka.http.model.japi;

public interface UriBuilder {
    UriBuilder scheme(String scheme);

    UriBuilder host(String host);
    UriBuilder port(int port);
    UriBuilder userInfo(String userInfo);

    UriBuilder path(String path);
    UriBuilder addPathSegment(String segment);
    UriBuilder query(String query);

    UriBuilder toRelative();

    UriBuilder addParameter(String key, String value);

    Uri build();
}
