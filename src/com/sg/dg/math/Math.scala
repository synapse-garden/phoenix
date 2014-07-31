package com.sg.dg.math

import Ordering.Implicits._

/**
 * Created by bodie on 7/19/14.
 */
object Math {

  val PI = 3.141592f
  val TWO_PI = PI*2.0f
  val HALF_PI = PI*0.5f
  val QUARTER_PI = PI*0.25f

  val sinValues = List( 0.0f, 0.258819f, 0.5f, 0.707107f, 0.866025f, 0.965926f, 1.0f ) // Every 15 degrees or pi/12 radians

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

  def fastSin( a: Float ): Float = {
    val rad = modulo(a, TWO_PI ) //Gets radians between 0 and 2PI
    val smallRad = modulo(rad, HALF_PI )
    var (modX,modY) = (1,1)
    val perc = smallRad / HALF_PI

    //modX -= ( ( rad >= HALF_PI ) || ( rad < HALF_PI*3 ) )&2
    //modY -= ( rad >= PI )&2
    if( rad >= HALF_PI && rad < HALF_PI*3 ) modX = -1
    if( rad >= PI ) modY = -1

    lerp( sinValues( math.floor( perc*6 ).toInt ), sinValues( math.ceil( perc*6f ).toInt ), math.ceil( perc*6f ).toFloat - perc*6f )
  }

  def fastCos( a: Float ): Float = {
    fastSin( a-HALF_PI )
  }

  def modulo( a: Float, b: Float ): Float = {
    var c = a
    while( c > b ) c -= b
    while( c < 0f ) c += b
    c
  }
}
