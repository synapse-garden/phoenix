/**
 * Created by bodie on 7/17/14.
 */

package com.sg.pnx

import com.sg.pnx.graphics.{Displayer, RGBColor}

import com.sg.pnx.util._
import com.sg.pnx.reality.World
import com.sg.pnx.graphics.util.DisplayUtil

object Game extends App {
  val DEBUG = false//true
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
