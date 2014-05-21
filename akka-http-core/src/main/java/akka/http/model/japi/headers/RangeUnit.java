package akka.http.model.japi.headers;

public abstract class RangeUnit {
    public abstract String name();

    public static RangeUnit create(String name) {
        return new akka.http.model.headers.RangeUnits.Other(name);
    }
}
