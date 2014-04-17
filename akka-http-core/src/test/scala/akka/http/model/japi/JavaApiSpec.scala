package akka.http.model.japi

import org.scalatest.{ MustMatchers, FreeSpec }
import scala.collection.JavaConverters._

class JavaApiSpec extends FreeSpec with MustMatchers {
  "The Java API should work for" - {
    "work with Uris" - {
      "addParameter" in {
        Http.UriBuilder(Http.Uri("/abc"))
          .addParameter("name", "paul")
          .build() must be(Http.Uri("/abc?name=paul"))
      }
      "addSegment" in {
        Http.UriBuilder(Http.Uri("/abc"))
          .addPathSegment("def")
          .build() must be(Http.Uri("/abc/def"))

        Http.UriBuilder(Http.Uri("/abc/"))
          .addPathSegment("def")
          .build() must be(Http.Uri("/abc/def"))
      }
      "scheme/host/port" in {
        Http.UriBuilder(Http.Uri("/abc"))
          .scheme("http")
          .host("example.com")
          .port(8258)
          .build() must be(Http.Uri("http://example.com:8258/abc"))
      }
      "toRelative" in {
        Http.UriBuilder(Http.Uri("http://example.com/abc"))
          .toRelative
          .build() must be(Http.Uri("/abc"))
      }
      "pathSegments" in {
        Http.Uri("/abc/def/ghi/jkl")
          .pathSegments().asScala.toSeq must contain inOrderOnly ("abc", "def", "ghi", "jkl")
      }
      "access parameterMap" in {
        Http.Uri("/abc?name=blub&age=28")
          .parameterMap().asScala must contain allOf ("name" -> "blub", "age" -> "28")
      }
      "access parameters" in {
        val Seq(param1, param2, param3) =
          Http.Uri("/abc?name=blub&age=28&name=blub2")
            .parameters.asScala.toSeq

        param1.key() must be("name")
        param1.value() must be("blub")

        param2.key() must be("age")
        param2.value() must be("28")

        param3.key() must be("name")
        param3.value() must be("blub2")
      }
      "containsParameter" in {
        val uri = Http.Uri("/abc?name=blub")
        uri.containsParameter("name") must be(true)
        uri.containsParameter("age") must be(false)
      }
      "access single parameter" in {
        val uri = Http.Uri("/abc?name=blub")
        uri.parameter("name") must be(akka.japi.Option.some("blub"))
        uri.parameter("age") must be(akka.japi.Option.none)

        Http.Uri("/abc?name=blub&name=blib").parameter("name") must be(akka.japi.Option.some("blub"))
      }
    }
  }
}
