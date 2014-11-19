/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import scala.annotation.varargs

// These definitions cannot be moved into extra files, otherwise @varargs will break
// (separate compilation?)

trait BasicDirectives {
  /**
   * Tries the given routes in sequence until the first one matches.
   */
  @varargs
  def route(innerRoutes: Route*): Route = ???

  /**
   * A route that completes the request with a static text
   */
  def complete(text: String): Route = ???

  /**
   * A route that completes the request using the given marshaller and value.
   */
  def completeAs[T](marshaller: Marshaller[T], value: T): Route = ???

  /**
   * A route that extracts a value and completes the request with it.
   */
  def extractAndComplete[T](marshaller: Marshaller[T], extraction: RequestVal[T]): Route =
    ???

  /**
   * A directive that makes sure that all the standalone extractions have been
   * executed and validated.
   */
  @varargs
  def extractHere(extractions: RequestVal[_]*): Directive = ???

  @varargs
  def handleWith[T1](handler: Handler, extractions: RequestVal[_]*): Route = ???
  def handleWith[T1](e1: RequestVal[T1], handler: Handler1[T1]): Route = ???
  def handleWith[T1, T2](e1: RequestVal[T1], e2: RequestVal[T2], handler: Handler2[T1, T2]): Route = ???
  def handleWith[T1, T2, T3](
    e1: RequestVal[T1], e2: RequestVal[T2], e3: RequestVal[T3], handler: Handler3[T1, T2, T3]): Route = ???
  def handleWith[T1, T2, T3, T4](
    e1: RequestVal[T1], e2: RequestVal[T2], e3: RequestVal[T3], e4: RequestVal[T4], handler: Handler4[T1, T2, T3, T4]): Route = ???

  @varargs
  def handleWith(instance: AnyRef, methodName: String, extractions: RequestVal[_]*): Route =
    handleWith(instance.getClass, instance, methodName, extractions: _*)

  @varargs
  def handleWith(clazz: Class[_], methodName: String, extractions: RequestVal[_]*): Route =
    handleWith(clazz, null, methodName, extractions: _*)

  /**
   * Handles the route by calling the method specified by `clazz` and `methodName`. Additionally, the value
   * of all extractions will be passed to the function.
   *
   * For extraction types `Extraction[T1]`, `Extraction[T2]`, ... the shape of the method must match this pattern:
   *
   * public static RouteResult methodName([RequestContext ctx], T1 t1, T2 t2, ...)
   *
   * The RequestContext parameter can be left off if it isn't needed.
   */
  @varargs
  def handleWith(clazz: Class[_], instance: AnyRef, methodName: String, extractions: RequestVal[_]*): Route =
    ???
}

trait FileAndResourceDirectives {
  /**
   * Completes GET requests with the content of the given resource.
   * If the resource cannot be found or read the Route rejects the request.
   */
  def getFromResource(path: String): Route = ???
}

trait MethodDirectives {
  /** Handles the inner routes if the incoming is a GET request, rejects the request otherwise */
  @varargs
  def get(innerRoutes: Route*): Route = ???

  /** Handles the inner routes if the incoming is a POST request, rejects the request otherwise */
  @varargs
  def post(innerRoutes: Route*): Route = ???

  /** Handles the inner routes if the incoming is a PUT request, rejects the request otherwise */
  @varargs
  def put(innerRoutes: Route*): Route = ???

  /** Handles the inner routes if the incoming is a DELETE request, rejects the request otherwise */
  @varargs
  def delete(innerRoutes: Route*): Route = ???
}

trait PathDirectives {
  /**
   * Tries to consumes the complete unmatched path given a number of PathMatchers. Between each
   * of the matchers a `/` will be matched automatically.
   *
   * A matcher can either be a matcher of type `PathMatcher`, or a literal string.
   *
   * FIXME: bettter documentation
   */
  @varargs
  def path(matchers: AnyRef*): Directive = ???

  @varargs
  def pathPrefix(matchers: AnyRef*): Directive = ???
}

object Directives
  extends BasicDirectives
  with FileAndResourceDirectives
  with MethodDirectives
  with PathDirectives
