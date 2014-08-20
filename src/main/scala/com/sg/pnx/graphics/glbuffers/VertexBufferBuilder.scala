package com.sg.pnx.graphics.glbuffers

import com.sg.pnx.reality.EntityBuilder
import com.sg.pnx.graphics.util.GLUtil

/**
 * Created by bodie on 7/28/14.
 */
object VertexBufferBuilder {
  def buildVertexBuffer( vertices: Array[Float] = Array[Float]( ),
                         parentId: Int = -1,
                         entityId: Int = EntityBuilder.getId( ) ): VertexBuffer = {

    val( vaoId, vaoIndex, vboId ) = Buffers.addVerts( vertices )
    val newVB = new VertexBuffer(
            vboId = vboId,
            vaoId = vaoId,
            vaoIndex = vaoIndex,
            vertexCount = vertices.length,
            eId = entityId )
    Buffers.registerVertexBuffer( newVB.eId, newVB )
  }
}
