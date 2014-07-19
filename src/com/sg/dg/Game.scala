/**
 * Created by bodie on 7/17/14.
 */

package com.sg.dg

import com.sg.dg.util._
import org.joda.time

object Game extends App {
  val FRAMERATE = 60
  val MS_PER_FRAME = (1 / FRAMERATE.toFloat * 1000f).toLong
  val done = false

  InitUtil init()
  gameLoop()
  DestroyUtil destroy()

  def gameLoop() {
    val loopTime = time.DateTime.now.getMillis

    val die = Displayer.update || Inputter.killMe

    val diff = time.DateTime.now.getMillis - loopTime
    if( diff > 0 ) Thread sleep( diff )

    Displayer draw

    if( !die ) gameLoop
  }
}
