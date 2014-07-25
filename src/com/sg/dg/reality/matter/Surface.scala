package com.sg.dg.reality.matter

/**
 * Created by bodie on 7/21/14.
 */
class Surface( val pId: Int, val eId: Int, var draw: Boolean, val vaoId: Int ) {
  def points = vaoId
  def parentId = pId
  def entityId = eId
  def toDraw = draw
  def toDraw_=(b: Boolean) = draw = b
}
