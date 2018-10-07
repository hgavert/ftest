lazy val packageInfo = Seq(
  organization := "gavert",
  name := "ctest",
  version := "0.1.0"
)

lazy val scalaVersions = Seq(
  scalaVersion := "2.12.6" //"2.12.7"
)

scalacOptions += "-target:jvm-1.8"

lazy val versions = new Object {
  val finatra = "18.9.1"
  val twitterUtils = "18.9.1"
  val guice = "4.0"
  val scalaCheck = "1.14.0"
  val scalaTest = "3.0.0" // "2.2.6" //3.0.5
}

lazy val commonResolvers = Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com",
  "Finatra Repo" at "http://twitter.github.com/finatra"
)

lazy val finatraDependencies = Seq(
  "com.twitter"    %% "finatra-http"    % versions.finatra,
  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests"
)

lazy val commonBuildLibs = Seq(
  //"com.twitter"    %% "finatra-http"    % versions.finatra,
  "com.twitter"    %% "util-collection" % versions.twitterUtils // ei kyl olekaan vielä käytössä
)
lazy val commonTestLibs = Seq(
  "org.scalacheck" %% "scalacheck"      % versions.scalaCheck,
  "org.scalatest"  %% "scalatest"       % versions.scalaTest
).map(_ % Test)

lazy val finatraTestLibs = Seq(
//  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests"
//  "com.twitter" %% "finatra-http" % versions.finatra % Test,
//  "com.twitter" %% "finatra-http" % versions.finatra % Test classifier "tests",
//  "com.twitter" %% "inject-server" % versions.finatra % Test,
//  "com.twitter" %% "inject-server" % versions.finatra % Test classifier "tests"
)


//val finatraTestLibs = Seq(
  //"com.twitter.finatra" %% "finatra-http" % versions.finatra % Test//,
  //"com.twitter.finatra" %% "finatra-http" % versions.finatra % Test classifier "tests"//,
  //"com.twitter.inject" %% "inject-server" % versions.finatra % Test,
  //"com.twitter.inject" %% "inject-server" % versions.finatra % Test classifier "tests"
//)
/*
val finatraTestLibs = Seq(
  "com.twitter.finatra" %% "finatra-http" % versions.finatra % Test,
  "com.twitter.finatra" %% "finatra-jackson" % versions.finatra % Test,
  "com.twitter.inject" %% "inject-server" % versions.finatra % Test,
  "com.twitter.inject" %% "inject-app" % versions.finatra % Test,
  "com.twitter.inject" %% "inject-core" % versions.finatra % Test,
  "com.twitter.inject" %% "inject-modules" % versions.finatra % Test,
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % Test,
  "com.twitter.finatra" %% "finatra-http" % versions.finatra % Test classifier "tests",
  "com.twitter.finatra" %% "finatra-jackson" % versions.finatra % Test classifier "tests",
  "com.twitter.inject" %% "inject-server" % versions.finatra % Test classifier "tests",
  "com.twitter.inject" %% "inject-app" % versions.finatra % Test classifier "tests",
  "com.twitter.inject" %% "inject-core" % versions.finatra % Test classifier "tests",
  "com.twitter.inject" %% "inject-modules" % versions.finatra % Test classifier "tests",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % Test classifier "tests"
)
*/

// set the main class for 'sbt run'
mainClass in (Compile,run) := Some("gavert.ftest.CAPI.CServerMain")


lazy val root = (project in file("."))
  .settings(packageInfo: _*)
  .settings(scalaVersions: _*)
  .settings(resolvers ++= commonResolvers)
  .settings(libraryDependencies ++= finatraDependencies)
//  .settings(libraryDependencies ++= commonBuildLibs)
  .settings(libraryDependencies ++= commonTestLibs)
//  .settings(libraryDependencies ++= finatraTestLibs)
  .settings(scalacOptions ++= Seq("-feature", "-deprecation"))
