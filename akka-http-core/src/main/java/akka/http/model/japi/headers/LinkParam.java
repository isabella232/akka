package akka.http.model.japi.headers;

import akka.http.model.japi.MediaType;
import akka.http.model.japi.Uri;
import akka.http.model.japi.Util;

public abstract class LinkParam {
    public abstract String key();
    public abstract Object value();
}
