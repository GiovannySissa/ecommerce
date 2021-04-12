import Dependencies._
import org.scalafmt.sbt.ScalafmtPlugin.scalafmtConfigSettings

lazy val commonSettings = Seq(
  scalaVersion := "2.13.4",
  version := "1.0.0",
  fork in IntegrationTest := true,
  organization := "io.gs",
  name := "ecommerce",
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases")
  ),
  libraryDependencies ++= commonDependencies ++ testDependencies,
  addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.11.3" cross CrossVersion.full),
  addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1")
)

lazy val ItConfig = config("it") extend (IntegrationTest, Test)

lazy val testSettings =
  inConfig(ItConfig)(Defaults.testSettings ++ scalafmtConfigSettings)

lazy val core =
  project
    .configs(IntegrationTest)
    .settings(commonSettings, name += "-core", testSettings)
    .dependsOn(protobuf)

lazy val protobuf =
  project.settings(
    scalaVersion := "2.13.4",
    version := "1.0.0",
    organization := "io.gs",
    name := "ecommerce-protobuf",
    resolvers += Resolver.mavenLocal,
    Compile / PB.targets := Seq(
      scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
    ),
    libraryDependencies ++= protobufDependencies
  )

lazy val ingestor = project
  .configs(IntegrationTest)
  .settings(commonSettings, name += "-ingestor", testSettings)
  .dependsOn(core, core % "compile->compile;test->test", core % "compile->compile;test->it")

lazy val root = (project in file("."))
  .aggregate(core, ingestor, protobuf)
  .settings(name := "root", commonSettings)
