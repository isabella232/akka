package akka.http.model.japi;

import java.util.Map;

public abstract class MediaRange {
    public abstract String mainType();
    public abstract float qValue();
    public abstract boolean matches(MediaType mediaType);

    public abstract Map<String, String> getParameters();
}
