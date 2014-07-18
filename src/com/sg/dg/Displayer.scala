/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg

import org.lwjgl.opengl.GL11._
import org.lwjgl.util.glu.GLU

import org.lwjgl.opengl.Display

object Displayer {
  var color: Float = 0f
  var mult: Float = -1f

  def draw() {
    glClearColor( World.color.clampRed, World.color.clampGreen, World.color.clampGreen, 1f )
    glClear( GL_COLOR_BUFFER_BIT )
    Display update
  }

  def update() {

  }
}
