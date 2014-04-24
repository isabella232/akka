package akka.http.model.japi;

import java.util.Map;

public interface MediaRange {
    String mainType();
    float qValue();
    boolean matches(MediaType mediaType);

    Map<String, String> getParameters();
}
