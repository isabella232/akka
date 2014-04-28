package akka.http.model.japi.headers;



/**
 *  Model for the `Remote-Address` header.
 *  Custom header we use for optionally transporting the peer's IP in an HTTP header.
 */
public interface Remote_Address {
    RemoteAddress address();
}
