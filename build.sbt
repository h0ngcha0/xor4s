import java.nio.file.Files

enablePlugins(JavaAppPackaging)

name := "xor4s"
organization := "it.softfork"
version := "0.1.0"

scalaVersion in ThisBuild := "2.13.1"
crossScalaVersions in ThisBuild := Seq("2.12.9", "2.13.0")

scalacOptions := Seq(
  "-unchecked",
  "-feature",
  "-deprecation",
  "-Ywarn-dead-code",
  "-Ywarn-extra-implicit",
  "-Xfatal-warnings",
  "-Ywarn-extra-implicit",
  "-Ywarn-unused:locals",
  "-Ywarn-unused:patvars",
  "-Ywarn-unused:privates",
//  "-Ywarn-unused:imports",
  "-Ymacro-annotations"
)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.1.0",
  "com.github.mpilquist" %% "simulacrum" % "0.19.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalatest" %% "scalatest" % "3.1.0" % "test",
  "com.madgag.spongycastle" % "core" % "1.58.0.0" % "test",
  "com.lihaoyi" %% "pprint" % "0.5.8"
)

resolvers ++= Seq(
  Resolver.jcenterRepo
)

enablePlugins(JavaAppPackaging)

Revolver.enableDebugging(port = 5050, suspend = false)

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
