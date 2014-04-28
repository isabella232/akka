package akka.http.model.japi.headers;

import akka.http.model.japi.DateTime;

/**
 *  Model for the `If-Unmodified-Since` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p4-conditional-26#section-3.4
 */
public interface If_Unmodified_Since {
    DateTime date();
}
