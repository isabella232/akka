package akka.http.model.japi;

public interface ContentType {
    MediaType mediaType();

    boolean isCharsetDefined();
    HttpCharset getDefinedCharset();
}
