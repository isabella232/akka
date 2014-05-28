/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.model.japi.headers;

import akka.http.model.japi.DateTime;

/**
 *  
 *  
 *  Specification: http://tools.ietf.org/html/draft-ietf-httpbis-p5-range-26#section-3.2 + http://tools.ietf.org/html/draft-ietf-httpbis-p4-conditional-26#section-3.5
 */
public interface If_Range {
    Object entityTagOrDateTime();
}
