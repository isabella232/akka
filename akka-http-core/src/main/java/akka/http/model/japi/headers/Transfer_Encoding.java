package akka.http.model.japi.headers;

/**
 *  Model for the `Transfer-Encoding` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p1-messaging-26#section-3.3.1
 */
public abstract class Transfer_Encoding extends akka.http.model.HttpHeader {
    public abstract Iterable<TransferEncoding> getEncodings();

    public static Transfer_Encoding create(TransferEncoding... encodings) {
        return new akka.http.model.headers.Transfer$minusEncoding(akka.http.model.japi.Util.<TransferEncoding, akka.http.model.headers.TransferEncoding>convertArray(encodings));
    }
}
