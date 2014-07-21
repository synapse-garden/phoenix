/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg

import scala.collection.mutable

import org.lwjgl.opengl.GL11
import org.lwjgl.util.glu.GLU
import org.lwjgl.opengl.{ARBShaderObjects, GL11, Display}

import com.sg.dg.util.{ShaderUtil, GLUtil, GLCamera}
import com.sg.dg.reality.World
import com.sg.dg.reality.matter._
import scala.collection.mutable.ArrayBuffer
import com.sg.dg.gfx.Shaders

object Displayer {
  val surfaces = mutable.HashMap[Int, Surface]().withDefaultValue(null)

  var surfacesToDraw = mutable.HashMap[Int, Boolean]().withDefaultValue(false)

  def draw() {
    GL11 glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT )
    GL11 glLoadIdentity()

    GLCamera.lookThrough()

    drawSurfaces()

    Display update()
  }

  def enqueueIdToDraw(id: Int) {
    surfacesToDraw += id -> true
  }

  def registerSurface(s: Surface) {
    surfaces += s.entityId -> s
  }

  def update() {
    GLCamera.updateCamera()
  }

  def drawSurfaces() {
    if( Shaders.useShaders )
      Shaders useProgram()

    GL11 glColor3f(0f, 0.707f, 0.707f)

    GL11 glBegin(GL11 GL_TRIANGLES)

    for( id <- surfacesToDraw.keys if surfacesToDraw(id)) {
      val s = surfaces(id)
      for (pointCount <- 0 to s.points.length / 3) {
        GL11 glVertex3f(s.points(pointCount), s.points(pointCount + 1), s.points(pointCount + 2))
      }
    }

    GL11 glEnd()

    if( Shaders.useShaders )
      Shaders endShaders()
  }
}

