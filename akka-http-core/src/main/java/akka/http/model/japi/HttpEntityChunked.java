package akka.http.model.japi;

import org.reactivestreams.api.Producer;

public abstract class HttpEntityChunked extends HttpEntityRegular {
    public abstract Producer<ChunkStreamPart> getChunks();
}
