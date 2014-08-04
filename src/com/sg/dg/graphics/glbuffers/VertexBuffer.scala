package com.sg.dg.graphics.glbuffers

import com.sg.dg.reality.EntityBuilder

/**
 * Created by bodie on 7/28/14.
 */
class VertexBuffer( val vboId: Int,
            val vaoId: Int,
            val vaoIndex: Int,
            val vertexCount: Int = 0,
            val eId: Int = EntityBuilder.getId( ) ) {
  def destroy( ) {
    Buffers.disposeVertexBuffer( eId, vboId, vaoId, vaoIndex )
  }
}
