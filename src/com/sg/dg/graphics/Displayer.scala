package com.sg.dg.graphics

import scala.collection.mutable
import com.sg.dg.reality.matter.Surface
import org.lwjgl.opengl.{GL33, GL32, Display, GL11}
import com.sg.dg.graphics.shaders.Shaders

/**
 * Created by bodie on 7/24/14.
 */
object Displayer {
   val surfaces = mutable.HashMap[Int, Surface]().withDefaultValue(null)

   var surfacesToDraw = mutable.HashMap[Int, Boolean]().withDefaultValue(false)

   def draw() {
     GL11 glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT )
     GL11 glDrawArrays( GL11.GL_TRIANGLE_STRIP, 0, 4 )
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

     // var vboId = GL15.glGenBuffers()
     // GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
     // GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesFloatBuffer, GL15.GL_STREAM_DRAW);

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
