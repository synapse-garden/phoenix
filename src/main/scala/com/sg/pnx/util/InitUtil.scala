/**
 * Created by bodie on 7/19/14.
 */

package com.sg.pnx.util

import com.sg.pnx.graphics.util.{DisplayUtil, GLUtil}
import GLUtil.setupGL
import com.sg.pnx.graphics.GLCamera
import com.sg.pnx.Inputter
import com.sg.pnx.Game
import java.nio.file.{Files, Paths, Path}
import java.io.File

object InitUtil {
  def init( ) {
    loadNativeLibs( )
    DisplayUtil.setupDisplay( fullscreen = !Game.DEBUG )
    Inputter.init( )
    DisplayUtil.setupShaders( )

    setupGL( DisplayUtil.width, DisplayUtil.height )
    GLCamera setupCamera( 0f, 1.2f, -8.0f )
  }

  private def loadNativeLibs( ) {
    if( !IOUtil.checkForNativeLibs( ) )
      IOUtil.extractJar( src = new File( SysUtil.jarPath ), dst = new File( SysUtil.nativeLibPath ) )

    // SysUtil.addNativePath( SysUtil.nativeLibPath )
    // System.setProperty( "java.library.path", SysUtil.nativeLibPath )
    // for( lib <- SysUtil.nativeLibs.keys ) System.loadLibrary( SysUtil.nativeLibs( lib ) )

    for( lib <- SysUtil.nativeLibs.keys )
      System.load(
        SysUtil.rootAbsolutePath +
        SysUtil.separator +
        SysUtil.nativeLibPath +
        SysUtil.nativeLibs( lib ) +
        "." + SysUtil.nativeLibExtension )
  }
}

