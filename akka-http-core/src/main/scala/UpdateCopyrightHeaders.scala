/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

import java.io.{ FileOutputStream, File }
import scala.io.Source
import scala.util.matching.Regex

object UpdateCopyrightHeaders extends App {
  val encoding = "utf-8"
  val copyrightLines = "Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>".split("\n")
  val srcDir = new File("akka-http-core/src/")

  def updateCopyright(file: File): Unit = {
    val contents = Source.fromFile(file, encoding).mkString
    val newContents = updateCopyright(contents)
    if (newContents != contents) {
      val newFile = File.createTempFile("copyrighted", ".txt")
      val os = new FileOutputStream(newFile)
      os.write(newContents.getBytes(encoding))
      os.close()
      newFile.renameTo(file)
    }
  }

  val formattedCopyrightLines = "/**\n" + Regex.quoteReplacement(copyrightLines.map(" * " + _).mkString("\n")) + "\n */\n\n$2"

  val MaybeComment = """^(?s:(\s*/\*(?:(?!\*/).)*\*/\n{0,2})?(\s*.*))""".r

  def updateCopyright(content: String): String =
    MaybeComment.replaceAllIn(content, formattedCopyrightLines)

  def recursiveFilesIn(filter: File ⇒ Boolean)(dir: File): Seq[File] =
    if (dir.getName != ".." && dir.getName != ".") {
      val (dirs, files) = dir.listFiles().partition(_.isDirectory)
      files.filter(filter).toVector ++ dirs.flatMap(recursiveFilesIn(filter))
    } else Vector.empty

  def withExtension(ext: String): File ⇒ Boolean = _.getName.endsWith(s".$ext")
  implicit class AddPredicateOps[T](val f: T ⇒ Boolean) extends AnyVal {
    def ||(g: T ⇒ Boolean): T ⇒ Boolean = t ⇒ f(t) || g(t)
  }

  recursiveFilesIn(withExtension("scala") || withExtension("java"))(srcDir).foreach(updateCopyright)
}
