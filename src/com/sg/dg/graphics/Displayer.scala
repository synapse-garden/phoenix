package com.sg.dg.graphics

import scala.collection.mutable
import com.sg.dg.reality.matter.Surface
import org.lwjgl.opengl._
import com.sg.dg.graphics.shaders.Shaders
import com.sg.dg.graphics.util.GLUtil
import com.sg.dg.graphics.glbuffers.{BufferHandler, Buffers}

/**
 * Created by bodie on 7/24/14.
 */
object Displayer {
  val surfaces = mutable.HashMap[Int, Surface]( ).withDefaultValue(null)

  var surfacesToDraw = mutable.HashMap[Int, Boolean]( ).withDefaultValue(false)

  def draw( ) {
    GL11 glClear( GL11 GL_COLOR_BUFFER_BIT )

    if( Shaders.useShaders )
      Shaders.useProgram( )

    drawFsQuad
    drawSurfaces
    if( Shaders.useShaders )
      Shaders.endShaders( )

    Display update( )
  }

  def enqueueIdToDraw(id: Int) {
    surfacesToDraw += id -> true
  }

  def registerSurface(s: Surface) {
    surfaces += s.entityId -> s
  }

  def update( ) {
    GLCamera.updateCamera( )
    Shaders.updateUniforms( )
  }

  def sync( fps: Int = 60 ) {
    Display.sync( fps )
  }

  def drawFsQuad( ) {
    BufferHandler.bindVAO( Buffers.fsQuadVAOId )

    GL20.glEnableVertexAttribArray( Buffers.fsQuadVAOIndex )

    GL11.glDrawArrays( GL11.GL_TRIANGLES, Buffers.fsQuadVAOIndex, Buffers.fsQuadVertexCount )

    GL20.glDisableVertexAttribArray( Buffers.fsQuadVAOIndex )

    BufferHandler.unbindVAO( )

    GLUtil.exitOnGLError("Error in drawFsQuad")
  }

  def drawSurfaces( ) {
    for( id <- surfacesToDraw.keys if surfacesToDraw( id ) ) {
      val s = surfaces( id )
    }
    GLUtil.exitOnGLError("Error in drawSurfaces")
  }

  def dispose( ) {
    Buffers.dispose( )
  }
}
