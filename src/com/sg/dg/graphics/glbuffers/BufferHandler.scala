package com.sg.dg.graphics.glbuffers

import org.lwjgl.opengl.{GL31, GL15}
import scala.collection.mutable

/**
 * Created by bodie on 7/24/14.
 */
object BufferHandler {
  private var vboId: Int = 0
  private var pboId: Int = 0
  private var uboId: Int = 0

  def init() {
    // vboId = GL15.glGenBuffers()
    // pboId = GL15.glGenBuffers()
    uboId = makeUBO()
  }

  private def makeUBO(): Int = {
    val newId = GL15.glGenBuffers()
    GL15.glBindBuffer( GL31.GL_UNIFORM_BUFFER, newId )
    newId
  }
  // var vboId = GL15.glGenBuffers()
  // GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
  // GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesFloatBuffer, GL15.GL_STREAM_DRAW);
}
