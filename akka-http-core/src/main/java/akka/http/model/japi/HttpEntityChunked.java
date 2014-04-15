package akka.http.model.japi;

import org.reactivestreams.api.Producer;

public interface HttpEntityChunked extends HttpEntity {
    Producer<ChunkStreamPart> getChunks();
}
