package com.sg.dg.graphics.glbuffers

import com.sg.dg.graphics.util.GLUtil
import org.lwjgl.opengl.{GL30, GL15, GL20}

/**
 * Created by bodie on 7/24/14.
 */
object Buffers {
  var fsQuadVBOId: Int = 0
  var fsQuadVAOId: Int = 0
  var fsQuadVAOIndex: Int = 0
  var fsQuadVertexCount: Int = 6

  var vaoId: Int = 0
  var vboId: Int = 0

  def buildFsQuadVBO( ) {
    val bufferIds = BufferHandler.initFsQuad( )
    fsQuadVAOId = bufferIds._1
    fsQuadVBOId = bufferIds._2

    GLUtil.exitOnGLError( "Error in setupQuad" )
  }

  def dispose( ) {
    disposeFsQuad( )
  }

  def disposeFsQuad( ) {
    BufferHandler.disableVBOAt( fsQuadVAOIndex )
    BufferHandler.deleteVBO( fsQuadVBOId )
    BufferHandler.deleteVAO( fsQuadVAOId )
  }
}
