package akka.http.model.japi

import java.lang.Iterable
import java.io.File
import scala.reflect.ClassTag
import scala.collection.mutable.ListBuffer
import akka.util.ByteString
import akka.http.model

object Http {
  def HttpRequest(): HttpRequestBuilder = HttpRequest(model.HttpRequest())
  def HttpRequest(request: HttpRequest): HttpRequestBuilder =
    new HttpRequestBuilder with CommonBuilder[HttpRequestBuilder] {
      protected def initialProtocol: HttpProtocol = request.protocol
      protected def initialHeaders: Iterable[HttpHeader] = request.getHeaders

      var method: model.HttpMethod = cast[model.HttpMethod](request.method)
      var uri: model.Uri = cast[model.Uri](request.uri)
      var entity: model.HttpEntity.Regular = cast[model.HttpEntity.Regular](request.entity)

      def entity(entity: HttpEntityRegular): HttpRequestBuilder = {
        this.entity = cast[model.HttpEntity.Regular](entity)
        this
      }
      def uri(relativeUri: Uri): HttpRequestBuilder = {
        this.uri = cast[model.Uri](uri)
        this
      }
      def uri(path: String): HttpRequestBuilder = ???

      def method(method: HttpMethod): HttpRequestBuilder = {
        this.method = cast[model.HttpMethod](method)
        this
      }
      def build(): HttpRequest =
        model.HttpRequest(method, uri, headers.toList, entity, protocol)
    }

  def HttpResponse(): HttpResponseBuilder = HttpResponse(model.HttpResponse())
  def HttpResponse(response: HttpResponse): HttpResponseBuilder =
    new HttpResponseBuilder with CommonBuilder[HttpResponseBuilder] {
      protected def initialProtocol: HttpProtocol = response.protocol
      protected def initialHeaders: Iterable[HttpHeader] = response.getHeaders

      var status: model.StatusCode = model.StatusCodes.OK
      var entity: model.HttpEntity = model.HttpEntity.Empty

      def status(code: Int): HttpResponseBuilder = status(StatusCode(code))
      def status(statusCode: StatusCode): HttpResponseBuilder = {
        this.status = cast[model.StatusCode](statusCode)
        this
      }
      def entity(entity: HttpEntity): HttpResponseBuilder = {
        this.entity = cast[model.HttpEntity](entity)
        this
      }
      protected def entity(e: HttpEntityRegular): HttpResponseBuilder = entity(e: HttpEntity)

      def build(): HttpResponse =
        model.HttpResponse(status, headers.toList, entity, protocol)
    }

  private trait CommonBuilder[T] extends HttpEntityRegularBuilder[T] {
    protected def initialHeaders: Iterable[HttpHeader]
    protected def initialProtocol: HttpProtocol
    protected def entity(entity: HttpEntityRegular): T

    var protocol: model.HttpProtocol = cast[model.HttpProtocol](initialProtocol)
    var headers = ListBuffer.empty[model.HttpHeader]
    addHeaders(initialHeaders)

    def addHeaders(headers: Iterable[HttpHeader]): this.type = {
      import collection.JavaConverters._
      this.headers ++= headers.asScala.map(cast[model.HttpHeader])
      this
    }

    def addHeader(header: HttpHeader): this.type = {
      this.headers += cast[model.HttpHeader](header)
      this
    }

    def protocol(protocol: HttpProtocol): this.type = {
      this.protocol = cast[model.HttpProtocol](protocol)
      this
    }

    def entity(string: String): T = entity(HttpEntity(string))
    def entity(bytes: Array[Byte]): T = entity(HttpEntity(bytes))
    def entity(bytes: ByteString): T = entity(HttpEntity(bytes))
    def entity(contentType: ContentType, string: String): T = entity(HttpEntity(contentType, string))
    def entity(contentType: ContentType, bytes: Array[Byte]): T = entity(HttpEntity(contentType, bytes))
    def entity(contentType: ContentType, bytes: ByteString): T = entity(HttpEntity(contentType, bytes))
    def entity(contentType: ContentType, file: File): T = entity(HttpEntity(contentType, file))
  }

  def Uri(uri: String): Uri = Uri(uri)
  def StatusCode(code: Int): StatusCode = model.StatusCode.int2StatusCode(code)

  def HttpEntity(string: String): HttpEntityRegular = model.HttpEntity(string)
  def HttpEntity(bytes: Array[Byte]): HttpEntityRegular = model.HttpEntity(bytes)
  def HttpEntity(bytes: ByteString): HttpEntityRegular = model.HttpEntity(bytes)
  def HttpEntity(contentType: ContentType, string: String): HttpEntityRegular =
    model.HttpEntity(cast[model.ContentType](contentType), string)
  def HttpEntity(contentType: ContentType, bytes: Array[Byte]): HttpEntityRegular =
    model.HttpEntity(cast[model.ContentType](contentType), bytes)
  def HttpEntity(contentType: ContentType, bytes: ByteString): HttpEntityRegular =
    model.HttpEntity(cast[model.ContentType](contentType), bytes)
  def HttpEntity(contentType: ContentType, file: File): HttpEntityRegular =
    model.HttpEntity(cast[model.ContentType](contentType), file)

  def cast[T](obj: AnyRef)(implicit classTag: ClassTag[T]): T =
    try classTag.runtimeClass.cast(obj).asInstanceOf[T]
    catch {
      case exp: ClassCastException ⇒
        throw new IllegalArgumentException(s"Illegal custom subclass of $classTag. " +
          s"Please use only the provided factories in akka.http.model.japi.Http")
    }
}
