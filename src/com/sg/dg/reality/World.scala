/**
 * Created by bodie on 7/18/14.
 */

package com.sg.dg.reality

import scala.collection.mutable
import com.sg.dg.graphics.Displayer

object World {
  val entities = mutable.HashMap[Int, Entity]().withDefaultValue( null )

  var entitiesToUpdate = mutable.HashMap[Int, Boolean]().withDefaultValue(false)

  def addToUpdateList( entityId: Int ) {
    entitiesToUpdate += entityId -> true
  }

  def init() {
    val newId = EntityBuilder.getId( )
    entities += ( newId -> EntityBuilder.newDefaultEntity( id = newId ) )
    addToUpdateList( newId )
  }

  def update() {
    for( id <- entitiesToUpdate.keys if entitiesToUpdate(id) ) {
      val e = entities(id)
      e.update( )
      if( e.surface.toDraw ) {
      }
        Displayer.enqueueIdToDraw(e.surface.entityId)
    }
  }

  def setupWorld() {

  }
}
