package akka.http.model.japi

import akka.http.model

import JavaMapping.Implicits._

object Headers extends HeaderConstructors {
  def RawHeader(name: String, value: String): headers.RawHeader =
    model.headers.RawHeader(name, value)
}
