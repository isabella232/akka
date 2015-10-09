/*
 * Copyright (C) 2009-2015 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.impl.engine.ws

import akka.stream.scaladsl.{ FlattenStrategy, Source }
import akka.stream.testkit.TestSubscriber.TimeoutException
import akka.util.ByteString
import org.scalatest.{ Matchers, FreeSpec }

import scala.util.control.NoStackTrace

class ByteStringSinkProbeSpec extends FreeSpec with Matchers with WithMaterializerSpec {
  object TheException extends Exception("Everything is wrong") with NoStackTrace

  "ByteStringSinkProbe" - {
    "expectBytes(length) should" - {
      "return exactly the next chunk if the size matches" in new TestSetup with StringChunkedInput {
        def chunks = Seq("abcdef")

        probe.expectBytes(6) shouldEqual ByteString("abcdef")
      }
      "return a subsequence of a big chunk of bytes" in new TestSetup with StringChunkedInput {
        def chunks = Seq("abcdef")

        probe.expectBytes(3) shouldEqual ByteString("abc")
        probe.expectBytes(1) shouldEqual ByteString("d")
        probe.expectBytes(2) shouldEqual ByteString("ef")
      }
      "return a subsequence of a several chunks of data" in new TestSetup with StringChunkedInput {
        def chunks = Seq("abc", "def")

        probe.expectBytes(6) shouldEqual ByteString("abcdef")
      }
      "provide a proper error message" - {
        "if an error occurs while waiting for more data" in new TestSetup {
          def input: Source[ByteString, Unit] =
            Source(List(Source.single(ByteString("abc")), Source.failed[ByteString](TheException))).flatten[ByteString](FlattenStrategy.concat)

          val exception =
            intercept[AssertionError] {
              probe.expectBytes(4)
            }
          exception.getMessage shouldEqual
            """Got error while waiting for 1 more bytes (out of 4): 'Everything is wrong'. Already received bytes:
              |  ByteString(3 bytes)
              |
              |    61 62 63                                        | abc""".stripMargin
          exception.getCause shouldEqual TheException
        }
        "if completion occurs while waiting for more data" in new TestSetup {
          def input: Source[ByteString, Unit] = Source.single(ByteString("abc"))

          val exception =
            intercept[AssertionError] {
              probe.expectBytes(4)
            }
          exception.getMessage shouldEqual
            """Truncation. Got completion while waiting for 1 more bytes (out of 4). Already received bytes:
              |  ByteString(3 bytes)
              |
              |    61 62 63                                        | abc""".stripMargin
        }
        "if a timeout occurs while waiting for more data" in new TestSetup {
          def input: Source[ByteString, Unit] = Source.single(ByteString("abc")) ++ Source.lazyEmpty

          val exception =
            intercept[TimeoutException] {
              probe.expectBytes(4)
            }
          exception.getMessage shouldEqual
            """Timeout after 3 seconds while waiting for 1 more bytes (out of 4). Already received bytes:
              |  ByteString(3 bytes)
              |
              |    61 62 63                                        | abc""".stripMargin
        }
      }
    }
    "expectBytes(ByteString) should" - {
      "succeed if bytes match the expected" - {
        "if it matches exactly the next chunk" in new TestSetup with StringChunkedInput {
          def chunks = Seq("abcdef")

          probe.expectBytes(ByteString("abcdef"))
        }
        "if it is a subsequence of a big chunk of bytes" in new TestSetup with StringChunkedInput {
          def chunks = Seq("abcdef")

          probe.expectBytes(ByteString("abc"))
          probe.expectBytes(ByteString("d"))
          probe.expectBytes(ByteString("ef"))
        }
        "if it is a subsequence of a several chunks of data" in new TestSetup with StringChunkedInput {
          def chunks = Seq("abc", "def")

          probe.expectBytes(ByteString("abcdef"))
        }
      }
      "provide a proper error message" - {
        "if only a few bytes differ" in pending
        "if a suffix differs" in pending
        "if a prefix differs" in pending
        "if the expected data doesn't match" in pending
        "if an error occurs while waiting for more data" in pending
        "if completion occurs while waiting for more data" in pending
        "if a timeout occurs while waiting for more data" in pending
      }
    }

    "expectUtf8EncodedString(byteLength) should" - {
      "return a subsequence of a big chunk of bytes" in pending
      "return a subsequence of a several chunks of data" in pending
      "provide a proper error message" - {
        "if an error occurs while waiting for more data" in pending
        "if completion occurs while waiting for more data" in pending
        "if a timeout occurs while waiting for more data" in pending
      }
    }
    "expectUtf8EncodedString(String) should" - {
      "succeed if string matches the expected one" - {
        "if it is a subsequence of a big chunk of bytes" in pending
        "if it is a subsequence of a several chunks of data" in pending
      }
      "provide a proper error message" - {
        "if only a few characters differ" in pending
        "if a suffix differs" in pending
        "if a prefix differs" in pending
        "if the expected data doesn't match at all" in pending
        "if an error occurs while waiting for more data" in pending
        "if completion occurs while waiting for more data" in pending
        "if a timeout occurs while waiting for more data" in pending
      }
    }
    "expectNoBytes should" - {
      "succeed if nothing was received during the timeout interval" in pending
      "provide a proper error message" - {
        "if more data arrived" in pending
        "if completion occurs" in pending
        "if an error occurs" in pending
      }
    }
    "expectCompletion should" - {
      "succeed if completion occurred" in pending
      "provide a proper error message" - {
        "if more data arrived" in pending
        "if an error occurs" in pending
        "if a timeout occurs" in pending
      }
    }
    "expectError() should" - {
      "succeed if an error occurred" in pending
      "provide a proper error message" - {
        "if more data arrived" in pending
        "if completion occurs" in pending
        "if a timeout occurs" in pending
      }
    }
    "expectError(expected) should" - {
      "succeed if the given error occurs" in pending
      "provide a proper error message" - {
        "if an error occurs that has the same message but doesn't equal the given one" in pending
        "if an error of the right type but with wrong message arrived" in pending
        "if more data arrives" in pending
        "if completion occurs" in pending
        "if a timeout occurs" in pending
      }
    }
  }

  abstract class TestSetup {
    def input: Source[ByteString, Unit]

    val probe = ByteStringSinkProbe()
    input.runWith(probe.sink)
  }
  trait StringChunkedInput extends TestSetup {
    def chunks: Seq[String]

    def input: Source[ByteString, Unit] = Source(chunks.map(ByteString(_, "utf8")).toList)
  }
}
