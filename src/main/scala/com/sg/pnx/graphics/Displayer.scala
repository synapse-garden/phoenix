package com.sg.pnx.graphics

import scala.collection.mutable
import com.sg.pnx.reality.matter.{SurfaceBuilder, Surface}
import org.lwjgl.opengl._
import com.sg.pnx.graphics.shaders.Shaders
import com.sg.pnx.graphics.util.GLUtil
import com.sg.pnx.graphics.glbuffers.{VertexBuffer, BufferHandler, Buffers}
import com.sg.pnx.reality.{Entity, EntityBuilder}
import org.lwjgl.util.vector.Vector4f

/**
 * Created by bodie on 7/24/14.
 */
object Displayer {
  private var surfaces = mutable.HashMap[Int, Surface]( ).withDefaultValue(null)
  private var surfacesToDraw = mutable.HashMap[Int, Boolean]( ).withDefaultValue(false)
  private var fsq: Entity = getFsQuad( )

  private def getFsQuad( ): Entity = {
    val newId = EntityBuilder.getId( )
    EntityBuilder.buildEntityWithSurface( id = newId, sfc = SurfaceBuilder.newSquare( pId = newId, dim = 2f, pos = new Vector4f(0f, 0f, 1f, 1f) ) )
  }

  private def drawFsq( ) {
    Shaders.setVertexSubroutines( ("vertexCamera", "fsQuad") )
    Shaders.setFragmentSubroutines( ("fragmentProcess", "fsQuad") )
    drawSurface( fsq.surface.vertexBuffer )
    Shaders.setVertexSubroutines( ("vertexCamera", "world") )
    Shaders.setFragmentSubroutines( ("fragmentProcess", "world") )
    GLUtil.exitOnGLError( )
  }

  def draw( ) {
    GL11 glClear( GL11 GL_COLOR_BUFFER_BIT )

    drawFsq
    drawSurfaces

    Display update( )
  }

  def enqueueIdToDraw( surfaceId: Int ) {
    surfacesToDraw += surfaceId -> true
  }

  // Registers the Surface's existence; enqueueIdToDraw needs the surface to be registered.
  def registerSurface( s: Surface ) = {
    surfaces += s.entityId -> s
  }

  def update( ) {
    Shaders.updateUniforms( )
  }

  def sync( fps: Int = 60 ) {
    Display.sync( fps )
  }

  def drawSurfaces( ) {
    for( id <- surfacesToDraw.keys if surfacesToDraw( id ) ) {
      drawSurface( surfaces( id ).vertexBuffer )
    }
  }

  private def drawSurface( vb: VertexBuffer ) {
    val ( vaoId, vaoIndex, vertexCount ) = ( vb.vaoId, vb.vaoIndex, vb.vertexCount )
    BufferHandler.bindVAO( vaoId )
    BufferHandler.enableVAO( vaoIndex )
    GL11.glDrawArrays( GL11.GL_TRIANGLES, vaoIndex, vertexCount )
    BufferHandler.disableVAO( vaoIndex )
    GLUtil.exitOnGLError( )
  }

  def dispose( ) {
    Shaders.endProgram( )
    Buffers.dispose( )
  }
}
