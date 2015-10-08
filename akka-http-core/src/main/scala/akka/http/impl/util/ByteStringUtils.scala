/*
 * Copyright (C) 2009-2015 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.impl.util

import akka.util.ByteString

object ByteStringUtils {
  def hexdump(numIndent: Int = 2, bytesPerLine: Int = 16, maxLinesPerBlock: Int = 5)(bytes: ByteString): String =
    hexdumpIterator(numIndent, bytesPerLine, maxLinesPerBlock)(bytes).mkString("\n")
  def hexdumpIterator(numIndent: Int = 2, bytesPerLine: Int = 16, maxLinesPerBlock: Int = 5)(bytes: ByteString): Iterator[String] = {
    val maxBytes = bytesPerLine * maxLinesPerBlock
    val indent = " " * numIndent

    def formatBytes(bs: ByteString): Stream[String] = {
      def asHex(b: Byte): String = b formatted "%02X"
      def asASCII(b: Byte): Char =
        if (b >= 0x20 && b < 0x7f) b.toChar
        else '.'

      def formatLine(bs: ByteString): String = {
        val data = bs.toSeq
        val hex = data.map(asHex).mkString(" ")
        val ascii = data.map(asASCII).mkString
        f"$indent%s  $hex%-48s| $ascii"
      }
      def formatBytes(bs: ByteString): String =
        bs.grouped(bytesPerLine).map(formatLine).mkString("\n")

      val prefix = s"${indent}ByteString(${bs.size} bytes)"

      if (bs.size <= maxBytes * 2) Stream(prefix + "\n") lazy_+ formatBytes(bs)
      else
        Stream(s"$prefix first + last $maxBytes:\n") lazy_+
          formatBytes(bs.take(maxBytes)) lazy_+
          s"\n$indent                    ... [${bs.size - (maxBytes * 2)} bytes omitted] ...\n" lazy_+
          formatBytes(bs.takeRight(maxBytes))
    }

    formatBytes(bytes).toIterator
  }

  implicit class StreamExtensions[T](val str: Stream[T]) extends AnyVal {
    def lazy_+(next: ⇒ T): Stream[T] = str.append(next :: Nil)
  }
}
