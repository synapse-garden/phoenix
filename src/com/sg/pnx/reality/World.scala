/**
 * Created by bodie on 7/18/14.
 */

package com.sg.pnx.reality

import scala.collection.mutable.HashMap
import com.sg.pnx.graphics.Displayer
import org.lwjgl.util.vector.Vector4f
import com.sg.pnx.reality.matter.SurfaceBuilder

object World {
  private val entities = HashMap[Int, Entity]( )
  private var entitiesToUpdate = HashMap[Int, Boolean]( ).withDefaultValue( false )
  private def addToUpdateList( entityId: Int ) {
    entitiesToUpdate += entityId -> true
  }

  private def registerEntity( ent: Entity, id: Int ): Boolean = {
    if( entities.contains( id ) ) false
    entities += id -> ent
    true
  }

  def init( ) {
    setupWorld( )
  }

  def setupWorld( ) {
    setupEntities
  }

  def setupEntities( ) {
    val squaresRowId = EntityBuilder.getId( )
    val squaresRow = EntityBuilder.buildEntity( id = squaresRowId, pos = new Vector4f )

    for( i <- -1 to 1 ) {
      val squareId = EntityBuilder.getId( )
      val pos = new Vector4f( i * 0.5f, 0f, 0.5f, 0f )
      val newSquare = EntityBuilder.buildEntityWithSurface(
        parentId = squaresRowId,
        id = squareId,
        pos = pos,
        sfc = SurfaceBuilder.newSquare( pId = squareId, pos = pos, dim = 0.3f )
      )
      registerEntity( newSquare, squareId )
      squaresRow.attachChild( squareId )
      addToUpdateList( squareId )
    }
    registerEntity( squaresRow, squaresRowId )
    addToUpdateList( squaresRowId )
  }

  def update( ) {
    for( id <- entitiesToUpdate.keys if entitiesToUpdate( id ) ) {
      val entity = entities( id )
      entity.update( )
      if( entity.drawable && entity.surface.toDraw ) {
        Displayer.enqueueIdToDraw( entity.surface.entityId )
      }
    }
  }
}
