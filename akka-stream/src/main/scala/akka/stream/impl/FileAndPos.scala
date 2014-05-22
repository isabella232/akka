package akka.stream.impl

import scala.language.experimental.macros

import scala.reflect.macros.Context

case class FileAndPos(file: String, line: Int)

object FileAndPos {
  implicit def create: FileAndPos = macro fileAndPosImpl

  def fileAndPosImpl(c: Context): c.Expr[FileAndPos] = {
    import c.universe._

    val pos = c.enclosingPosition
    val file = c.literal(pos.source.file.name)
    val line = c.literal(pos.line)

    reify(FileAndPos(file.splice, line.splice))
  }
}
