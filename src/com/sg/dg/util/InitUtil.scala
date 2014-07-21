/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg.util

import com.sg.dg.util.DisplayUtil.setupDisplay
import com.sg.dg.util.GLUtil.setupGL

object InitUtil {
  def init() {
    setupDisplay()
    setupGL( DisplayUtil.width, DisplayUtil.height )
    GLCamera setupCamera(0f, 1f, -10.0f)
  }
}
