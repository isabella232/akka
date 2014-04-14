package akka.http.model.japi;

public interface HttpRequest extends HttpMessage {
    HttpMethod method();
    Uri uri();

    HttpEntityRegular entity();
}
