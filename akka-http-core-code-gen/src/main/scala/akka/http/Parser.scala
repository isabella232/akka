package akka.http

import org.parboiled2._
import scala.io.Source
import java.io.FileReader
import org.parboiled2.ParseError

import HeaderType._

import java.io.File
import scala.io.Source
import scala.util.Try

case class InfoLine[T](name: String, value: T)
class HeaderDefinitionParser(val input: ParserInput) extends Parser {
  def document: Rule1[Seq[InfoLine[_]]] = rule {
    //name /*~ parameters ~ spec ~ documentation*/ ~> ((name: String) ⇒ HeaderDefinition(name, null, null, null))
    oneOrMore(infoLine) ~ zeroOrMore(CharPredicate("\n ")) ~ EOI
  }
  def infoLine: Rule1[InfoLine[_]] = rule { name | spec | parameters | documentation | extraObject | extraClass | rendering | seqRendererSeparator }

  def name: Rule1[InfoLine[String]] = rule {
    linePrefix("name") ~ restOfLine ~> (InfoLine[String] _)
  }
  def spec: Rule1[InfoLine[String]] = rule {
    linePrefix("spec") ~ restOfLine ~> (InfoLine[String] _)
  }
  def seqRendererSeparator: Rule1[InfoLine[String]] = rule {
    linePrefix("seqRendererSeparator") ~ restOfLine ~> (InfoLine[String] _)
  }
  def parameters: Rule1[InfoLine[Seq[HeaderParameter]]] = rule {
    linePrefix("parameters") ~ '\n' ~ oneOrMore(parameter) ~> (InfoLine[Seq[HeaderParameter]] _)
  }
  def parameter: Rule1[HeaderParameter] = rule {
    oneOrMore(space) ~ identifier ~ ":" ~ ws ~ headerType ~ ws ~ ('=' ~ ws ~ restOfLine ~> (Some(_)) | lineEndOrEOI ~ push(None)) ~> HeaderParameter
  }
  def headerType: Rule1[HeaderType] = rule {
    (mapType | seqType | eitherType | simpleType)
  }
  def mapType: Rule1[MapType] = rule {
    "Map[" ~ ws ~ simpleType ~ ',' ~ ws ~ simpleType ~ ']' ~> MapType
  }
  def eitherType: Rule1[EitherType] = rule {
    "Either[" ~ ws ~ simpleType ~ ',' ~ ws ~ simpleType ~ ']' ~> EitherType
  }
  def seqType: Rule1[SeqType] = rule {
    "Seq[" ~ ws ~ simpleType ~ ']' ~> SeqType
  }

  def simpleType: Rule1[SimpleType] = rule {
    ("headers." ~ identifier ~ ws ~> HeaderPackageType) |
      predefinedType(StringType) |
      predefinedType(IntType) |
      predefinedType(LongType) |
      predefinedType(BooleanType) |
      predefinedType(UriType) |
      predefinedType(DateTimeType) |
      (identifier ~ ws ~> BasePackageType)
  }

  def documentation: Rule1[InfoLine[String]] = rule {
    linePrefix("docs") ~ '\n' ~ (zeroOrMore(indentedLine) ~> (_.mkString("\n"))) ~> (InfoLine[String] _)
  }
  def indentedLine: Rule1[String] = rule {
    oneOrMore(space) ~ restOfLine
  }
  def fullIndentedLine: Rule1[String] = rule {
    capture(oneOrMore(space) ~ zeroOrMore(withoutLineEnd) ~ lineEndOrEOI)
  }
  def extraObject: Rule1[InfoLine[String]] = rule {
    linePrefix("object") ~ '\n' ~ fullIndentedLines ~> (InfoLine[String] _)
  }
  def extraClass: Rule1[InfoLine[String]] = rule {
    linePrefix("class") ~ '\n' ~ fullIndentedLines ~> (InfoLine[String] _)
  }
  def fullIndentedLines: Rule1[String] = rule {
    zeroOrMore(fullIndentedLine) ~> ((_: Seq[String]).mkString)
  }

  def rendering: Rule1[InfoLine[String]] = rule {
    linePrefix("rendering") ~ restOfLine ~> (InfoLine[String] _)
  }

  def predefinedType(tpe: SimpleType): Rule1[SimpleType] = rule {
    tpe.name ~ ws ~ push(tpe)
  }
  def linePrefix(name: String): Rule1[String] = rule {
    name ~ ":" ~ ws ~ push(name)
  }

  def restOfLine: Rule1[String] = rule { capture(zeroOrMore(withoutLineEnd)) ~ lineEndOrEOI }
  def identifier: Rule1[String] = rule { capture(oneOrMore(CharPredicate.AlphaNum)) }
  def ws = rule { zeroOrMore(' ') }
  def space: CharPredicate = CharPredicate(' ')
  def lineEndOrEOI = CharPredicate("\n") ++ CharPredicate(EOI)
  def withoutLineEnd: CharPredicate = CharPredicate.All -- CharPredicate("\n")
}

object HeaderDefinitionParser {

  def convertInfos(infos: Seq[InfoLine[_]]): HttpHeaderDefinition = {
    val map = infos.map(i ⇒ i.name -> i.value).toMap
    HttpHeaderDefinition(
      map("name").asInstanceOf[String],
      map("parameters").asInstanceOf[Seq[HeaderParameter]],
      map.get("rendering").map(_.asInstanceOf[String]),
      map.get("seqRendererSeparator").map(_.asInstanceOf[String]),
      map.get("spec").map(_.asInstanceOf[String]),
      map.get("docs").map(_.asInstanceOf[String]),
      map.get("object").map(_.asInstanceOf[String]),
      map.get("class").map(_.asInstanceOf[String]))
  }

  def parse(file: File): Try[HttpHeaderDefinition] = {
    val content = Source.fromFile(file).iter.mkString
    val parser = new HeaderDefinitionParser(content)
    val res = parser.document.run().map(convertInfos)
    res.failed.map {
      case error: ParseError ⇒
        println(s"Parsing of $file failed")
        println(parser.formatError(error, showTraces = true))
      case e ⇒
        println(s"Parsing of $file failed")
        e.printStackTrace
    }
    res
  }
}