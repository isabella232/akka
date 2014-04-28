package akka.http.model.japi.headers;

import akka.http.model.japi.DateTime;

/**
 *  Model for the `Last-Modified` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p4-conditional-26#section-2.2
 */
public interface Last_Modified {
    DateTime date();
}
