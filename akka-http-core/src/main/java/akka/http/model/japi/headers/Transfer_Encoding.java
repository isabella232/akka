package akka.http.model.japi.headers;



/**
 *  Model for the `Transfer-Encoding` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p1-messaging-26#section-3.3.1
 */
public interface Transfer_Encoding {
    Iterable<TransferEncoding> getEncodings();
}
