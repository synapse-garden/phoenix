package com.sg.dg.math

import Ordering.Implicits._

/**
 * Created by bodie on 7/19/14.
 */
object Math {
  def clamp[T : Ordering]( v: T, lower: T, upper: T ): T = {
    clampWith(v, lower, upper)( defaultClamp )
  }

  def clampWith[T]( v: T, lower: T, upper: T )( f0: (T, T, T) => T ): T = {
    f0( v, lower, upper )
  }

  def defaultClamp[T : Ordering]( v: T, lower: T, upper: T ): T = {
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
