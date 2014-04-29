package akka

package object http {
  implicit class UpperCasedString(val str: String) extends AnyVal {
    def upperCased: String =
      str.take(1).toUpperCase + str.drop(1)
  }
}