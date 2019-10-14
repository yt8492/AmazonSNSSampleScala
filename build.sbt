val ScalatraVersion = "2.6.5"

organization := "com.yt8492"

name := "AmazonSNSSampleScala"

version := "0.1"

scalaVersion := "2.12.6"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-json" % ScalatraVersion,
  "org.json4s" %% "json4s-jackson" % "3.6.7",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.9.v20180320" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "io.atlassian.aws-scala" %% "aws-scala-core" % "8.0.3",
  "io.atlassian.aws-scala" %% "aws-scala-sns" % "8.0.3",
)

enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)
