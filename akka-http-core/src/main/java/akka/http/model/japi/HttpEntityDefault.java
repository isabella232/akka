package akka.http.model.japi;

import akka.util.ByteString;
import org.reactivestreams.api.Producer;

public abstract class HttpEntityDefault extends HttpEntityRegular {
    public abstract long contentLength();
    public abstract Producer<ByteString> data();
}
