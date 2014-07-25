/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg.util

import com.sg.dg.graphics.util.{DisplayUtil, GLUtil}
import GLUtil.setupGL
import com.sg.dg.graphics.GLCamera
import com.sg.dg.Inputter
import com.sg.dg.Game
import com.sg.dg.graphics.glbuffers.Buffers

object InitUtil {
  def init() {
    DisplayUtil.setupDisplay( fullscreen = !Game.DEBUG )
    Inputter.init( )
    Buffers.buildFsQuadVBO( )
    DisplayUtil.setupShaders( )

    setupGL( DisplayUtil.width, DisplayUtil.height )
    GLCamera setupCamera(0f, 1.2f, -8.0f)
  }
}
