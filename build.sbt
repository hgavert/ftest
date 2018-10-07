lazy val packageInfo = Seq(
  organization := "gavert",
  name := "ctest",
  version := "0.1.0"
)

lazy val scalaVersions = Seq(
  scalaVersion := "2.12.6"
)


lazy val versions = new Object {
  val finatra = "18.9.1"
  val twitterUtils = "18.9.1"
  val guice = "4.0"
  val scalaCheck = "1.14.0"
  val scalaTest = "3.0.5"
}

lazy val commonResolvers = Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)

lazy val finatraDependencies = Seq(
  "com.twitter" %% "finatra-http" % versions.finatra,
  "com.twitter" %% "finatra-http" % versions.finatra % "test",
  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-server" % versions.finatra % "test",
  "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-core" % versions.finatra % "test",
  "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-app" % versions.finatra % "test",
  "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-modules" % versions.finatra % "test",
  "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)


/*
"com.twitter" %% "finatra-jackson" % versions.finatra % "test",
"com.twitter" %% "finatra-jackson" % versions.finatra % "test" classifier "tests"
*/

lazy val commonBuildLibs = Seq(
  "com.twitter"    %% "util-collection" % versions.twitterUtils
)
lazy val commonTestLibs = Seq(
  "org.scalacheck" %% "scalacheck"      % versions.scalaCheck,
  "org.scalatest"  %% "scalatest"       % versions.scalaTest
).map(_ % Test)


// set the main class for 'sbt run'
mainClass in (Compile,run) := Some("gavert.ftest.CAPI.CServerMain")


lazy val root = (project in file("."))
  .settings(packageInfo: _*)
  .settings(scalaVersions: _*)
  .settings(resolvers ++= commonResolvers)
  .settings(libraryDependencies ++= finatraDependencies)
  .settings(libraryDependencies ++= commonBuildLibs)
  .settings(libraryDependencies ++= commonTestLibs)
  .settings(scalacOptions ++= Seq("-target:jvm-1.8", "-feature", "-deprecation"))
