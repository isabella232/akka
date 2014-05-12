package akka.http.model.japi;

import akka.util.ByteString;

public abstract class ChunkStreamPart {
    public abstract ByteString data();
    public abstract String extension();
    public abstract boolean isLastChunk();

    // FIXME: document: will always be empty for regular chunks
    public abstract Iterable<HttpHeader> getTrailerHeaders();
}
