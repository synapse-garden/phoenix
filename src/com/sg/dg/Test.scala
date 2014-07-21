package com.sg.dg

/**
 * Created by bodie on 7/19/14.
 */

object Test extends App {
  println("Remap Test - " + testRemap(550, 0.5f))
  println("Lerp Test - " + testLerp(0.372f, 372))

  def testRemap(in: Float, expected: Float): (Float, Float, Boolean) = {
    val ans = util.Math.remap(in, 500, 600)
    (ans, expected, ans == expected)
  }

  def testLerp(in: Float, expected: Float): (Float, Float, Boolean) = {
    val ans = util.Math.lerp(0, 1000, in)
    (ans, expected, ans == expected)
  }
}
