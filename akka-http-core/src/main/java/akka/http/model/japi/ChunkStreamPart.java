package akka.http.model.japi;

import akka.util.ByteString;

public interface ChunkStreamPart {
    ByteString data();
    String extension();
    boolean isRegularChunk();
    boolean isLastChunk();
}
