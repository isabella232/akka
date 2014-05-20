package akka.http

import scala.io.{ Codec, Source }
import java.io.{ FileWriter, File }
import akka.http.GenerationConfig._
import scala.Some
import java.security.MessageDigest

object HeaderCodeGeneration {
  def generateJApi(header: HttpHeaderDefinition): Unit =
    generateTo(targetJavaHeaderFile(header), headerJapiTemplate(header))

  def headerJapiTemplate(header: HttpHeaderDefinition): String = {
    import header._

    s"""package akka.http.model.japi.headers;
       |
       |${neededImportStatements("\n")}
       |
       |/**
       | *  Model for the `$name` header.${documentation.map(_.split("\n").map("\n *  " + _).mkString).getOrElse("")}${specSource.map("\n *  Specification: " + _).getOrElse("")}
       | */
       |public abstract class $javaIdentifier extends akka.http.model.HttpHeader {
       |    ${javaParameterGetterDefinitions("\n    ")}
       |
       |    public static $javaIdentifier create(${javaParameterDefinitions(", ")}) {
       |        return new akka.http.model.headers.$scalaIdentifierInJava(${javaParameterToScala(", ")});
       |    }
       |}
       |""".stripMargin
  }

  def generateHeadersScala(headers: Seq[HttpHeaderDefinition]): Unit = {
    val result = Source.fromFile(headersScalaTemplateFile).mkString + headers.map(scalaHeaderDefinitionTemplate).mkString
    generateTo(headersScalaTargetFile, result)
  }

  def scalaHeaderDefinitionTemplate(header: HttpHeaderDefinition): String = {
    import header._

    val extraConstructor = header.parameters.toList match {
      case HeaderParameter(name, SeqType(tpe), _) :: Nil ⇒
        Some(s"def apply($name: ${tpe.scalaType}*): $scalaIdentifier = apply(immutable.Seq($name: _*))")
      case _ ⇒ None
    }
    val extraRenderers = header.parameters.flatMap {
      case HeaderParameter(name, SeqType(tpe), _) ⇒
        val sep = header.seqRendererSeparator match {
          case Some("none") ⇒ None
          case Some(sep)    ⇒ Some(s"Renderer.seqRenderer[${tpe.scalaType}](separator = $sep)")
          case None         ⇒ Some(s"Renderer.defaultSeqRenderer[${tpe.scalaType}]")
        }
        sep.map(sep ⇒ s"implicit val ${name}Renderer = $sep // cache")
      case _ ⇒ None
    }
    val extraCompanionLines = header.extraObjectContent.toSeq ++ (extraConstructor.toSeq ++ extraRenderers).map("  " + _)
    val companionBody =
      if (extraCompanionLines.isEmpty) ""
      else " {\n" + extraCompanionLines.mkString("\n") + "\n}"

    val extraRenderImports =
      header.parameters.flatMap {
        case HeaderParameter(name, SeqType(tpe), _) if !header.seqRendererSeparator.exists(_ == "none") ⇒ Some(s"import $scalaIdentifier.${name}Renderer")
        case _ ⇒ None
      }
    val extraClassBody = extraRenderImports.map("  " + _).mkString("\n") + "\n" + header.extraClassContent.getOrElse("")

    val renderCode = rendering.getOrElse("r ~~ " + parameters.map(_.name).mkString(" ~ "))

    val extraJavaMethods = header.parameters.flatMap {
      case p @ HeaderParameter(name, SeqType(tpe), _) ⇒ Some(s"def ${p.javaMethodName} = $name.asJava")
      case p @ HeaderParameter(name, MapType(keyTpe, valueTpe), _) ⇒ Some(s"def ${p.javaMethodName} = $name.asJava")
      case p @ HeaderParameter(name, UriType, _) ⇒ Some(s"def ${p.javaMethodName} = $name.asJava")
      case _ ⇒ None
    }
    val extraJavaApi =
      if (extraJavaMethods.isEmpty) ""
      else
        s"""
           |  // Java API
           |${extraJavaMethods.map("  " + _).mkString("\n")}
           |""".stripMargin

    s"""${specSource.map("// " + _ + "\n").getOrElse("")}object $scalaIdentifier extends ModeledCompanion$companionBody
       |final case class $scalaIdentifier($scalaParameterDefinitions) extends japi.headers.$javaIdentifier with ModeledHeader {
       |$extraClassBody  def renderValue[R <: Rendering](r: R): r.type = $renderCode
       |  protected def companion = $scalaIdentifier
       |$extraJavaApi}
       |
       |""".stripMargin
  }

  def targetJavaHeaderFile(header: HttpHeaderDefinition): File =
    new File(basedir, s"java/akka/http/model/japi/headers/${header.javaIdentifier}.java")

  def headersScalaTargetFile: File =
    new File(basedir, "scala/akka/http/model/headers/headers.scala")
  def headersScalaTemplateFile: File =
    new File(basedir, "header-templates/headers.scala.template")

  def generateJavaHeaderConstructors(headers: Seq[HttpHeaderDefinition]): Unit =
    generateTo(headerConstructorsTargetFile, javaHeaderConstructorsTemplate(headers))

  def javaHeaderConstructorsTemplate(headers: Seq[HttpHeaderDefinition]): String = {
    s"""package akka.http.model.japi
       |
       |import akka.http.model
       |import akka.http.model.japi
       |
       |import JavaMapping.Implicits._
       |
       |trait HeaderConstructors {
       |  ${headers.map(javaHeaderConstructor).mkString("\n\n  ")}
       |}""".stripMargin
  }

  def javaHeaderConstructor(header: HttpHeaderDefinition): String = {
    import header._
    s"""def $javaIdentifier(${javaParameterInScalaDefinitions(", ")}): japi.headers.$javaIdentifier =
       |    model.headers.$scalaIdentifier(${parameters.map(asScala).mkString(", ")})"""
  }

  def headerConstructorsTargetFile: File =
    new File(basedir, "scala/akka/http/model/japi/HeaderConstructors.scala")

  def generateTo(target: File, result: String): Unit = {
    if (!target.exists() || isNew(target, result)) {
      println(s"Regenerating ${target.getPath}")
      save()
    }

    def save(): Unit = {
      val ow = new FileWriter(target)
      try {
        ow.write(result)
      } finally ow.close()
    }
  }

  def isNew(target: File, newContent: String): Boolean = {
    val oldContent = Source.fromFile(target)(Codec.UTF8).mkString
    val oldHash = md5(oldContent)
    val newHash = md5(newContent)
    oldHash != newHash
  }

  def md5(str: String) = {
    val digest = MessageDigest.getInstance("MD5")
    digest.digest(str.getBytes("utf8")).toSeq
  }

  def asScala(param: HeaderParameter): String =
    param.tpe match {
      case _: PrimitiveType ⇒ param.name
      case _                ⇒ s"${param.name}.asScala"
    }
}
