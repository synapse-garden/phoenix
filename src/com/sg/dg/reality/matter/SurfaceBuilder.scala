package com.sg.dg.reality.matter

import com.sg.dg.math.Vec4
import com.sg.dg.reality.EntityBuilder
import com.sg.dg.graphics.Displayer
import com.sg.dg.graphics.glbuffers.VertexBufferBuilder

/**
 * Created by bodie on 7/21/14.
 */
object SurfaceBuilder {
  def buildSurface(parentId: Int = -1,
                   entityId: Int = EntityBuilder.getId(),
                   materialId: Int = 0,
                   draw: Boolean = true,
                   vertices: Array[Float] = Array[Float]( )): Surface = {

    val vb = VertexBufferBuilder.buildVertexBuffer(
      vertices = vertices,
      parentId = parentId,
      entityId = entityId)

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

  def newSquare( pId: Int, pos: Vec4 = new Vec4, dim: Float = 4.0f ): Surface = {
    val offset = dim / 2f
    val newId = EntityBuilder.getId()

    buildSurface(
      parentId = pId,
      entityId = newId,
      materialId = -1,
      draw = true,
      vertices = Array[Float](
         offset, 0f,  offset,
        -offset, 0f,  offset,
        -offset, 0f, -offset,
        -offset, 0f, -offset,
         offset, 0f, -offset,
         offset, 0f,  offset
      )
    )
  }
}
//      // Left bottom triangle
//      -1f,  1f, 0f,
//      -1f, -1f, 0f,
//      1f, -1f, 0f,
//      // Right top triangle
//      1f, -1f, 0f,
//      1f,  1f, 0f,
//      -1f,  1f, 0f
