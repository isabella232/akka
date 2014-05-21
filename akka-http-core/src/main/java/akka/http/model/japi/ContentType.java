package akka.http.model.japi;

public abstract class ContentType {
    public abstract MediaType mediaType();

    public abstract boolean isCharsetDefined();
    public abstract HttpCharset getDefinedCharset();

    public static ContentType create(MediaType mediaType, HttpCharset charset) {
        return akka.http.model.ContentType.apply((akka.http.model.MediaType) mediaType, (akka.http.model.HttpCharset) charset);
    }
    public static ContentType create(MediaType mediaType) {
        return akka.http.model.ContentType.apply((akka.http.model.MediaType) mediaType);
    }
}
