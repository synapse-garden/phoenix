package com.sg.dg.reality

import com.sg.dg.reality.matter.SurfaceBuilder
import com.sg.dg.reality.matter.Surface

/**
 * Created by bodie on 7/21/14.
 */
object EntityBuilder {
  private var nextId: Int = 0

  def newFsQuad( id: Int = getId( ) ): Entity = {
    buildEntityWithSurface( id, SurfaceBuilder.newSquare( pId = id ) )
  }

  def buildEntity( id: Int = getId( ) ): Entity = {
    buildEntityWithSurface( id, SurfaceBuilder.buildSurface( parentId = id ) )
  }

  def buildEntityWithSurface( id: Int = getId( ), surface: Surface ): Entity = {
    new Entity( id, surface )
  }

  def getId( ): Int = {
    nextId += 1
    nextId
  }
}
