package com.sg.dg.reality

import com.sg.dg.reality.matter.SurfaceBuilder

/**
 * Created by bodie on 7/21/14.
 */
object EntityBuilder {
  private var nextId: Int = 0

  def newDefaultEntity( id: Int = getId() ): Entity = {
    new Entity( id, SurfaceBuilder.newSquare( pId = id ) )
  }

  def getId(): Int = {
    nextId += 1
    nextId
  }
}
