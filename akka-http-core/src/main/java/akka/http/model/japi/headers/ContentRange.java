package akka.http.model.japi.headers;

import akka.http.model.headers.ContentRange$;
import akka.japi.Option;

public abstract class ContentRange {
    public abstract boolean isByteContentRange();
    public abstract boolean isSatisfiable();
    public abstract boolean isOther();

    public abstract Option<Long> getSatisfiableFirst();
    public abstract Option<Long> getSatisfiableLast();

    public abstract Option<String> getOtherValue();

    public abstract Option<Long> getInstanceLength();

    public static ContentRange create(long first, long last) {
        return ContentRange$.MODULE$.apply(first, last);
    }
    public static ContentRange create(long first, long last, long instanceLength) {
        return ContentRange$.MODULE$.apply(first, last, instanceLength);
    }
    @SuppressWarnings("unchecked")
    public static ContentRange create(long first, long last, Option<Long> instanceLength) {
        return ContentRange$.MODULE$.apply(first, last, ((Option<Object>) (Option) instanceLength).asScala());
    }
    public static ContentRange createUnsatisfiable(long length) {
        return new akka.http.model.headers.ContentRange.Unsatisfiable(length);
    }
    public static ContentRange createOther(String value) {
        return new akka.http.model.headers.ContentRange.Other(value);
    }
}
