package akka.http.model.japi.headers;

import akka.http.model.japi.DateTime;
import akka.japi.Option;

public interface HttpCookie {
    String name();
    String content();

    Option<DateTime> getExpires();
    Option<Long> getMaxAge();
    Option<String> getDomain();
    Option<String> getPath();
    boolean secure();
    boolean httpOnly();
    Option<String> getExtension();
}
