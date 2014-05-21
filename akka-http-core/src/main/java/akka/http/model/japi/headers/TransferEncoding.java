package akka.http.model.japi.headers;

import akka.http.model.headers.TransferEncodings;
import akka.http.model.japi.Util;

import java.util.Map;

public abstract class TransferEncoding {
    public abstract String name();

    public abstract Map<String, String> getParameters();

    public static TransferEncoding createExtension(String name) {
        return new TransferEncodings.Extension(name, Util.emptyMap);
    }
    public static TransferEncoding createExtension(String name, Map<String, String> parameters) {
        return new TransferEncodings.Extension(name, Util.convertMapToScala(parameters));
    }
}
