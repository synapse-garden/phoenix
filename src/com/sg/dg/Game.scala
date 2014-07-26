/**
 * Created by bodie on 7/17/14.
 */

package com.sg.dg

import com.sg.dg.graphics.{Displayer, RGBColor}

import com.sg.dg.util._
import org.joda.time
import com.sg.dg.reality.World
import com.sg.dg.graphics.util.DisplayUtil
import org.lwjgl.util.glu.GLU
import org.lwjgl.opengl.GL11

object Game extends App {
  val DEBUG = false
  // val DEBUG = true
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
