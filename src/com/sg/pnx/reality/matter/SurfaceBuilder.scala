package com.sg.pnx.reality.matter

import com.sg.pnx.reality.EntityBuilder
import com.sg.pnx.graphics.Displayer
import com.sg.pnx.graphics.glbuffers.VertexBufferBuilder
import org.lwjgl.util.vector.Vector4f
import com.sg.pnx.graphics.util.GLUtil

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
    // Register the surface's existence in Displayer; enqueueIdToDraw later.
    Displayer.registerSurface( newSfc )
    newSfc
  }

  def newSquare( pId: Int, pos: Vector4f = new Vector4f, dim: Float = 4.0f ): Surface = {
    val offset = dim / 2f
    val newId = EntityBuilder.getId( )
    val (x, y, z) = (pos.x, pos.y, pos.z)

    buildSurface(
      parentId = pId,
      entityId = newId,
      materialId = -1,
      draw = true,
      vertices = Array[Float](
        -offset + x,  offset + y, z,
        -offset + x, -offset + y, z,
         offset + x, -offset + y, z,
         offset + x, -offset + y, z,
         offset + x,  offset + y, z,
        -offset + x,  offset + y, z
      )
    )
  }
}
