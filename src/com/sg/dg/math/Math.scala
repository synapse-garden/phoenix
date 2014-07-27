package com.sg.dg.math

/**
 * Created by bodie on 7/19/14.
 */
object Math {

/*
<aloiscochard> ! def f[T](ord: scala.math.Ordering[T])(x: T): T = x
<multibot_>  f: [T](ord: scala.math.Ordering[T])(x: T)T
<aloiscochard> ! def g[T](ord: scala.math.Ordering[T])(f0: T => T = f(ord) _): Int = ???
<multibot_>  g: [T](ord: scala.math.Ordering[T])(f0: T => T)Int
*/

  def clampI( v: Int, lower: Int, upper: Int, f: (Int, Int, Int) => Int = defaultClampI ): Int = {
    f( v, lower, upper )
  }

  def defaultClampI( v: Int, lower: Int, upper: Int ): Int = {
    if( v > upper ) upper
    else if( v < lower ) lower
    else v
  }

  def clampF( v: Float, lower: Float, upper: Float, f: (Float, Float, Float) => Float = defaultClampF ): Float = {
    f( v, lower, upper )
  }

  def defaultClampF( v: Float, lower: Float, upper: Float ): Float = {
    if( v > upper ) upper
    else if( v < lower ) lower
    else v
  }

  /*
  def clamp[T](Ordering[T])( v: T, lower: T, upper: T, f: (T, T, T) => T = defaultClamp ): T = {
    f( v, lower, upper )
  }

  def defaultClamp[T : Ordering]( v: T, lower: T, upper: T ): T = {
      if(Ordering[T].gt( v, upper )) upper
      else if(Ordering[T].lt( v, lower )) lower
      else v
  }
  */

  def test(x: Float): Float = 2f * x

  def remap( v: Float, inputLower: Float, inputUpper: Float, mappedLower: Float = 0f, mappedUpper: Float = 1f): Float = {
    val domain = inputUpper - inputLower
    (((v-inputLower)/domain)*mappedUpper)+mappedLower
  }

  def lerp( a: Float, b: Float, blend: Float = 0.5f ): Float = {
    b*blend + a*(1-blend)
  }
}
