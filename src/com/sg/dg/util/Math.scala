package com.sg.dg.util

/**
 * Created by bodie on 7/19/14.
 */
object Math {
  def clamp( v: Float, lower: Float = 0f, upper: Float = 1f, f: (Float, Float, Float) => Float = defaultClamp): Float = {
    f(v, lower, upper)
  }

  def defaultClamp( v: Float, upper: Float, lower: Float ): Float = {
    v match {
      case v if v > upper => upper
      case v if v < lower => lower
      case _ => lower
    }
  }

  def test(x: Float): Float = 2f * x

  def remap( v: Float, inputLower: Float, inputUpper: Float, mappedLower: Float = 0f, mappedUpper: Float = 1f): Float = {
    val domain = inputUpper - inputLower
    (((v-inputLower)/domain)*mappedUpper)+mappedLower
  }

  def lerp( a: Float, b: Float, blend: Float ): Float = {
    b*blend + a*(1-blend)
  }
}
