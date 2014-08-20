package com.sg.pnx.graphics.glbuffers

import com.sg.pnx.graphics.util.GLUtil
import org.lwjgl.opengl.{GL30, GL15, GL20}
import scala.collection.mutable

/**
 * Created by bodie on 7/24/14.
 */
object Buffers {
  private var vertexBuffers = mutable.HashMap[Int, VertexBuffer]( )

  def addVerts( vertices: Array[Float] ): ( Int, Int, Int ) = {
    BufferHandler.bufferVerts( vertices = vertices )
  }

  def registerVertexBuffer( eId: Int, vb: VertexBuffer ): VertexBuffer = {
    vertexBuffers += eId -> vb
    vb
  }

  def disposeVertexBuffer( eId: Int, vboId: Int, vaoId: Int, vaoIndex: Int ) {
    val vb = vertexBuffers( eId )
    BufferHandler.disposeBuffer( vboId, vaoId, vaoIndex )
    vertexBuffers -= eId
  }

  def dispose( ) {
    for( (id, vb) <- vertexBuffers ) disposeVertexBuffer( id, vb.vboId, vb.vaoId, vb.vaoIndex )
  }
}
