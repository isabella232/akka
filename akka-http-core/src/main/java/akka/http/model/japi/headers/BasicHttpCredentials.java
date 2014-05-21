package akka.http.model.japi.headers;

public abstract class BasicHttpCredentials extends akka.http.model.headers.HttpCredentials {
    public abstract String username();
    public abstract String password();
}
