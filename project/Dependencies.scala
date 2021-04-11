import sbt._

object Dependencies {
  object Version {
    lazy val scalaTest   = "3.2.2"
    lazy val akka        = "2.6.13"
    lazy val streamKafka = "2.0.7"
    lazy val shapeless   = "2.3.3"
    lazy val cats        = "2.1.1"
    lazy val mouse       = "1.0.2"
    lazy val circe       = "0.12.3"
    lazy val doobie      = "0.12.1"
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
    "io.circe"          %% "circe-parser"      % Version.circe
  )

  lazy val testDependencies: Seq[ModuleID] = Seq(
    "org.scalatest"     %% "scalatest"       % Version.scalaTest,
    "org.scalatestplus" %% "scalacheck-1-15" % "3.2.5.0" % "test"
  )
}
