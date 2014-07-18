/**
 * Created by bodie on 7/18/14.
 */

package com.sg.dg

object World {
  private var c: gfx.RGBColor = new gfx.RGBColor( 0f, 0f, 0f )

  def color: gfx.RGBColor = c
  def setColor( newColor: gfx.RGBColor ) = {
    c = newColor
  }

  def update() {

  }
}
