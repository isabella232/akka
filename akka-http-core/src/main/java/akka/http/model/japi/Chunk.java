package akka.http.model.japi;

import akka.http.model.HttpEntity;
import akka.util.ByteString;

public abstract class Chunk implements ChunkStreamPart {
    public static Chunk create(ByteString data, String extension) {
        return new HttpEntity.Chunk(data, extension);
    }
}
