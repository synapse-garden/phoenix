/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg

import org.lwjgl.opengl.GL11._
import org.lwjgl.util.glu.GLU

import org.lwjgl.opengl.Display

object Displayer {
  def draw() {
    glClearColor(math.tan(color).toFloat, math.sin(color).toFloat, 0.5f, 1f)
    glClear(GL_COLOR_BUFFER_BIT)
    Display update
  }

  def update(): Boolean = {
    mult = if(color > 1f) 1 else if(color < 0f) -1 else mult
    color = color - (mult * 0.02f)
    Display isCloseRequested
  }
}
