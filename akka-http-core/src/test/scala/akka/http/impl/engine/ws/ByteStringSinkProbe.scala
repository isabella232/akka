/*
 * Copyright (C) 2009-2015 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.impl.engine.ws

import akka.actor.ActorSystem
import akka.http.impl.util.ByteStringUtils
import akka.stream.scaladsl.{ Source, Sink }
import akka.stream.testkit.TestSubscriber
import akka.stream.testkit.TestSubscriber._
import akka.util.ByteString

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration

trait ByteStringSinkProbe {
  def sink: Sink[ByteString, Unit]

  def expectBytes(length: Int): ByteString
  def expectBytes(expected: ByteString): Unit

  def expectUtf8EncodedString(string: String): Unit

  def expectNoBytes(): Unit
  def expectNoBytes(timeout: FiniteDuration): Unit

  def expectComplete(): Unit
  def expectError(): Throwable
  def expectError(cause: Throwable): Unit
}

object ByteStringSinkProbe {
  case class UnexpectedEventDuringByteCollection(event: SubscriberEvent, alreadyReceived: ByteString)(msg: String, cause: Throwable = null) extends AssertionError(msg, cause)
  case class DifferingInput(firstMismatchPosition: Int, matchingPrefix: ByteString, mismatchingSuffix: ByteString, expectedSuffix: ByteString)(msg: String) extends AssertionError(msg)

  def apply()(implicit system: ActorSystem): ByteStringSinkProbe =
    new ByteStringSinkProbe {
      val probe = TestSubscriber.probe[ByteString]()
      val sink: Sink[ByteString, Unit] = Sink(probe)

      def expectNoBytes(): Unit = {
        probe.ensureSubscription()
        probe.expectNoMsg()
      }
      def expectNoBytes(timeout: FiniteDuration): Unit = {
        probe.ensureSubscription()
        probe.expectNoMsg(timeout)
      }

      var inBuffer = ByteString.empty
      @tailrec def expectBytes(length: Int): ByteString =
        if (inBuffer.size >= length) {
          val res = inBuffer.take(length)
          inBuffer = inBuffer.drop(length)
          res
        } else {
          try inBuffer ++= probe.requestNext()
          catch {
            case UnexpectedEventError(e @ OnError(cause)) ⇒
              val stillMissing = length - inBuffer.size
              throw UnexpectedEventDuringByteCollection(e, inBuffer)(s"Got error while waiting for $stillMissing more bytes (out of $length): '${cause.getMessage}'. Already received bytes:\n" + ByteStringUtils.hexdump()(inBuffer), cause)
            case UnexpectedEventError(c @ OnComplete) ⇒
              val stillMissing = length - inBuffer.size
              throw UnexpectedEventDuringByteCollection(c, inBuffer)(s"Truncation. Got completion while waiting for $stillMissing more bytes (out of $length). Already received bytes:\n" + ByteStringUtils.hexdump()(inBuffer))
            case TimeoutException(after) ⇒
              val stillMissing = length - inBuffer.size
              throw new TimeoutException(after)(s"Timeout after $after while waiting for $stillMissing more bytes (out of $length). Already received bytes:\n" + ByteStringUtils.hexdump()(inBuffer))
          }
          expectBytes(length)
        }

      import ByteStringUtils.hexdump
      def hex(bs: ByteString): String = hexdump()(bs)

      def expectBytes(expected: ByteString): Unit = {
        @tailrec def rec(pos: Int): Unit =
          if (pos == expected.length) () // finished
          else if (inBuffer.nonEmpty) {
            val next = inBuffer(0)
            if (next == expected(pos)) {
              inBuffer = inBuffer.drop(1)
              rec(pos + 1)
            } else
              throw DifferingInput(pos, expected.take(pos), inBuffer, expected.drop(pos))(
                s"Unexpected data received at position $pos:\nMatching prefix: ${hex(expected.take(pos))}\nExpected suffix: ${hex(expected.drop(pos))}\nActual suffix: ${hex(inBuffer)}")
          } else {
            try inBuffer ++= probe.requestNext()
            catch {
              case UnexpectedEventError(e @ OnError(cause)) ⇒
                val length = expected.length
                val stillMissing = length - pos
                val prefix = expected.take(pos)
                throw UnexpectedEventDuringByteCollection(e, inBuffer)(s"Got error while waiting for $stillMissing more bytes (out of $length): '${cause.getMessage}'. Already matching prefix:\n" + hex(prefix), cause)
              case UnexpectedEventError(c @ OnComplete) ⇒
                val length = expected.length
                val stillMissing = length - pos
                val prefix = expected.take(pos)
                throw UnexpectedEventDuringByteCollection(c, inBuffer)(s"Truncation. Got completion while waiting for $stillMissing more bytes (out of $length). Already matching prefix:\n" + hex(prefix))
              case TimeoutException(after) ⇒
                val length = expected.length
                val stillMissing = length - pos
                val prefix = expected.take(pos)
                throw new TimeoutException(after)(s"Timeout after $after while waiting for $stillMissing more bytes (out of $length). Already matching prefix:\n" + hex(prefix))
            }
            rec(pos)
          }

        rec(0)
      }

      def expectUtf8EncodedString(string: String): Unit =
        expectBytes(ByteString(string, "utf8"))

      def expectComplete(): Unit = probe.expectComplete()
      def expectError(): Throwable = probe.expectError()
      def expectError(cause: Throwable): Unit = probe.expectError(cause)
    }
}
