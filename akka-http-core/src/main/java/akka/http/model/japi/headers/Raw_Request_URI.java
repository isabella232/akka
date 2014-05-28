/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.model.japi.headers;

/**
 *  Model for the `Raw-Request-URI` header.
 *  Custom header we use for transporting the raw request URI either to the application (server-side)
 *  or to the request rendering stage (client-side).
 */
public abstract class Raw_Request_URI extends akka.http.model.HttpHeader {
    public abstract String uri();

    public static Raw_Request_URI create(String uri) {
        return new akka.http.model.headers.Raw$minusRequest$minusURI(uri);
    }
}
