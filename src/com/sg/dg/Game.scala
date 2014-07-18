/**
 * Created by bodie on 7/17/14.
 */

package com.sg.dg

// What GL version you plan on using
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.{
Display,
DisplayMode
}

object Game extends App {
  val displayMode = new DisplayMode(640, 480)
  Display.setTitle("LWJGL Test")
  Display.setDisplayMode(displayMode)
  Display.create()
  fader(0f)
  Display.destroy()
  sys.exit(0)

  def fader(color: Float, d: Int = -1) {
    val mult = if(color > 1f) 1 else if(color < 0f) -1 else d
    val newColor = color - (mult * 0.05f)

    glClearColor(newColor, newColor, newColor, 1f)
    glClear(GL_COLOR_BUFFER_BIT)

    Display.update()
    Thread.sleep(60)
    if(!Display.isCloseRequested) fader(newColor, mult)
  }
}
