package akka.http.model.japi.headers;

import java.util.Map;

public interface HttpChallenge {
    String scheme();
    String realm();

    Map<String, String> getParameters();
}