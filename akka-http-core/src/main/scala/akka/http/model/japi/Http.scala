package akka.http.model.japi

import scala.collection.mutable.ListBuffer
import java.lang.Iterable

import akka.http.{ model ⇒ sc }
import akka.http.model.HttpMethods
import scala.reflect.ClassTag

object Http {
  def HttpRequest(): HttpRequestBuilder = HttpRequest(sc.HttpRequest())
  def HttpRequest(request: sc.HttpRequest): HttpRequestBuilder =
    new HttpRequestBuilder {
      var method: sc.HttpMethod = request.method
      var uri: sc.Uri = request.uri
      var entity: sc.HttpEntity.Regular = request.entity
      var protocol: sc.HttpProtocol = request.protocol
      var headers = ListBuffer[sc.HttpHeader](request.headers: _*)

      def addHeaders(headers: Iterable[HttpHeader]): HttpRequestBuilder = {
        import collection.JavaConverters._
        this.headers ++= headers.asScala.map(cast[sc.HttpHeader])
        this
      }

      def addHeader(header: HttpHeader): HttpRequestBuilder = {
        this.headers += cast[sc.HttpHeader](header)
        this
      }

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

      def protocol(protocol: HttpProtocol): HttpRequestBuilder = {
        this.protocol = cast[sc.HttpProtocol](protocol)
        this
      }

      def build(): HttpRequest =
        akka.http.model.HttpRequest(method, uri, headers.toList, entity, protocol)
    }

  def Uri(uri: String): Uri = Uri(uri)

  def cast[T](obj: AnyRef)(implicit classTag: ClassTag[T]): T =
    try classTag.runtimeClass.cast(obj).asInstanceOf[T]
    catch {
      case exp: ClassCastException ⇒
        throw new IllegalArgumentException(s"Illegal custom subclass of $classTag. " +
          s"Please use only the provided factories in akka.http.model.japi.Http")
    }
}
