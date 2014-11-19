/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi

import java.lang.reflect.Method

import scala.collection.immutable

import akka.http.server
import akka.http.server.{ PathMatchers ⇒ ScalaPathMatchers }

import scala.annotation.varargs
import akka.http.model.HttpMethods

import impl._

import scala.concurrent.Future

// These definitions cannot be moved into extra files, otherwise @varargs will break
// (separate compilation?)

trait BasicDirectives {
  /**
   * Tries the given routes in sequence until the first one matches.
   */
  @varargs
  def route(innerRoutes: Route*): Route = RouteConcatenation(innerRoutes.toVector)

  /**
   * A route that completes the request with a static text
   */
  def complete(text: String): Route =
    new OpaqueRoute() {
      def handle(ctx: RequestContext): RouteResult = ctx.complete(text)
    }

  /**
   * A route that completes the request using the given marshaller and value.
   */
  def completeAs[T](marshaller: Marshaller[T], value: T): Route =
    new OpaqueRoute() {
      def handle(ctx: RequestContext): RouteResult = ctx.completeAs(marshaller, value)
    }

  /**
   * A route that extracts a value and completes the request with it.
   */
  def extractAndComplete[T](marshaller: Marshaller[T], extraction: RequestVal[T]): Route =
    handle(extraction)(ctx ⇒ ctx.completeAs(marshaller, extraction.get(ctx)))

  /**
   * A directive that makes sure that all the standalone extractions have been
   * executed and validated.
   */
  @varargs
  def extractHere(extractions: RequestVal[_]*): Directive =
    Directive(Extract(extractions.map(_.asInstanceOf[StandaloneExtractionImpl[_ <: AnyRef]]), _))

  @varargs
  def handleWith[T1](handler: Handler, extractions: RequestVal[_]*): Route =
    handle(extractions: _*)(handler.handle(_))
  def handleWith[T1](e1: RequestVal[T1], handler: Handler1[T1]): Route =
    handle(e1)(ctx ⇒ handler.handle(ctx, e1.get(ctx)))
  def handleWith[T1, T2](e1: RequestVal[T1], e2: RequestVal[T2], handler: Handler2[T1, T2]): Route =
    handle(e1, e2)(ctx ⇒ handler.handle(ctx, e1.get(ctx), e2.get(ctx)))
  def handleWith[T1, T2, T3](
    e1: RequestVal[T1], e2: RequestVal[T2], e3: RequestVal[T3], handler: Handler3[T1, T2, T3]): Route =
    handle(e1, e2, e3)(ctx ⇒ handler.handle(ctx, e1.get(ctx), e2.get(ctx), e3.get(ctx)))
  def handleWith[T1, T2, T3, T4](
    e1: RequestVal[T1], e2: RequestVal[T2], e3: RequestVal[T3], e4: RequestVal[T4], handler: Handler4[T1, T2, T3, T4]): Route =
    handle(e1, e2, e3, e4)(ctx ⇒ handler.handle(ctx, e1.get(ctx), e2.get(ctx), e3.get(ctx), e4.get(ctx)))

  private[japi] def handle(extractions: RequestVal[_]*)(f: RequestContext ⇒ RouteResult): Route = {
    val route =
      new OpaqueRoute() {
        def handle(ctx: RequestContext): RouteResult = f(ctx)
      }
    val saExtractions = extractions.collect { case sa: StandaloneExtractionImpl[AnyRef] ⇒ sa }
    if (saExtractions.isEmpty) route
    else extractHere(saExtractions: _*).route(route)
  }

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
  def handleWith(clazz: Class[_], instance: AnyRef, methodName: String, extractions: RequestVal[_]*): Route = {
    def chooseOverload(methods: Seq[Method]): (RequestContext, Seq[Any]) ⇒ RouteResult = {
      val extractionTypes = extractions.map(_.resultClass).toList
      val RequestContextClass = classOf[RequestContext]

      import java.{ lang ⇒ jl }
      def paramMatches(expected: Class[_], actual: Class[_]): Boolean = expected match {
        case e if e isAssignableFrom actual ⇒ true
        // FIXME: add all boxing / unboxing variants
        case jl.Integer.TYPE if actual == classOf[jl.Integer] ⇒ true
        case _ ⇒ false
      }
      def paramsMatch(params: Seq[Class[_]]): Boolean = {
        val res =
          params.size == extractionTypes.size &&
            (params, extractionTypes).zipped.forall(paramMatches)

        res
      }
      def returnTypeMatches(method: Method): Boolean =
        method.getReturnType == classOf[RouteResult]

      object ParameterTypes {
        def unapply(method: Method): Option[List[Class[_]]] = Some(method.getParameterTypes.toList)
      }

      methods.filter(returnTypeMatches).collectFirst {
        case method @ ParameterTypes(RequestContextClass :: rest) if paramsMatch(rest) ⇒ {
          (ctx: RequestContext, params: Seq[Any]) ⇒ method.invoke(instance, (ctx +: params).toArray.asInstanceOf[Array[AnyRef]]: _*).asInstanceOf[RouteResult]
        }

        case method @ ParameterTypes(rest) if paramsMatch(rest) ⇒
          (ctx: RequestContext, params: Seq[Any]) ⇒ method.invoke(instance, params.toArray.asInstanceOf[Array[AnyRef]]: _*).asInstanceOf[RouteResult]
      }.getOrElse(throw new RuntimeException("No suitable method found"))
    }
    def lookupMethod() = {
      val candidateMethods = clazz.getMethods.filter(_.getName == methodName)
      chooseOverload(candidateMethods)
    }

    val method = lookupMethod()

    handle(extractions: _*)(ctx ⇒ method(ctx, extractions.map(_.get(ctx))))
  }
}

trait FileAndResourceDirectives {
  /**
   * Completes GET requests with the content of the given resource.
   * If the resource cannot be found or read the Route rejects the request.
   */
  def getFromResource(path: String): Route = GetFromResource(path)
}

trait MethodDirectives {
  /** Handles the inner routes if the incoming is a GET request, rejects the request otherwise */
  @varargs
  def get(innerRoutes: Route*): Route = MethodFilter(HttpMethods.GET, innerRoutes.toVector)

  /** Handles the inner routes if the incoming is a POST request, rejects the request otherwise */
  @varargs
  def post(innerRoutes: Route*): Route = MethodFilter(HttpMethods.POST, innerRoutes.toVector)

  /** Handles the inner routes if the incoming is a PUT request, rejects the request otherwise */
  @varargs
  def put(innerRoutes: Route*): Route = MethodFilter(HttpMethods.PUT, innerRoutes.toVector)

  /** Handles the inner routes if the incoming is a DELETE request, rejects the request otherwise */
  @varargs
  def delete(innerRoutes: Route*): Route = MethodFilter(HttpMethods.DELETE, innerRoutes.toVector)
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
  def path(matchers: AnyRef*): Directive =
    Directive(PathPrefix(convertMatchers(matchers) :+ new PathMatcherImpl(ScalaPathMatchers.PathEnd.tmap(_ ⇒ Tuple1(null)), isLast = true), _))

  @varargs
  def pathPrefix(matchers: AnyRef*): Directive =
    Directive(PathPrefix(convertMatchers(matchers), _))

  private def convertMatchers(matchers: Seq[AnyRef]): immutable.Seq[PathMatcherImpl[_ <: AnyRef]] = {
    def parse(matcher: AnyRef): PathMatcherImpl[_ <: AnyRef] = matcher match {
      case p: PathMatcherImpl[AnyRef] ⇒ p
      case name: String               ⇒ new PathMatcherImpl(name -> null)
    }

    if (matchers.nonEmpty) matchers.map(parse).toVector
    else Vector.empty
  }
}

object Directives
  extends BasicDirectives
  with FileAndResourceDirectives
  with MethodDirectives
  with PathDirectives
