name := """akka-dice"""

version := "1.0"

scalaVersion := "2.11.6"

resolvers += "spray repo" at "http://repo.spray.io"
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.scalaz" %% "scalaz-core" % "7.1.1",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "org.scalatest" % "scalatest_2.11" % "2.2.6" % "test"
  )
}

Revolver.settings