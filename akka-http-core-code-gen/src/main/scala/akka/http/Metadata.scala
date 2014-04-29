package akka.http

sealed trait HeaderType {
  def javaType: String
  def javaTypeAsScala: String
  def javaMethodName(name: String): String

  def scalaType: String

  def baseTypeNames: Set[String]
}
case class SeqType(of: SimpleType) extends HeaderType {
  def javaType: String = s"Iterable<${of.javaType}>"
  def javaTypeAsScala: String = s"${of.javaTypeAsScala}*" //s"java.lang.Iterable[${of.javaTypeAsScala}]"
  def javaMethodName(name: String): String = s"get${name.upperCased}"

  def scalaType: String = s"immutable.Seq[${of.scalaType}]"

  def baseTypeNames: Set[String] = of.baseTypeNames
}
case class MapType(keyType: SimpleType, valueType: SimpleType) extends HeaderType {
  def javaType: String = s"java.util.Map<${keyType.javaType}, ${valueType.javaType}>"
  def javaTypeAsScala: String = s"java.util.Map[${keyType.javaTypeAsScala}, ${valueType.javaTypeAsScala}]"
  def javaMethodName(name: String): String = s"get${name.upperCased}"

  def scalaType: String = s"Map[${keyType.scalaType}, ${valueType.scalaType}]"

  def baseTypeNames: Set[String] = keyType.baseTypeNames ++ valueType.baseTypeNames
}
case class EitherType(leftType: SimpleType, rightType: SimpleType) extends HeaderType {
  def javaType: String = "Object"
  def javaTypeAsScala: String = "AnyRef"

  def javaMethodName(name: String): String = name

  def scalaType: String = s"Either[${leftType.scalaType}, ${rightType.scalaType}]"

  def baseTypeNames: Set[String] = leftType.baseTypeNames ++ rightType.baseTypeNames
}
sealed trait SimpleType extends HeaderType {
  def name: String

  def javaType: String = name
  def javaTypeAsScala: String = name
  def javaMethodName(name: String): String = name

  def scalaType: String = name
}
case class BaseType(name: String) extends SimpleType {
  def baseTypeNames: Set[String] = Set.empty
}
case class PrimitiveType(name: String) extends SimpleType {
  def baseTypeNames: Set[String] = Set.empty

  override def javaType: String = name.toLowerCase()
}
case class BasePackageType(name: String) extends SimpleType {
  def baseTypeNames: Set[String] = Set(name)
}
case class HeaderPackageType(name: String) extends SimpleType {
  override def javaTypeAsScala: String = s"headers.$name"
  def baseTypeNames: Set[String] = Set.empty
}
case class HeaderParameter(name: String, tpe: HeaderType, defaultValue: Option[String]) {
  def javaGetterDefinition: String =
    s"${tpe.javaType} $javaMethodName();"

  def javaDefinitionInScala: String =
    s"$name: ${tpe.javaTypeAsScala}"

  def javaMethodName = tpe.javaMethodName(name)

  def scalaDefinition: String =
    s"$name: ${tpe.scalaType}" + defaultValue.map(" = " + _).getOrElse("")
}

object HeaderType {
  val StringType = BaseType("String")
  val IntType = PrimitiveType("Int")
  val LongType = PrimitiveType("Long")
  val BooleanType = PrimitiveType("Boolean")
}

case object UriType extends SimpleType {
  def name: String = "Uri"
  def baseTypeNames: Set[String] = Set(name)

  //override def scalaType: String = super.scalaType
  //override def javaType: String = super.javaType

  override def javaMethodName(name: String): String = s"get${name.upperCased}"
}

case class HttpHeaderDefinition(
  name: String,
  parameters: Seq[HeaderParameter],
  rendering: Option[String],
  seqRendererSeparator: Option[String],
  specSource: Option[String],
  documentation: Option[String],
  extraObjectContent: Option[String],
  extraClassContent: Option[String]) {
  def javaIdentifier: String =
    name.replace('-', '_')

  def javaParameterGetterDefinitions(sep: String): String =
    parameters.map(_.javaGetterDefinition).mkString(sep)

  def javaParameterInScalaDefinitions(sep: String): String =
    parameters.map(_.javaDefinitionInScala).mkString(sep)

  def neededImportStatements(sep: String): String =
    parameters.flatMap(_.tpe.baseTypeNames).toSeq.sorted
      .map(name ⇒ s"import akka.http.model.japi.$name;")
      .mkString(sep)

  def scalaIdentifier: String =
    if (name.contains("-")) s"`$name`"
    else name

  def scalaParameterDefinitions: String =
    parameters.map(_.scalaDefinition).mkString(", ")
}