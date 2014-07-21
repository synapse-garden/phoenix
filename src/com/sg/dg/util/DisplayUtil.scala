/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg.util

import org.lwjgl.opengl.{
Display,
DisplayMode
}
import java.lang.System.err

object DisplayUtil {
  private var (w, h) = (0, 0)
  private var (wDiv2, hDiv2) = (0, 0)

  private def setDim(newW: Int, newH: Int) {
    w = newW
    h = newH
    wDiv2 = (newW / 2f).toInt
    hDiv2 = (newH / 2f).toInt
  }

  def width: Int = w
  def height: Int = h
  def halfHeight: Int = hDiv2
  def halfWidth: Int = wDiv2

  def die: Boolean = Display isCloseRequested

  def setupDisplay() {
    try {
      val dm: DisplayMode = Display.getAvailableDisplayModes.foldLeft(new DisplayMode(640, 480))(
        (m1, m2) =>
          if( m2.isFullscreenCapable &&
              m2.getHeight >= m1.getHeight &&
              m2.getWidth  >= m1.getWidth ) m2
          else m1
      )

      Display setTitle "LWJGL Test"
      Display setVSyncEnabled true
      Display setDisplayModeAndFullscreen dm
      Display create( )

      setDim( dm.getWidth, dm.getHeight )
    } catch {
      case e: Exception => err.println("Error setting up Display")
        sys exit 0
    }
  }

  def destroyDisplay() {
    Display destroy()
  }
}

