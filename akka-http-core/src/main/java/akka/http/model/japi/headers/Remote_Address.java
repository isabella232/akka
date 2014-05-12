package akka.http.model.japi.headers;



/**
 *  Model for the `Remote-Address` header.
 *  Custom header we use for optionally transporting the peer's IP in an HTTP header.
 */
public abstract class Remote_Address extends akka.http.model.HttpHeader {
    public abstract RemoteAddress address();
}
