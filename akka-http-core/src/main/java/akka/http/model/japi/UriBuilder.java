package akka.http.model.japi;

import akka.japi.Option;

public interface UriBuilder {
    UriBuilder scheme(String scheme);

    UriBuilder host(Host host);
    UriBuilder host(String host);
    UriBuilder port(int port);
    UriBuilder userInfo(String userInfo);

    UriBuilder path(String path);
    UriBuilder addPathSegment(String segment);
    UriBuilder query(String query);

    UriBuilder toRelative();

    UriBuilder addParameter(String key, String value);

    UriBuilder fragment(String fragment);
    UriBuilder fragment(Option<String> fragment);

    Uri build();
}
