/**
 * Created by bodie on 7/17/14.
 */

package com.sg.dg

import com.sg.dg.graphics.{Displayer, RGBColor}

import com.sg.dg.util._
import org.joda.time
import com.sg.dg.reality.World
import com.sg.dg.graphics.util.DisplayUtil

object Game extends App {
  val DEBUG = true
  val FRAMERATE = 60
  val MS_PER_FRAME = 1000 / FRAMERATE

  InitUtil init()
  World init()
  gameLoop()
  DestroyUtil destroy()

  def gameLoop() {
    val loopBeginTime = time.DateTime.now.getMillis

    Inputter.update()
    val die = DisplayUtil.die || Inputter.exitRequested

    World.update()
    Displayer.update()

    val frameTime = time.DateTime.now.getMillis - loopBeginTime
    if( MS_PER_FRAME - frameTime > 0 ) Thread sleep (MS_PER_FRAME - frameTime)

    Displayer.draw()

    if( !die ) gameLoop()
  }
}
