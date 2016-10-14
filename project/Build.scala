import sbt._
import Keys._
import sbtassembly.AssemblyKeys._
import sbtassembly.{MergeStrategy, PathList}
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object BuildSettings {

  val scalaBuildVersion = "2.11.8"

  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := "org.elastic",
    scalacOptions ++= Seq(),
    scalaVersion := scalaBuildVersion,
    crossScalaVersions := Seq(),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
  )
}

object MyBuild extends Build {
  import BuildSettings._

  // Dependencies

  val githubName = "elastic_graph_core"
  val apiRoot = "https://alex-at-home.github.io"
  val docVersion = "current"

  val utestVersion = "0.4.3"
  lazy val utestJvmDeps = "com.lihaoyi" %% "utest" % utestVersion % "test"

  lazy val simpleScalaHttpServer = "com.tumblr" %% "colossus" % "0.8.1" % "test"

  val rest_client_library_branch = "" //("#$branch" or "" for master)
  val rest_client_library_uri =
  uri(s"https://github.com/Alex-At-Home/rest_client_library.git$rest_client_library_branch")

  val elastic_client_library_branch = "" //("#$branch" or "" for master)
  val elastic_client_library_uri =
  uri(s"https://github.com/Alex-At-Home/elasticsearch_scala_driver.git$elastic_client_library_branch")

  val esGraphDbVersion = "0.1-SNAPSHOT"

  // Tightly coupled deps

  lazy val elastic_client_libraryJVM = ProjectRef(elastic_client_library_uri, "elasticsearch_scala_coreJVM")
  lazy val elastic_client_libraryJS = ProjectRef(elastic_client_library_uri, "elasticsearch_scala_coreJS")

  // Projects

  lazy val root = Project(
    "root",
    file("."),
    settings = buildSettings
  )

  lazy val elastic_graph_core = crossProject
    .in(file("elastic_graph_core"))
    .settings(
      buildSettings ++ Seq(
        name := "Elastic GraphDB Core",
        version := esGraphDbVersion,
        apiURL := Some(url(s"$apiRoot/$githubName/$docVersion/")),
        autoAPIMappings := true,
        libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaBuildVersion,
        libraryDependencies += "com.lihaoyi" %%% "utest" % utestVersion % "test",
        testFrameworks += new TestFramework("utest.runner.Framework")
      ): _*)
    .jvmSettings()
    .jsSettings(
      scalaJSUseRhino in Global := false
    )
  lazy val elastic_graph_coreJVM = elastic_graph_core.jvm dependsOn elastic_client_libraryJVM
  lazy val elastic_graph_coreJS = elastic_graph_core.js dependsOn elastic_client_libraryJS


}
