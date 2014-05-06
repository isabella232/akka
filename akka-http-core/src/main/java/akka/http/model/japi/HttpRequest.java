package akka.http.model.japi;

public interface HttpRequest extends HttpMessage {
    HttpMethod method();
    Uri getUri();

    HttpEntityRegular entity();

    public static abstract class Builder implements HttpEntityRegular.Builder<Builder> {
        public static Builder create() {
            return Http.HttpRequest();
        }
        public static Builder create(HttpRequest initialize) {
            return Http.HttpRequest(initialize);
        }

        public abstract HttpRequest build();

        public abstract Builder protocol(HttpProtocol protocol);
        public abstract Builder method(HttpMethod method);
        public abstract Builder uri(Uri relativeUri);
        public abstract Builder uri(String path);
        public abstract Builder entity(HttpEntityRegular entity);

        public abstract Builder addHeader(HttpHeader header);
        public abstract Builder addHeaders(Iterable<HttpHeader> headers);
        public abstract Builder removeHeader(String headerName);
    }
}
