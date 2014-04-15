package akka.http.model.japi;

public interface LastChunk extends ChunkStreamPart {
    String extension();
    Iterable<HttpHeader> getTrailerHeaders();
}
