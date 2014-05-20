package akka.http

import java.io.File
import akka.http.GenerationConfig._

object GenerateHeadersApp extends App {
  val definitions =
    new File(basedir, "header-templates").listFiles().filter(_.getName.endsWith(".header")).toSeq
      .map(HeaderDefinitionParser.parse(_).toOption.get)
      .sortBy(_.name)

  definitions.foreach(HeaderCodeGeneration.generateJApi)
  HeaderCodeGeneration.generateHeadersScala(definitions)
}
