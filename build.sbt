ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.0"

lazy val root = (project in file("."))
  .settings(
    name := "untitled1",
    libraryDependencies ++= Seq(
      "io.getquill"          %% "quill-jdbc-zio" % "4.4.0",
      "org.postgresql"       %  "postgresql"     % "42.5.0",
      "org.http4s" %% "http4s-dsl" % "0.23.15",
      "org.http4s" %% "http4s-blaze-server" % "0.23.12",
      "org.typelevel" %% "cats-core" % "2.8.0",
      "org.typelevel" %% "cats-effect" % "3.3.14",
      "org.http4s" %% "http4s-circe" % "0.23.15",
      "io.circe" %% "circe-generic" % "0.14.2",
      "io.circe" %% "circe-literal" % "0.14.2"
    )
  )
