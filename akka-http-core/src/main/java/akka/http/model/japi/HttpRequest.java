/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.model.japi;

/**
 * Represents an Http request.
 */
public abstract class HttpRequest implements HttpMessage, HttpMessage.MessageTransformations<HttpRequest> {
    /**
     * Returns the Http method of this request.
     */
    public abstract HttpMethod method();

    /**
     * Returns the Uri of this request.
     */
    public abstract Uri getUri();

    /**
     * Returns the entity of this request.
     */
    public abstract RequestEntity entity();

    /**
     * Returns a copy of this instance with a new method.
     */
    public abstract HttpRequest withMethod(HttpMethod method);

    /**
     * Returns a copy of this instance with a new Uri.
     */
    public abstract HttpRequest withUri(Uri relativeUri);

    /**
     * Returns a copy of this instance with a new Uri.
     */
    public abstract HttpRequest withUri(String path);

    /**
     * Returns a copy of this instance with a new entity.
     */
    public abstract HttpRequest withEntity(RequestEntity entity);

    /**
     * Returns a default request to be modified using the `withX` methods.
     */
    public static HttpRequest create() {
        return Accessors$.MODULE$.HttpRequest();
    }

    /**
     * A default GET request to be modified using the `withX` methods.
     */
    public static HttpRequest GET = create();
    /**
     * A default POST request to be modified using the `withX` methods.
     */
    public static HttpRequest POST = create().withMethod(HttpMethods.POST);
    /**
     * A default PUT request to be modified using the `withX` methods.
     */
    public static HttpRequest PUT = create().withMethod(HttpMethods.PUT);
    /**
     * A default DELETE request to be modified using the `withX` methods.
     */
    public static HttpRequest DELETE = create().withMethod(HttpMethods.DELETE);
    /**
     * A default HEAD request to be modified using the `withX` methods.
     */
    public static HttpRequest HEAD = create().withMethod(HttpMethods.HEAD);
}
