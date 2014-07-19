package com.sg.dg

/**
 * Created by bodie on 7/19/14.
 */

object Test extends App {
  println(testTest(2, 4))

  def testTest(in: Float, expected: Float): (Float, Boolean) = {
    val ans = util.Math.test(in)
    (ans, ans == expected)
  }
}
