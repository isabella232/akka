package akka.http.model.japi;

import akka.http.model.HttpEntity$;
import akka.util.ByteString;
import org.reactivestreams.api.Producer;
import scala.concurrent.ExecutionContext;

import java.io.File;

public abstract class HttpEntity {
    public static final HttpEntityRegular Empty = HttpEntity$.MODULE$.Empty();

    public abstract boolean isKnownEmpty();
    public abstract boolean isRegular();
    public abstract boolean isChunked();
    public abstract boolean isDefault();
    public abstract boolean isCloseDelimited();

    public abstract ContentType contentType();

    public abstract Producer<ByteString> getDataBytes(ExecutionContext executionContext);

    public static HttpEntityDefault create(String string) {
        return HttpEntity$.MODULE$.apply(string);
    }
    public static HttpEntityDefault create(byte[] bytes) {
        return HttpEntity$.MODULE$.apply(bytes);
    }
    public static HttpEntityDefault create(ByteString bytes) {
        return HttpEntity$.MODULE$.apply(bytes);
    }
    public static HttpEntityDefault create(ContentType contentType, String string) {
        return HttpEntity$.MODULE$.apply((akka.http.model.ContentType) contentType, string);
    }
    public static HttpEntityDefault create(ContentType contentType, byte[] bytes) {
        return HttpEntity$.MODULE$.apply((akka.http.model.ContentType) contentType, bytes);
    }
    public static HttpEntityDefault create(ContentType contentType, ByteString bytes) {
        return HttpEntity$.MODULE$.apply((akka.http.model.ContentType) contentType, bytes);
    }
    public static HttpEntityDefault create(ContentType contentType, File file) {
        return HttpEntity$.MODULE$.apply((akka.http.model.ContentType) contentType, file);
    }
    public static HttpEntityDefault create(ContentType contentType, long contentLength, Producer<ByteString> data) {
        return new akka.http.model.HttpEntity.Default((akka.http.model.ContentType) contentType, contentLength, data);
    }
    public static HttpEntityCloseDelimited createCloseDelimited(ContentType contentType, Producer<ByteString> data) {
        return new akka.http.model.HttpEntity.CloseDelimited((akka.http.model.ContentType) contentType, data);
    }
    public static HttpEntityChunked createChunked(ContentType contentType, Producer<ChunkStreamPart> chunks) {
        return new akka.http.model.HttpEntity.Chunked(
                (akka.http.model.ContentType) contentType,
                Util.<ChunkStreamPart, akka.http.model.HttpEntity.ChunkStreamPart>upcastProducer(chunks));
    }
}
