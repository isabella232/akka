package akka.http.model.japi.headers;

import java.util.Map;

public interface HttpCredentials {
    String scheme();
    String token();

    Map<String, String> getParameters();
}
