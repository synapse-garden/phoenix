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
  val DEBUG = false
  val FRAMERATE = 60

  InitUtil init( )
  World init( )
  gameLoop( )
  DestroyUtil destroy( )

  def gameLoop( ) {
    Inputter.update( )
    val die = DisplayUtil.die || Inputter.exitRequested

    World.update( )
    Displayer.update( )

    Displayer.sync( FRAMERATE )

    Displayer.draw( )

    if( !die ) gameLoop( )
  }
}
