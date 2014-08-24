/**
 * Created by bodie on 7/19/14.
 */

package com.sg.pnx.graphics.util

import org.lwjgl.opengl.{PixelFormat, ContextAttribs, Display, DisplayMode}
import java.lang.System.err
import com.sg.pnx.graphics.shaders.Shaders

object DisplayUtil {
  private var _isCreated: Boolean = false
  private var (w, h) = (0, 0)
  private var (wDiv2, hDiv2) = (0, 0)

  private def setDim( newW: Int, newH: Int ) {
    w = newW
    h = newH
    wDiv2 = ( newW / 2f ).toInt
    hDiv2 = ( newH / 2f ).toInt
  }

  def isCreated = _isCreated

  def width: Int = w
  def height: Int = h
  def halfHeight: Int = hDiv2
  def halfWidth: Int = wDiv2

  def die: Boolean = Display isCloseRequested

  def setupDisplay(fullscreen: Boolean = true) {
    try {
      val dm: DisplayMode = Display.getAvailableDisplayModes.foldLeft( new DisplayMode(1280, 800) )(
        (m1, m2) =>
          if( m2.isFullscreenCapable == fullscreen &&
              m2.getHeight >= m1.getHeight &&
              m2.getWidth  >= m1.getWidth ) m2
          else m1
      )

      val contextAttributes = new ContextAttribs( 3, 2 )
        .withForwardCompatible( true )
        .withProfileCore( true )
      val pixelFormat = new PixelFormat( )
        .withDepthBits( 24 )
        .withSamples( 4 )
        .withSRGB( false ) // true crashes if fullscreen on windows

      Display setTitle "LWJGL Test"
      Display setVSyncEnabled true
      Display setDisplayModeAndFullscreen dm
      Display.create( pixelFormat, contextAttributes )

      setDim( dm.getWidth, dm.getHeight )
    } catch {
      case e: Exception =>
        err.println( "Error setting up Display: " + e.getMessage )
        sys exit 0
    }
    _isCreated = true
    GLUtil.exitOnGLError( )
  }

  def setupShaders( ) {
    Shaders.loadAndUseShaders( )
    if( Shaders.useShaders ) {
      Shaders.useProgram( )
      Shaders.getUniforms( )
      Shaders.getAttribs( )
      Shaders.getSubroutines( )
    }
  }

  def destroyDisplay( ) {
    Display destroy( )
  }
}
