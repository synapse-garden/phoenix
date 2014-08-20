package com.sg.pnx.util

import scala.collection.immutable
import java.io.File
import java.lang.reflect.Field

/**
 * Created by bodie on 8/20/14.
 */
object SysUtil {
  def os = {
    val name = System.getProperty( "os.name" ).toLowerCase
    if( name.indexOf("mac") >= 0 ) "osx"
    else if( name.indexOf("win") >= 0 ) "windows"
    else "linux"
  }

  def bitness = {
    val resp = System.getProperty( "os.arch" )
    if( resp.indexOf("64") >= 0 ) "64"
    else "32"
  }

  def separator = {
    os match {
      case "linux" => "/"
      case _ => "\\"
    }
  }

  def nativeLibs = {
    immutable.HashMap[String, String](
      ("opengl", os + bitness match {
        case "linux32" => "liblwjgl"
        case "linux64" => "liblwjgl64"
        case "windows32" => "lwjgl"
        case "windows64" => "lwjgl64"
      }),
      ("openal", os + bitness match {
          case "linux32" => "libopenal"
          case "linux64" => "libopenal64"
          case "windows32" => "OpenAL32"
          case "windows64" => "OpenAL64"
      })
    )
  }

  def nativeLibExtension = {
    os match {
      case "linux" => "so"
      case _ => "dll"
    }
  }

  def nativeLibPath = {
    "res" + separator + "native" + separator + os + separator
  }

  def jarPaths = {
    Seq[String]( "lib" + separator + "lwjgl-platform-2.9.1-natives-" + os + ".jar" )
  }

  def rootAbsolutePath = {
    new File(".").getAbsolutePath
  }

  def addNativePath( pathToAdd: String ) = {
    val usrPathsField: Field =  classOf[ClassLoader].getDeclaredField( "usr_paths" )
    usrPathsField.setAccessible( true )

    val paths = usrPathsField.get( null ).asInstanceOf[Array[String]]

    if( !paths.contains( pathToAdd ) ) {
      usrPathsField.set( null, paths :+ pathToAdd )
    }
  }
}
