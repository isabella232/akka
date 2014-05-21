package akka.http.model.japi;

import akka.util.ByteString;

import java.io.File;

public abstract class HttpEntityRegular extends HttpEntity {
    public static interface Builder<This> {
        This withEntity(String string);
        This withEntity(byte[] bytes);
        This withEntity(ByteString bytes);

        This withEntity(ContentType type, String string);
        This withEntity(ContentType type, byte[] bytes);
        This withEntity(ContentType type, ByteString bytes);

        This withEntity(ContentType type, File file);
    }
}
