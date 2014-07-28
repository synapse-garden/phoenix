package com.sg.dg.reality

/**
 * Created by bodie on 7/21/14.
 */
class Entity( entityId: Int,
              sfc: matter.Surface,
              var position: Array[Float] = Array[Float](0f, 0f, 0f) ) {
  def id = entityId
  def surface = sfc

  def x = position(0)
  def x_=(newX: Float) = position(0) = newX
  def y = position(1)
  def y_=(newY: Float) = position(1) = newY
  def z = position(2)
  def z_=(newZ: Float) = position(2) = newZ

  def update( ) {

  }
}
