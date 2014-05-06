package akka.http.model.japi;

import akka.http.model.HttpEntity;
import akka.util.ByteString;

public interface ChunkStreamPart {
    ByteString data();
    String extension();
    boolean isRegularChunk();
    boolean isLastChunk();

    public static abstract class C {
        public static Chunk Chunk(ByteString data, String extension) {
            return new HttpEntity.Chunk(data, extension);
        }
        public static LastChunk LastChunk(String extension, Iterable<HttpHeader> trailers) {
            return new HttpEntity.LastChunk(extension, Util.<HttpHeader, akka.http.model.HttpHeader>convertIterable(trailers));
        }
    }
}
