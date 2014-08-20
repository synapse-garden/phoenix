name := "phoenix"

version := "1.0"

scalaVersion := "2.10.2"

classpathTypes += "maven-plugin"

libraryDependencies ++= Seq(
  "org.lwjgl.lwjgl" % "lwjgl" % "2.9.1",
  "org.lwjgl.lwjgl" % "lwjgl_util" % "2.9.1"
)
