name := "phoenix"

version := "0.1"

scalaVersion := "2.11.4"

autoCompilerPlugins := true

addCompilerPlugin( "com.nativelibs4java" %% "scalaxy-streams" % "0.3.2" )

scalacOptions ++= Seq(
  "-Xplugin-require:scalaxy-streams",
  "-optimise",
  "-Yclosure-elim",
  "-Yinline"
)

libraryDependencies ++= Seq(
  "org.lwjgl.lwjgl" % "lwjgl" % "2.9.1",
  "org.lwjgl.lwjgl" % "lwjgl_util" % "2.9.1"
)
