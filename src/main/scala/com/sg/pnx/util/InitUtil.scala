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
      for( jar <- SysUtil.jarPaths ) IOUtil.extractJar( src = new File( jar ), dst = new File( SysUtil.nativeLibPath ) )

    SysUtil.addNativePath( SysUtil.nativeLibPath )
    println( System.getProperty( "java.library.path" ) )
  }
}

