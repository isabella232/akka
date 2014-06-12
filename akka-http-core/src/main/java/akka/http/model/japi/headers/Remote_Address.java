/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.model.japi.headers;

import akka.http.model.japi.RemoteAddress;

/**
 *  Model for the `Remote-Address` header.
 *  Custom header we use for optionally transporting the peer's IP in an HTTP header.
 */
public abstract class Remote_Address extends akka.http.model.HttpHeader {
    public abstract RemoteAddress address();

    public static Remote_Address create(RemoteAddress address) {
        return new akka.http.model.headers.Remote$minusAddress(((akka.http.model.RemoteAddress) address));
    }
}
