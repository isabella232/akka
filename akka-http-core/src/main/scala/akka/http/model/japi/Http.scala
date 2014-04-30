package akka.http.model.japi

import java.lang.Iterable
import java.io.File
import scala.collection.mutable.ListBuffer
import akka.util.ByteString
import akka.http.model
import akka.http.model.japi
import akka.japi.Option
import java.util
import scala.annotation.tailrec

import JavaMapping.Implicits._

object Http {
  def HttpRequest(): HttpRequestBuilder = HttpRequest(model.HttpRequest())
  def HttpRequest(request: HttpRequest): HttpRequestBuilder =
    new HttpRequestBuilder with MessageBuilder[HttpRequestBuilder] {
      protected def initialProtocol: HttpProtocol = request.protocol
      protected def initialHeaders: Iterable[HttpHeader] = request.getHeaders

      var method: model.HttpMethod = request.method.asScala
      var uri: model.Uri = request.getUri.asScala
      var entity: model.HttpEntity.Regular = request.entity.asScala

      def entity(entity: HttpEntityRegular): HttpRequestBuilder = {
        this.entity = entity.asScala
        this
      }
      def uri(relativeUri: Uri): HttpRequestBuilder = {
        this.uri = relativeUri.asScala
        this
      }
      def uri(path: String): HttpRequestBuilder = {
        this.uri = model.Uri(path)
        this
      }

      def method(method: HttpMethod): HttpRequestBuilder = {
        this.method = method.asScala
        this
      }
      def build(): HttpRequest =
        model.HttpRequest(method, uri, headers.toList, entity, protocol)
    }

  def HttpResponse(): HttpResponseBuilder = HttpResponse(model.HttpResponse())
  def HttpResponse(response: HttpResponse): HttpResponseBuilder =
    new HttpResponseBuilder with MessageBuilder[HttpResponseBuilder] {
      protected def initialProtocol: HttpProtocol = response.protocol
      protected def initialHeaders: Iterable[HttpHeader] = response.getHeaders

      var status: model.StatusCode = model.StatusCodes.OK
      var entity: model.HttpEntity = model.HttpEntity.Empty

      def status(code: Int): HttpResponseBuilder = status(StatusCode(code))
      def status(statusCode: StatusCode): HttpResponseBuilder = {
        this.status = statusCode.asScala
        this
      }
      def entity(entity: HttpEntity): HttpResponseBuilder = {
        this.entity = entity.asScala
        this
      }
      protected def entity(e: HttpEntityRegular): HttpResponseBuilder = entity(e: HttpEntity)

      def build(): HttpResponse =
        model.HttpResponse(status, headers.toList, entity, protocol)
    }

  private trait MessageBuilder[T] extends HttpEntityRegularBuilder[T] {
    protected def initialHeaders: Iterable[HttpHeader]
    protected def initialProtocol: HttpProtocol
    protected def entity(entity: HttpEntityRegular): T

    var protocol: model.HttpProtocol = initialProtocol.asScala
    var headers = ListBuffer.empty[model.HttpHeader]
    addHeaders(initialHeaders)

    def addHeaders(headers: Iterable[HttpHeader]): this.type = {
      this.headers ++= headers.asScala
      this
    }

    def addHeader(header: HttpHeader): this.type = {
      this.headers += header.asScala
      this
    }
    def removeHeader(headerName: String): this.type = {
      val lowerHeaderName = headerName.toLowerCase()
      this.headers = this.headers.filterNot(_.is(lowerHeaderName))
      this
    }

    def protocol(protocol: HttpProtocol): this.type = {
      this.protocol = protocol.asScala
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

  def Uri(): Uri = Uri(model.Uri())
  def Uri(uri: String): Uri = Uri(model.Uri(uri))

  private[japi] case class JavaUri(uri: model.Uri) extends Uri {
    def isRelative: Boolean = uri.isRelative
    def isAbsolute: Boolean = uri.isAbsolute
    def isEmpty: Boolean = uri.isEmpty

    def scheme(): String = uri.scheme
    def host(): Host = uri.authority.host
    def port(): Int = uri.authority.port
    def userInfo(): String = uri.authority.userinfo

    def path(): String = uri.path.toString

    import collection.JavaConverters._
    def pathSegments(): Iterable[String] = {
      import model.Uri.Path
      import Path._
      def gatherSegments(path: Path): List[String] = path match {
        case Empty               ⇒ Nil
        case Segment(head, tail) ⇒ head :: gatherSegments(tail)
        case Slash(tail)         ⇒ gatherSegments(tail)
      }
      gatherSegments(uri.path).asJava
    }

    def queryString(): String = uri.query.toString

    def parameterMap(): util.Map[String, String] = uri.query.toMap.asJava
    def parameters(): Iterable[japi.Uri.Parameter] = uri.query.map(t ⇒ Param(t._1, t._2): japi.Uri.Parameter).toIterable.asJava
    def containsParameter(key: String): Boolean = uri.query.get(key).isDefined
    def parameter(key: String): Option[String] = uri.query.get(key)

    case class Param(key: String, value: String) extends japi.Uri.Parameter

    def fragment: Option[String] = uri.fragment

    // Modification methods

    def t(f: model.Uri ⇒ model.Uri): Uri = JavaUri(f(uri))

    def scheme(scheme: String): Uri = t(_.withScheme(scheme))

    def host(host: Host): Uri = t(_.withHost(host.asScala))
    def host(host: String): Uri = t(_.withHost(host))
    def port(port: Int): Uri = t(_.withPort(port))
    def userInfo(userInfo: String): Uri = t(_.withUserInfo(userInfo))

    def path(path: String): Uri = t(_.withPath(model.Uri.Path(path)))

    def toRelative: Uri = t(_.toRelative)

    def query(query: String): Uri = t(_.withQuery(query))
    def addParameter(key: String, value: String): Uri = t { u ⇒
      u.withQuery(((key -> value) +: u.query.reverse).reverse)
    }

    def addPathSegment(segment: String): Uri = t { u ⇒
      import model.Uri.Path
      import Path._

      @tailrec def endsWithSlash(path: Path): Boolean = path match {
        case Empty               ⇒ false
        case Slash(Empty)        ⇒ true
        case Slash(tail)         ⇒ endsWithSlash(tail)
        case Segment(head, tail) ⇒ endsWithSlash(tail)
      }

      val newPath =
        if (endsWithSlash(u.path)) u.path ++ Path(segment)
        else u.path ++ Path./(segment)

      u.withPath(newPath)
    }

    def fragment(fragment: Option[String]): Uri = t(_.copy(fragment = fragment))
    def fragment(fragment: String): Uri = t(_.withFragment(fragment))
  }

  def Uri(uri: model.Uri): Uri = JavaUri(uri)

  def StatusCode(code: Int): StatusCode = model.StatusCode.int2StatusCode(code)

  def HttpEntity(string: String): HttpEntityRegular = model.HttpEntity(string)
  def HttpEntity(bytes: Array[Byte]): HttpEntityRegular = model.HttpEntity(bytes)
  def HttpEntity(bytes: ByteString): HttpEntityRegular = model.HttpEntity(bytes)
  def HttpEntity(contentType: ContentType, string: String): HttpEntityRegular =
    model.HttpEntity(contentType.asScala, string)
  def HttpEntity(contentType: ContentType, bytes: Array[Byte]): HttpEntityRegular =
    model.HttpEntity(contentType.asScala, bytes)
  def HttpEntity(contentType: ContentType, bytes: ByteString): HttpEntityRegular =
    model.HttpEntity(contentType.asScala, bytes)
  def HttpEntity(contentType: ContentType, file: File): HttpEntityRegular =
    model.HttpEntity(contentType.asScala, file)
}
