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
    else {
      if( os == "windows" ) {
        var arch = System.getenv( "PROCESSOR_ARCHITEW6432" )
        if( arch != null && !arch.isEmpty ) if( arch.indexOf( "64" ) >= 0 ) "64" else "32"
        else arch = System.getenv( "PROCESSOR_ARCHITECTURE" )
        if( arch != null && !arch.isEmpty ) if( arch.indexOf( "64" ) >= 0 ) "64" else "32"
      }
      else "32"
    }
  }

  def separator = {
    os match {
      case "linux" | "mac" => "/"
      case _ => "\\"
    }
  }

  def nativeLibs = {
    immutable.HashMap[String, List[String]](
      ("opengl", os match {
        case "linux" => List[String]( "liblwjgl.so", "liblwjgl64.so" )
        case "windows" => List[String]( "lwjgl.dll", "lwjgl64.dll" )
      }),
      ("openal", os match {
          case "linux" => List[String]( "libopenal.so", "libopenal64.so" )
          case "windows" => List[String]( "OpenAL32.dll", "OpenAL64.dll" )
      })
    )
  }

  def nativeLibExtension = {
    os match {
      case "linux" => ".so"
      case "windows" => ".dll"
      case _ => "???"
    }
  }

  def nativeLibPath = {
    "res" + separator + "native" + separator + os + separator
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

  def loadNativeLibs( ) {
    if( !checkForNativeLibs( ) )
      for( (_, libs) <- nativeLibs )
        for( lib <- libs ) IOUtil.extractJarLibToPath( src = lib, dst = new File( nativeLibPath ) )

    SysUtil.addNativePath( nativeLibPath )
  }

  private def checkForNativeLibs( ): Boolean = {
    nativeLibs.keys.forall( libName => {
      nativeLibs.get( libName ).forall( lib => new File( nativeLibPath + lib ).isFile )
    } )
  }
}
