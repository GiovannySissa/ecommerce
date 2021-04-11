import Dependencies._

lazy val commonSettings = Seq(
  scalaVersion     := "2.13.4",
  version          := "1.0.0",
  organization     := "io.gs",
  name := "ecommerce",
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases")
  ),
  libraryDependencies ++= commonDependencies ++ testDependencies ,
  addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.11.3" cross CrossVersion.full),
  addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1")
)

lazy val core = project.settings(commonSettings, name+= "-core")

lazy val ingestor = project.settings(commonSettings, name += "-ingestor") .dependsOn(core, core % "compile->compile;test->test", core % "compile->compile;test->it")