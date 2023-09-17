name := "MinTrianglePath"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.13.1"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"

// available for 2.12, 2.13
//libraryDependencies += "co.fs2" %% "fs2-core" % "2.4.0" // For cats 2 and cats-effect 2

// optional I/O library
//libraryDependencies += "co.fs2" %% "fs2-io" % "2.4.0"

// optional reactive streams interop
//libraryDependencies += "co.fs2" %% "fs2-reactive-streams" % "2.4.0"

// optional experimental library
//libraryDependencies += "co.fs2" %% "fs2-experimental" % "2.4.0"

// scalac options come from the sbt-tpolecat plugin so need to set any here

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)

lazy val app = (project in file("."))
  .settings(
    assembly / mainClass := Some("MainApp"),
    assembly / assemblyJarName := name.value +".jar",
  )