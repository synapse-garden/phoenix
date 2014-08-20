package com.sg.pnx

import com.sg.pnx.math.Math
import System.nanoTime

/**
 * Created by bodie on 7/19/14.
 */

object Test extends App {
  println("Remap Test - " + testRemap(550, 0.5f))
  println("Lerp Test - " + testLerp(0.372f, 372))
  println("Fast Sin Test A - Roots - " + testFastSinA )
  println("Fast Sin Test B - Average Error - " + testFastSinB)
  println("Fast Sin Test C - Efficiency Improvement - " + testFastSinC)

  def testRemap( in: Float, expected: Float): (Float, Float, Boolean ) = {
    val ans = Math.remap(in, 500, 600)
    (ans, expected, ans == expected)
  }

  def testLerp( in: Float, expected: Float): (Float, Float, Boolean ) = {
    val ans = Math.lerp(0, 1000, in)
    (ans, expected, ans == expected)
  }

  def testFastSinA( ): (Int) = {
    val testRads = List( 0f, Math.HALF_PI, Math.PI, Math.HALF_PI*3f )
    val answers = List( 0f, 1f, 0f, -1f )
    var correct = 0
    var i = 0

    for( i <- 0 to 3 ){
      if( Math.fastSin( testRads(i.toInt) ) == answers( i.toInt ) ) correct += 1
    }
    correct
  }

  def testFastSinB( ): (Float) = {
    var i = 0
    var error = 0.0f

    for( i <- 0 to 500 ){
      error += Math.fastSin( (i/500f) ) - scala.math.sin( (i/500f) ).toFloat
    }

    error / 500f
  }

  def testFastSinC( ): (Float) = {
    var i = 0
    val timeA = System.nanoTime
    val tempA = for (i <- 0 to 1000000000 ) scala.math.sin( i*0.042375f )
    val timeB = System.nanoTime
    val tempB = for (i <- 0 to 1000000000 ) Math.fastSin( i*0.042375f )
    val timeC = System.nanoTime

    (timeB - timeA).toFloat / (timeC - timeB).toFloat
  }
}
