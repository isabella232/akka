package akka.http.model.japi;

import akka.http.model.HttpEntity;

public abstract class LastChunk implements ChunkStreamPart {
    public abstract Iterable<HttpHeader> getTrailerHeaders();

    public static LastChunk create(String extension, Iterable<HttpHeader> trailers) {
        return new HttpEntity.LastChunk(extension, Util.<HttpHeader, akka.http.model.HttpHeader>convertIterable(trailers));
    }
}
