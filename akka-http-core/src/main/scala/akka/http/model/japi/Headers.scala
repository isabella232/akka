package akka.http.model.japi

import akka.http.model

import akka.http.util.DateTime

import JavaMapping.Implicits._

object Headers {
  def Accept(ranges: MediaRange*): HttpHeader =
    model.headers.Accept(ranges.asScala)

  def Accept_Charset(charsetRanges: HttpCharsetRange*): HttpHeader =
    model.headers.`Accept-Charset`(charsetRanges.asScala)
  def Accept_Encoding(encodings: headers.HttpEncodingRange*): HttpHeader =
    model.headers.`Accept-Encoding`(encodings.asScala)
  def Accept_Language(languages: headers.LanguageRange*): HttpHeader =
    model.headers.`Accept-Language`(languages.asScala)
  def Accept_Ranges(rangeUnits: headers.RangeUnit*): HttpHeader =
    model.headers.`Accept-Ranges`(rangeUnits.asScala)

  def Access_Control_Allow_Credentials(allow: Boolean): HttpHeader =
    model.headers.`Access-Control-Allow-Credentials`(allow)
  def Access_Control_Allow_Headers(headers: String*): HttpHeader =
    model.headers.`Access-Control-Allow-Headers`(headers: _*)
  def Access_Control_Allow_Methods(methods: HttpMethod*): HttpHeader =
    model.headers.`Access-Control-Allow-Methods`(methods.asScala)
  def Access_Control_Allow_Origin(range: headers.HttpOriginRange): HttpHeader =
    model.headers.`Access-Control-Allow-Origin`(range.asScala)

  def Access_Control_Expose_Headers(headers: String*): HttpHeader =
    model.headers.`Access-Control-Expose-Headers`(headers: _*)

  def Access_Control_Max_Age(deltaSeconds: Long): HttpHeader =
    model.headers.`Access-Control-Max-Age`(deltaSeconds)

  def Access_Control_Request_Headers(headers: String*): HttpHeader =
    model.headers.`Access-Control-Request-Headers`(headers: _*)

  def Access_Control_Request_Method(method: HttpMethod): HttpHeader =
    model.headers.`Access-Control-Request-Method`(method.asScala)

  def Allow(methods: HttpMethod*): HttpHeader =
    model.headers.Allow(methods.asScala)

  def Authorization(credentials: headers.HttpCredentials): HttpHeader =
    model.headers.Authorization(credentials.asScala)

  def Cache_Control(directives: headers.CacheDirective): HttpHeader =
    model.headers.`Cache-Control`(directives.asScala)

  def Connection(tokens: String*): HttpHeader =
    model.headers.Connection(tokens.toList)

  def Content_Disposition(dispositionType: headers.ContentDispositionType, parameters: java.util.Map[String, String]): HttpHeader =
    model.headers.`Content-Disposition`(dispositionType.asScala, parameters.asScala)

  def Content_Encoding(encodings: headers.HttpEncoding*): HttpHeader =
    model.headers.`Content-Encoding`(encodings.asScala)

  def Content_Range(rangeUnit: headers.RangeUnit, contentRange: headers.ContentRange): HttpHeader =
    model.headers.`Content-Range`(rangeUnit.asScala, contentRange.asScala)

  def Cookie(cookies: headers.HttpCookie*): HttpHeader =
    model.headers.Cookie(cookies.asScala)

  def ETag(etag: headers.EntityTag): HttpHeader =
    model.headers.ETag(etag.asScala)

  //  sealed abstract case class Expect private (): HttpHeader =

  // The case class has `host: Uri.Host` which currently isn't supported in Java
  def Host(host: String, port: Int): HttpHeader =
    model.headers.Host(host, port)

  def If_Match(m: headers.EntityTagRange): HttpHeader =
    model.headers.`If-Match`(m.asScala)

  def If_Modified_Since(date: DateTime): HttpHeader =
    model.headers.`If-Modified-Since`(date)

  def If_None_Match(m: headers.EntityTagRange): HttpHeader =
    model.headers.`If-None-Match`(m.asScala)

  // Left out the If_Range(Either[EntityTag, DateTime] constructor
  def If_Range(entityTag: headers.EntityTag): HttpHeader =
    model.headers.`If-Range`(entityTag.asScala)
  def If_Range(dateTime: DateTime): HttpHeader =
    model.headers.`If-Range`(dateTime)

  def If_Unmodified_Since(date: DateTime): HttpHeader =
    model.headers.`If-Unmodified-Since`(date)

  def Last_Modified(date: DateTime): HttpHeader =
    model.headers.`Last-Modified`(date)

  def Link(values: headers.LinkValue*): HttpHeader =
    model.headers.Link(values.asScala)

  def Location(uri: Uri): HttpHeader =
    model.headers.Location(uri.asScala)

  def Origin(origins: headers.HttpOrigin*): HttpHeader =
    model.headers.Origin(origins.asScala)

  def Range(rangeUnit: headers.RangeUnit, ranges: headers.ByteRange*): HttpHeader =
    model.headers.Range(rangeUnit.asScala, ranges.asScala)

  def Proxy_Authenticate(challenges: headers.HttpChallenge*): HttpHeader =
    model.headers.`Proxy-Authenticate`(challenges.asScala)

  def Proxy_Authorization(credentials: headers.HttpCredentials): HttpHeader =
    model.headers.`Proxy-Authorization`(credentials.asScala)

  def Raw_Request_URI(uri: String): HttpHeader =
    model.headers.`Raw-Request-URI`(uri)

  def Remote_Address(address: headers.RemoteAddress): HttpHeader =
    model.headers.`Remote-Address`(address.asScala)

  def Server(products: headers.ProductVersion*): HttpHeader =
    model.headers.Server(products.asScala)

  def Set_Cookie(cookie: headers.HttpCookie): HttpHeader =
    model.headers.`Set-Cookie`(cookie.asScala)

  def User_Agent(products: headers.ProductVersion*): HttpHeader =
    model.headers.`User-Agent`(products.asScala)

  def WWW_Authenticate(challenges: headers.HttpChallenge*): HttpHeader =
    model.headers.`WWW-Authenticate`(challenges.asScala)
  def X_Forwarded_For(addresses: headers.RemoteAddress): HttpHeader =
    model.headers.`X-Forwarded-For`(addresses.asScala)

  def RawHeader(name: String, value: String): headers.RawHeader =
    model.headers.RawHeader(name, value)

  // TODO: Constructors that we don't really want to support:
  // def Content_Length(length: Long)(implicit ev: ProtectedHeaderCreation.Enabled): HttpHeader =
  // def Content_Type(contentType: ContentType)(implicit ev: ProtectedHeaderCreation.Enabled): HttpHeader =
  // def Date(date: DateTime)(implicit ev: ProtectedHeaderCreation.Enabled): HttpHeader =
  // def Transfer_Encoding(encodings: headers.TransferEncoding*)(implicit ev: ProtectedHeaderCreation.Enabled): HttpHeader =
}
