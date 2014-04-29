package akka.http.model.japi.headers;

public interface EntityTag {
    String tag();
    boolean weak();
}
