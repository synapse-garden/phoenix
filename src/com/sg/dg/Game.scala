/**
 * Created by bodie on 7/17/14.
 */

package com.sg.dg

import com.sg.dg.graphics.{Displayer, RGBColor}

import com.sg.dg.util._
import com.sg.dg.reality.World
import com.sg.dg.graphics.util.DisplayUtil

object Game extends App {
  val DEBUG = true
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
    Displayer.draw( )
    Displayer.sync( FRAMERATE )

    if( !die ) gameLoop( )
  }
}
