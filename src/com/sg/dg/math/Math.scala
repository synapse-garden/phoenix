package com.sg.dg.math

/**
 * Created by bodie on 7/19/14.
 */
object Math {
  def clamp( v: Float, lower: Float = 0f, upper: Float = 1f, f: (Float, Float, Float) => Float = defaultClamp): Float = {
    f(v, lower, upper)
  }

  def defaultClamp( v: Float, lower: Float, upper: Float ): Float = {
      if( v > upper ) upper
      else if( v < lower ) lower
      else v
  }

  def test(x: Float): Float = 2f * x

  def remap( v: Float, inputLower: Float, inputUpper: Float, mappedLower: Float = 0f, mappedUpper: Float = 1f): Float = {
    val domain = inputUpper - inputLower
    (((v-inputLower)/domain)*mappedUpper)+mappedLower
  }

  def lerp( a: Float, b: Float, blend: Float = 0.5f ): Float = {
    b*blend + a*(1-blend)
  }
}
