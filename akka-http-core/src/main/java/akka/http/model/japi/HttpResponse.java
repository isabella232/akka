package akka.http.model.japi;

public interface HttpResponse extends HttpMessage {
    StatusCode status();

    public static abstract class Builder implements HttpEntityRegular.Builder<Builder> {
        public static Builder create() {
            return Http.HttpResponse();
        }
        public static Builder create(HttpResponse initialize) {
            return Http.HttpResponse(initialize);
        }

        public abstract HttpResponse build();

        public abstract Builder protocol(HttpProtocol protocol);
        public abstract Builder status(StatusCode statusCode);
        public abstract Builder status(int statusCode);
        public abstract Builder entity(HttpEntity entity);
        public abstract Builder addHeader(HttpHeader header);
        public abstract Builder addHeaders(Iterable<HttpHeader> headers);
        public abstract Builder removeHeader(String headerName);
    }
}
