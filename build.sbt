name := "learning-scala"

version := "1.0-SNAPSHOT"


scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-stream_2.11" % "2.4.10"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

