name := """minesweeper"""
organization := "eugenia"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += jdbc
libraryDependencies ++= Seq("org.postgresql" % "postgresql" % "9.3-1102-jdbc41")

lazy val doobieVersion = "0.9.0"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2" % doobieVersion
)

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

//assemblyMergeStrategy in assembly := {
//  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
//  case x                             => MergeStrategy.concat
//}
