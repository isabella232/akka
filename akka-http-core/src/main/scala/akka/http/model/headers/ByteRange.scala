/**
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.model
package headers

import akka.http.util.{ Rendering, ValueRenderable }
import akka.japi.Option
import java.lang

sealed abstract class ByteRange extends japi.headers.ByteRange with ValueRenderable {
  // Java API
  def getSliceFirst: Option[lang.Long] = Option.none
  def getSliceLast: Option[lang.Long] = Option.none
  def getOffset: Option[lang.Long] = Option.none
  def getSuffixLength: Option[lang.Long] = Option.none

  def isSlice: Boolean = false
  def isFromOffset: Boolean = false
  def isSuffix: Boolean = false
}

object ByteRange {
  def apply(first: Long, last: Long) = Slice(first, last)
  def fromOffset(offset: Long) = FromOffset(offset)
  def suffix(length: Long) = Suffix(length)

  case class Slice(first: Long, last: Long) extends ByteRange {
    require(0 <= first && first <= last, "first must be >= 0 and <= last")
    def render[R <: Rendering](r: R): r.type = r ~~ first ~~ '-' ~~ last

    // Java API
    override def isSlice: Boolean = true
    override def getSliceFirst: Option[lang.Long] = Option.some(first)
    override def getSliceLast: Option[lang.Long] = Option.some(last)
  }

  case class FromOffset(offset: Long) extends ByteRange {
    require(0 <= offset, "offset must be >= 0")
    def render[R <: Rendering](r: R): r.type = r ~~ offset ~~ '-'

    override def isFromOffset: Boolean = true
    override def getOffset: Option[lang.Long] = Option.some(offset)
  }

  case class Suffix(length: Long) extends ByteRange {
    require(0 <= length, "length must be >= 0")
    def render[R <: Rendering](r: R): r.type = r ~~ '-' ~~ length

    override def isSuffix: Boolean = true
    override def getSuffixLength: Option[lang.Long] = Option.some(length)
  }
}