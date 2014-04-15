package akka.http.model.japi;

public interface HttpResponse extends HttpMessage {
    StatusCode status();
}
