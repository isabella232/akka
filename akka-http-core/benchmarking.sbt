fork in run := true

connectInput in run := true

javaOptions in run ++= Seq("-verbose:gc", "-XX:+PreserveFramePointer")

javaHome in run := Some(file("/mnt/hd/bulk-data/openjdk-build/jdk1.8.0_60"))