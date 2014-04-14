package akka.http.model.japi;

import akka.util.ByteString;
import org.reactivestreams.api.Producer;

public interface HttpEntityDefault extends HttpEntityRegular {
    long contentLength();
    Producer<ByteString> data();
}
