package akka.http.model.japi.headers;

import akka.http.model.japi.Uri;

public interface LinkValue {
    Uri getUri();
    Iterable<LinkParam> getParameters();
}
