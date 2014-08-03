package com.sg.dg.reality.matter

import com.sg.dg.graphics.glbuffers.VertexBuffer

/**
 * Created by bodie on 7/21/14.
 */
class Surface( val pId: Int,
               val eId: Int,
               val vertexBuffer: VertexBuffer,
               var draw: Boolean,
               var vertices: Array[Float] = null ) {
  def points = vertices
  // def vertexBuffer = vertBuffer
  def parentId = pId
  def entityId = eId
  def toDraw = draw
  def toDraw_=( b: Boolean ) = draw = b
}
