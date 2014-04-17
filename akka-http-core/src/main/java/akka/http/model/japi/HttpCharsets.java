package akka.http.model.japi;

public interface HttpCharsets {
    public static final HttpCharset US_ASCII = akka.http.model.HttpCharsets.US$minusASCII();
    public static final HttpCharset ISO_8859_1 = akka.http.model.HttpCharsets.ISO$minus8859$minus1();
    public static final HttpCharset UTF_8 = akka.http.model.HttpCharsets.UTF$minus8();
    public static final HttpCharset UTF_16 = akka.http.model.HttpCharsets.UTF$minus16();
    public static final HttpCharset UTF_16BE = akka.http.model.HttpCharsets.UTF$minus16BE();
    public static final HttpCharset UTF_16LE = akka.http.model.HttpCharsets.UTF$minus16LE();
}
