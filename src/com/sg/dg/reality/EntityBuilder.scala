package com.sg.dg.reality

import com.sg.dg.reality.matter.SurfaceBuilder
import org.lwjgl.util.vector.Vector4f

import com.sg.dg.reality.matter.Surface

/**
 * Created by bodie on 7/21/14.
 */
object EntityBuilder {
  private var nextId: Int = 0

  def buildEntity( parentId: Int = -1, id: Int = getId( ), pos: Vector4f = new Vector4f ): Entity = {
    new Entity( parentId, id, pos )
  }

  def buildEntityWithSurface( parentId: Int = -1, id: Int = getId( ), pos: Vector4f = new Vector4f, sfc: Surface ): Entity = {
    new Entity( parentId, id, pos, sfc, drawable = true )
  }

  def getId( ): Int = {
    nextId += 1
    nextId
  }
}
