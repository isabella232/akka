package akka.http.model.japi;

import akka.stream.FlowMaterializer;
import akka.util.ByteString;
import org.reactivestreams.api.Producer;

public interface HttpEntity {
    boolean isKnownEmpty();
    ContentType contentType();

    public static final HttpEntity Empty = akka.http.model.HttpEntity.Empty;
}
