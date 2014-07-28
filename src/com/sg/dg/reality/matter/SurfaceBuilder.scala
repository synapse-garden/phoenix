package com.sg.dg.reality.matter

import com.sg.dg.reality.EntityBuilder
import com.sg.dg.graphics.Displayer
import com.sg.dg.graphics.glbuffers.VertexBufferBuilder

/**
 * Created by bodie on 7/21/14.
 */
object SurfaceBuilder {
  def buildSurface( parentId: Int = -1,
                    entityId: Int = EntityBuilder.getId( ),
                    vertices: Array[Float] = Array[Float]( ),
                    materialId: Int = 0,
                    draw: Boolean = true ): Surface = {

    val vb = VertexBufferBuilder.buildVertexBuffer(
      vertices = vertices,
      parentId = parentId,
      entityId = entityId )

    val newSfc = new Surface(
      pId = parentId,
      eId = entityId,
      vertexBuffer = vb,
      draw = true,
      vertices = vertices
    )

    Displayer.registerSurface( newSfc )
    newSfc
  }

  def newSquare( pId: Int ): Surface = {
    val vertices = Array[Float] (
      // Left bottom triangle
      -1f,  1f, 0f,
      -1f, -1f, 0f,
      1f, -1f, 0f,
      // Right top triangle
      1f, -1f, 0f,
      1f,  1f, 0f,
      -1f,  1f, 0f
    )

    buildSurface( parentId = pId, vertices = vertices )
  }
}
