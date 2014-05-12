package akka.http.model.japi;

import akka.util.ByteString;
import org.reactivestreams.api.Producer;

public abstract class HttpEntityCloseDelimited implements HttpEntity {
    public abstract Producer<ByteString> data();
}
