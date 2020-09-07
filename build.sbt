name := """minesweeper"""
organization := "eugenia"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "eugenia.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "eugenia.binders._"

herokuAppName in Compile := "buscamines"
herokuSkipSubProjects in Compile := false

//herokuFatJar in Compile := Some((assemblyOutputPath in assembly).value)

herokuIncludePaths in Compile := Seq(
  "app",
  "conf/routes",
  "ui",
  "public"
)

packageName in Docker := "buscamines"
dockerExposedPorts ++= Seq(9000, 9001)
//dockerRepository := Some("buscamines")
dockerUsername := Some("jeuges")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.concat
}
