package akka.http.model.japi

import akka.http.model
import akka.http.model.japi

import JavaMapping.Implicits._

trait HeaderConstructors {
  def Accept(mediaRanges: MediaRange*): japi.headers.Accept =
    model.headers.Accept(mediaRanges.asScala)

  def Accept_Charset(charsetRanges: HttpCharsetRange*): japi.headers.Accept_Charset =
    model.headers.`Accept-Charset`(charsetRanges.asScala)

  def Accept_Encoding(encodings: headers.HttpEncodingRange*): japi.headers.Accept_Encoding =
    model.headers.`Accept-Encoding`(encodings.asScala)

  def Accept_Language(languages: headers.LanguageRange*): japi.headers.Accept_Language =
    model.headers.`Accept-Language`(languages.asScala)

  def Accept_Ranges(rangeUnits: headers.RangeUnit*): japi.headers.Accept_Ranges =
    model.headers.`Accept-Ranges`(rangeUnits.asScala)

  def Access_Control_Allow_Credentials(allow: Boolean): japi.headers.Access_Control_Allow_Credentials =
    model.headers.`Access-Control-Allow-Credentials`(allow)

  def Access_Control_Allow_Headers(headers: String*): japi.headers.Access_Control_Allow_Headers =
    model.headers.`Access-Control-Allow-Headers`(headers.asScala)

  def Access_Control_Allow_Methods(methods: HttpMethod*): japi.headers.Access_Control_Allow_Methods =
    model.headers.`Access-Control-Allow-Methods`(methods.asScala)

  def Access_Control_Allow_Origin(range: headers.HttpOriginRange): japi.headers.Access_Control_Allow_Origin =
    model.headers.`Access-Control-Allow-Origin`(range.asScala)

  def Access_Control_Expose_Headers(headers: String*): japi.headers.Access_Control_Expose_Headers =
    model.headers.`Access-Control-Expose-Headers`(headers.asScala)

  def Access_Control_Max_Age(deltaSeconds: Long): japi.headers.Access_Control_Max_Age =
    model.headers.`Access-Control-Max-Age`(deltaSeconds)

  def Access_Control_Request_Headers(headers: String*): japi.headers.Access_Control_Request_Headers =
    model.headers.`Access-Control-Request-Headers`(headers.asScala)

  def Access_Control_Request_Method(method: HttpMethod): japi.headers.Access_Control_Request_Method =
    model.headers.`Access-Control-Request-Method`(method.asScala)

  def Allow(methods: HttpMethod*): japi.headers.Allow =
    model.headers.Allow(methods.asScala)

  def Authorization(credentials: headers.HttpCredentials): japi.headers.Authorization =
    model.headers.Authorization(credentials.asScala)

  def Cache_Control(directives: headers.CacheDirective*): japi.headers.Cache_Control =
    model.headers.`Cache-Control`(directives.asScala)

  def Content_Disposition(dispositionType: headers.ContentDispositionType, parameters: java.util.Map[String, String]): japi.headers.Content_Disposition =
    model.headers.`Content-Disposition`(dispositionType.asScala, parameters.asScala)

  def Content_Encoding(encodings: headers.HttpEncoding*): japi.headers.Content_Encoding =
    model.headers.`Content-Encoding`(encodings.asScala)

  def Content_Range(rangeUnit: headers.RangeUnit, contentRange: headers.ContentRange): japi.headers.Content_Range =
    model.headers.`Content-Range`(rangeUnit.asScala, contentRange.asScala)

  def Content_Type(contentType: ContentType): japi.headers.Content_Type =
    model.headers.`Content-Type`(contentType.asScala)

  def Cookie(cookies: headers.HttpCookie*): japi.headers.Cookie =
    model.headers.Cookie(cookies.asScala)

  def Date(date: DateTime): japi.headers.Date =
    model.headers.Date(date.asScala)

  def ETag(etag: headers.EntityTag): japi.headers.ETag =
    model.headers.ETag(etag.asScala)

  def If_Match(m: headers.EntityTagRange): japi.headers.If_Match =
    model.headers.`If-Match`(m.asScala)

  def If_Modified_Since(date: DateTime): japi.headers.If_Modified_Since =
    model.headers.`If-Modified-Since`(date.asScala)

  def If_None_Match(m: headers.EntityTagRange): japi.headers.If_None_Match =
    model.headers.`If-None-Match`(m.asScala)

  def If_Unmodified_Since(date: DateTime): japi.headers.If_Unmodified_Since =
    model.headers.`If-Unmodified-Since`(date.asScala)

  def Last_Modified(date: DateTime): japi.headers.Last_Modified =
    model.headers.`Last-Modified`(date.asScala)

  def Link(values: headers.LinkValue*): japi.headers.Link =
    model.headers.Link(values.asScala)

  def Location(uri: Uri): japi.headers.Location =
    model.headers.Location(uri.asScala)

  def Origin(origins: headers.HttpOrigin*): japi.headers.Origin =
    model.headers.Origin(origins.asScala)

  def Proxy_Authenticate(challenges: headers.HttpChallenge*): japi.headers.Proxy_Authenticate =
    model.headers.`Proxy-Authenticate`(challenges.asScala)

  def Proxy_Authorization(credentials: headers.HttpCredentials): japi.headers.Proxy_Authorization =
    model.headers.`Proxy-Authorization`(credentials.asScala)

  def Range(rangeUnit: headers.RangeUnit, ranges: headers.ByteRange*): japi.headers.Range =
    model.headers.Range(rangeUnit.asScala, ranges.asScala)

  def Raw_Request_URI(uri: String): japi.headers.Raw_Request_URI =
    model.headers.`Raw-Request-URI`(uri.asScala)

  def Remote_Address(address: headers.RemoteAddress): japi.headers.Remote_Address =
    model.headers.`Remote-Address`(address.asScala)

  def Server(products: headers.ProductVersion*): japi.headers.Server =
    model.headers.Server(products.asScala)

  def Set_Cookie(cookie: headers.HttpCookie): japi.headers.Set_Cookie =
    model.headers.`Set-Cookie`(cookie.asScala)

  def Transfer_Encoding(encodings: headers.TransferEncoding*): japi.headers.Transfer_Encoding =
    model.headers.`Transfer-Encoding`(encodings.asScala)

  def User_Agent(products: headers.ProductVersion*): japi.headers.User_Agent =
    model.headers.`User-Agent`(products.asScala)

  def WWW_Authenticate(challenges: headers.HttpChallenge*): japi.headers.WWW_Authenticate =
    model.headers.`WWW-Authenticate`(challenges.asScala)

  def X_Forwarded_For(addresses: headers.RemoteAddress*): japi.headers.X_Forwarded_For =
    model.headers.`X-Forwarded-For`(addresses.asScala)
}