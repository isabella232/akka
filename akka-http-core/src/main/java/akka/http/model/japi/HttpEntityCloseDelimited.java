package akka.http.model.japi;

import akka.util.ByteString;
import org.reactivestreams.api.Producer;

public interface HttpEntityCloseDelimited extends HttpEntity {
    Producer<ByteString> data();
}
