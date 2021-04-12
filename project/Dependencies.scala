import sbt._

object Dependencies {
  object Version {
    val scalaTest      = "3.2.2"
    val scalaCheck     = "3.2.5.0"
    val akka           = "2.6.13"
    val streamKafka    = "2.0.7"
    val shapeless      = "2.3.3"
    val cats           = "2.1.1"
    val mouse          = "1.0.2"
    val circe          = "0.12.3"
    val circeConfig    = "0.8.0"
    val doobie         = "0.12.1"
    val testContainers = "0.39.3"
    val logback        = "1.2.3"
    val log4cats       = "1.1.1"

  }

  lazy val commonDependencies: Seq[ModuleID] = Seq(
    "com.typesafe.akka" %% "akka-stream"       % Version.akka,
    "com.typesafe.akka" %% "akka-stream-kafka" % Version.streamKafka,
    "com.chuusai"       %% "shapeless"         % Version.shapeless,
    "org.typelevel"     %% "cats-core"         % Version.cats,
    "org.typelevel"     %% "mouse"             % Version.mouse,
    "org.tpolecat"      %% "doobie-hikari"     % Version.doobie,
    "org.tpolecat"      %% "doobie-postgres"   % Version.doobie,
    "io.circe"          %% "circe-core"        % Version.circe,
    "io.circe"          %% "circe-generic"     % Version.circe,
    "io.circe"          %% "circe-parser"      % Version.circe,
    "io.circe"          %% "circe-config"      % Version.circeConfig,
    "ch.qos.logback"     % "logback-classic"   % Version.logback,
    "io.chrisdavenport" %% "log4cats-slf4j"    % Version.log4cats
  )

  lazy val testDependencies: Seq[ModuleID] = Seq(
    "org.scalatest"     %% "scalatest"                       % Version.scalaTest,
    "org.scalatestplus" %% "scalacheck-1-15"                 % Version.scalaCheck,
    "com.dimafeng"      %% "testcontainers-scala-scalatest"  % Version.testContainers,
    "com.dimafeng"      %% "testcontainers-scala-postgresql" % Version.testContainers,
    "com.dimafeng"      %% "testcontainers-scala-kafka"      % Version.testContainers
  ).map(_ % "it,test")

  lazy val protobufDependencies: Seq[ModuleID] = Seq(
    "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
  )
}
