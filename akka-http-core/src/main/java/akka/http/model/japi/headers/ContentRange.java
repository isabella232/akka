package akka.http.model.japi.headers;

import akka.japi.Option;

public interface ContentRange {
    boolean isByteContentRange();
    boolean isSatisfiable();
    boolean isOther();

    Option<Long> getSatisfiableFirst();
    Option<Long> getSatisfiableLast();

    Option<String> getOtherValue();

    Option<Long> getInstanceLength();
}
