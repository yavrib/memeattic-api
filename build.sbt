name := "api"
organization := "com.memeattic"
version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.4"

lazy val asyncHttpClientVersion = "2.4.2"
lazy val akkaHttpVersion = "10.1.0-RC1"
lazy val akkaStreamVersion = "2.5.8"

libraryDependencies ++= Seq(
 "org.asynchttpclient" % "async-http-client" % asyncHttpClientVersion,
 "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
 "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion
)
