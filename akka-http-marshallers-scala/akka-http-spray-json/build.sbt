import akka._
import com.typesafe.tools.mima.plugin.MimaKeys

AkkaBuild.defaultSettings

Formatting.formatSettings

OSGi.httpSprayJson

Dependencies.httpSprayJson

MimaKeys.previousArtifacts := akkaStreamAndHttpPreviousArtifacts("akka-http-spray-json").value
