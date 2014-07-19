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
      Display create
    } catch {
      case e: Exception => err.println("Error setting up Display")
        sys exit 0
    }
  }

  def destroyDisplay() {
    Display destroy()
  }
}
