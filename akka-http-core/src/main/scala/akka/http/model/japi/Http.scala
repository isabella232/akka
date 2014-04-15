package akka.http.model.japi

import scala.collection.mutable.ListBuffer
import java.lang.Iterable

import akka.http.{ model ⇒ sc }
import akka.http.model.HttpMethods
import scala.reflect.ClassTag

object Http {
  def HttpRequest(): HttpRequestBuilder = HttpRequest(sc.HttpRequest())
  def HttpRequest(request: HttpRequest): HttpRequestBuilder =
    new HttpRequestBuilder with CommonBuilder {
      protected def initialProtocol: HttpProtocol = request.protocol
      protected def initialHeaders: Iterable[HttpHeader] = request.getHeaders

      var method: sc.HttpMethod = cast[sc.HttpMethod](request.method)
      var uri: sc.Uri = cast[sc.Uri](request.uri)
      var entity: sc.HttpEntity.Regular = cast[sc.HttpEntity.Regular](request.entity)

      def entity(entity: HttpEntity): HttpRequestBuilder = {
        this.entity = cast[sc.HttpEntity.Regular](entity)
        this
      }
      def uri(relativeUri: Uri): HttpRequestBuilder = {
        this.uri = cast[sc.Uri](uri)
        this
      }
      def uri(path: String): HttpRequestBuilder = ???

      def method(method: HttpMethod): HttpRequestBuilder = {
        this.method = cast[sc.HttpMethod](method)
        this
      }
      def build(): HttpRequest =
        sc.HttpRequest(method, uri, headers.toList, entity, protocol)
    }

  def HttpResponse(): HttpResponseBuilder = HttpResponse(sc.HttpResponse())
  def HttpResponse(response: HttpResponse): HttpResponseBuilder =
    new HttpResponseBuilder with CommonBuilder {
      protected def initialProtocol: HttpProtocol = response.protocol
      protected def initialHeaders: Iterable[HttpHeader] = response.getHeaders

      var status: sc.StatusCode = sc.StatusCodes.OK
      var entity: sc.HttpEntity = sc.HttpEntity.Empty

      def status(code: Int): HttpResponseBuilder = status(StatusCode(code))
      def status(statusCode: StatusCode): HttpResponseBuilder = {
        this.status = cast[sc.StatusCode](statusCode)
        this
      }
      def entity(entity: HttpEntity): HttpResponseBuilder = {
        this.entity = cast[sc.HttpEntity](entity)
        this
      }

      def build(): HttpResponse =
        sc.HttpResponse(status, headers.toList, entity, protocol)
    }

  private trait CommonBuilder {
    protected def initialHeaders: Iterable[HttpHeader]
    protected def initialProtocol: HttpProtocol

    var protocol: sc.HttpProtocol = cast[sc.HttpProtocol](initialProtocol)
    var headers = ListBuffer.empty[sc.HttpHeader]
    addHeaders(initialHeaders)

    def addHeaders(headers: Iterable[HttpHeader]): this.type = {
      import collection.JavaConverters._
      this.headers ++= headers.asScala.map(cast[sc.HttpHeader])
      this
    }

    def addHeader(header: HttpHeader): this.type = {
      this.headers += cast[sc.HttpHeader](header)
      this
    }

    def protocol(protocol: HttpProtocol): this.type = {
      this.protocol = cast[sc.HttpProtocol](protocol)
      this
    }
  }

  def Uri(uri: String): Uri = Uri(uri)
  def StatusCode(code: Int): StatusCode = sc.StatusCode.int2StatusCode(code)

  def cast[T](obj: AnyRef)(implicit classTag: ClassTag[T]): T =
    try classTag.runtimeClass.cast(obj).asInstanceOf[T]
    catch {
      case exp: ClassCastException ⇒
        throw new IllegalArgumentException(s"Illegal custom subclass of $classTag. " +
          s"Please use only the provided factories in akka.http.model.japi.Http")
    }
}
