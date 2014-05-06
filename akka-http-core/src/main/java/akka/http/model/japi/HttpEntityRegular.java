package akka.http.model.japi;

import akka.util.ByteString;

import java.io.File;

public interface HttpEntityRegular extends HttpEntity {
    public static interface Builder<This> {
        This entity(String string);
        This entity(byte[] bytes);
        This entity(ByteString bytes);

        This entity(ContentType type, String string);
        This entity(ContentType type, byte[] bytes);
        This entity(ContentType type, ByteString bytes);

        This entity(ContentType type, File file);
    }
}
