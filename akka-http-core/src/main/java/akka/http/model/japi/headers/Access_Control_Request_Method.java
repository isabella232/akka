/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.model.japi.headers;

import akka.http.model.japi.HttpMethod;

/**
 *  Model for the `Access-Control-Request-Method` header.
 *  Specification: http://www.w3.org/TR/cors/#access-control-request-method-request-header
 */
public abstract class Access_Control_Request_Method extends akka.http.model.HttpHeader {
    public abstract HttpMethod method();

    public static Access_Control_Request_Method create(HttpMethod method) {
        return new akka.http.model.headers.Access$minusControl$minusRequest$minusMethod(((akka.http.model.HttpMethod) method));
    }
}
