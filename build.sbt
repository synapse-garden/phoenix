name := "phoenix"

version := "0.1"

scalaVersion := "2.11.2"

autoCompilerPlugins := true

addCompilerPlugin( "com.nativelibs4java" %% "scalaxy-streams" % "0.2.1" )

scalacOptions ++= Seq(
  "-Xplugin-require:scalaxy-streams",
  "-optimise"
)

libraryDependencies ++= Seq(
  "org.lwjgl.lwjgl" % "lwjgl" % "2.9.1",
  "org.lwjgl.lwjgl" % "lwjgl_util" % "2.9.1"
)
