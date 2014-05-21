package akka.http.model.japi.headers;

public abstract class OAuth2BearerToken extends akka.http.model.headers.HttpCredentials {
    public abstract String token();
}
