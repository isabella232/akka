/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.model.japi.headers;

import akka.http.model.japi.ContentRange;

/**
 *  Model for the `Content-Range` header.
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p5-range-26#section-4.2
 */
public abstract class Content_Range extends akka.http.model.HttpHeader {
    public abstract RangeUnit rangeUnit();
    public abstract ContentRange contentRange();

    public static Content_Range create(RangeUnit rangeUnit, ContentRange contentRange) {
        return new akka.http.model.headers.Content$minusRange(((akka.http.model.headers.RangeUnit) rangeUnit), ((akka.http.model.ContentRange) contentRange));
    }
}
