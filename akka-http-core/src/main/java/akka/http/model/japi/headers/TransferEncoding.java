package akka.http.model.japi.headers;

import java.util.Map;

public interface TransferEncoding {
    String name();

    Map<String, String> getParameters();
}
