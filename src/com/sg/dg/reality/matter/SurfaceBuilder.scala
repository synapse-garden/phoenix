package com.sg.dg.reality.matter

import com.sg.dg.reality.EntityBuilder
import com.sg.dg.graphics.Displayer

/**
 * Created by bodie on 7/21/14.
 */
object SurfaceBuilder {

  def newSquare( pId: Int, dim: Float = 4.0f ): Surface = {
    val offset = dim/2f
    val newId = EntityBuilder.getId

    val newSfc = new Surface( pId = pId, eId = newId, draw = true,
      Array[Float] (
         offset, 0f,  offset,
        -offset, 0f,  offset,
        -offset, 0f, -offset,
        -offset, 0f, -offset,
         offset, 0f, -offset,
         offset, 0f,  offset
      )
    )

    Displayer.registerSurface( newSfc )

    newSfc
  }
}
