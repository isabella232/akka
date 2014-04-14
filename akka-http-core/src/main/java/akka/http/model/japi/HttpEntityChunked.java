package akka.http.model.japi;

import org.reactivestreams.api.Producer;

public interface HttpEntityChunked extends HttpEntityRegular {
    Producer<ChunkStreamPart> getChunks();
}
