package akka.http.model.japi;

public interface HttpCharsets {
    final HttpCharset US_ASCII = akka.http.model.HttpCharsets.US$minusASCII();
    final HttpCharset ISO_8859_1 = akka.http.model.HttpCharsets.ISO$minus8859$minus1();
    final HttpCharset UTF_8 = akka.http.model.HttpCharsets.UTF$minus8();
    final HttpCharset UTF_16 = akka.http.model.HttpCharsets.UTF$minus16();
    final HttpCharset UTF_16BE = akka.http.model.HttpCharsets.UTF$minus16BE();
    final HttpCharset UTF_16LE = akka.http.model.HttpCharsets.UTF$minus16LE();
}
