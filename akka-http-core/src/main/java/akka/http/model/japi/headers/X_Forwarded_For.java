/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.model.japi.headers;

/**
 *  Model for the `X-Forwarded-For` header.
 *  Specification: http://en.wikipedia.org/wiki/X-Forwarded-For
 */
public abstract class X_Forwarded_For extends akka.http.model.HttpHeader {
    public abstract Iterable<RemoteAddress> getAddresses();

    public static X_Forwarded_For create(RemoteAddress... addresses) {
        return new akka.http.model.headers.X$minusForwarded$minusFor(akka.http.model.japi.Util.<RemoteAddress, akka.http.model.headers.RemoteAddress>convertArray(addresses));
    }
}
