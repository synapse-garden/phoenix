/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg.util

import com.sg.dg.graphics.util.{DisplayUtil, GLUtil}
import GLUtil.setupGL
import com.sg.dg.graphics.GLCamera

object InitUtil {
  def init() {
    DisplayUtil.setupDisplay( )
    DisplayUtil.setupShaders()
    setupGL( DisplayUtil.width, DisplayUtil.height )
    GLCamera setupCamera(0f, 1.2f, -8.0f)
  }
}
