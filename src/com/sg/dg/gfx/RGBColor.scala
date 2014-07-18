/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg.gfx

import com.sg.dg.util

class RGBColor( red: Float, green: Float, blue: Float )  {
  def r: Float = red
  def g: Float = green
  def b: Float = blue

  def clampRed: Float = util.Math.clamp( r )
  def clampGreen: Float = util.Math.clamp( g )
  def clampBlue: Float = util.Math.clamp( b )
}
