/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg

import com.sg.dg.util.GLUtil
import com.sg.dg.util.GLCamera
import org.lwjgl.opengl.GL11._
import org.lwjgl.util.glu.GLU

import org.lwjgl.opengl.Display
import com.sg.dg.reality.World

object Displayer {
  def draw() {
    glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT )
    glLoadIdentity
    GLCamera.lookThrough
    World.box.draw
    Display update
  }

  def update() {
    GLCamera.updateCamera()
  }
}
