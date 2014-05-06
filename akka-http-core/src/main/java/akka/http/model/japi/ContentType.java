package akka.http.model.japi;

public interface ContentType {
    MediaType mediaType();

    boolean isCharsetDefined();
    HttpCharset getDefinedCharset();

    public static abstract class C {
        public static ContentType create(MediaType mediaType, HttpCharset charset) {
            return akka.http.model.ContentType.apply((akka.http.model.MediaType) mediaType, (akka.http.model.HttpCharset) charset);
        }
        public static ContentType create(MediaType mediaType) {
            return akka.http.model.ContentType.apply((akka.http.model.MediaType) mediaType);
        }
    }
}
