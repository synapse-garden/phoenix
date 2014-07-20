/**
 * Created by bodie on 7/18/14.
 */

package com.sg.dg

import com.sg.dg.util.DisplayUtil

object World {
  private var c: gfx.RGBColor = new gfx.RGBColor( 0f, 0f, 0f )

  def color: gfx.RGBColor = c
  def setColor( newColor: gfx.RGBColor ) = {
    c = newColor
  }

  def update() {
    setColor( new gfx.RGBColor(
              (Inputter.mouseX % DisplayUtil.width).toFloat / DisplayUtil.width.toFloat,
              (Inputter.mouseY % DisplayUtil.height).toFloat / DisplayUtil.height.toFloat,
              Inputter.mouseY.toFloat / Inputter.mouseX.toFloat ) ) //math.abs(math.sin( (( Inputter.mouseX.toFloat / Inputter.mouseY.toFloat )*2f*math.Pi ) )).toFloat ) )
  }
}
