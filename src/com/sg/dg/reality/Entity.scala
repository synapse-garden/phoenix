package com.sg.dg.reality

import org.lwjgl.util.vector.Vector4f

/**
 * Created by bodie on 7/21/14.
 */
class Entity( parentId: Int, entityId: Int, pos: Vector4f = new Vector4f, var surface: matter.Surface = null, val drawable: Boolean = false ) {
  private var _children = Array[Int]( )
  def children = _children // poor things
  def attachChild( id: Int ) {
    _children :+= id
  }

  def update( ) {

  }
}
