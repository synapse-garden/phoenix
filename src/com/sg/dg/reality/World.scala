/**
 * Created by bodie on 7/18/14.
 */

package com.sg.dg.reality

import scala.collection.mutable
import com.sg.dg.graphics.Displayer

object World {
  private val entities = mutable.HashMap[Int, Entity]( )

  var entitiesToUpdate = mutable.HashMap[Int, Boolean]( ).withDefaultValue( false )

  def addToUpdateList( entityId: Int ) {
    entitiesToUpdate += entityId -> true
  }

  def registerEntity( e: Entity ) {
    entities += e.id -> e
  }

  def init( ) {
    setupWorld( )
    addFsQuad( )
  }

  private def addFsQuad( ) {
    val newId = EntityBuilder.getId( )
    val fsq = EntityBuilder.newFsQuad( newId )
    Displayer.registerSurface( fsq.surface )
    Displayer.enqueueIdToDraw( fsq.surface.entityId )
    registerEntity( fsq )
    addToUpdateList( newId )
  }

  def update( ) {
    for( id <- entitiesToUpdate.keys if entitiesToUpdate(id) ) {
      val e = entities(id)
      e.update( )
      if( e.surface.toDraw && !Displayer.surfacesToDraw( id ) ) {
        Displayer.enqueueIdToDraw( e.surface.entityId )
      }
    }
  }

  def setupWorld( ) {

  }

  def registerEntity( ent: Entity, id: Int ): Boolean = {
    if ( !entities.contains( id ) ){
      entities += id -> ent
      true
    }
    else false
  }
}
