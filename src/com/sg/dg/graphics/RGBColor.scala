/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg.graphics

import com.sg.dg.math.Math

class RGBColor( red: Float, green: Float, blue: Float )  {
  def r: Float = red
  def g: Float = green
  def b: Float = blue

  def clampRed: Float = Math.clamp( r, 0f, 1f )
  def clampGreen: Float = Math.clamp( g, 0f, 1f )
  def clampBlue: Float = Math.clamp( b, 0f, 1f )
}
