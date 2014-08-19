/**
 * Created by bodie on 7/19/14.
 */

package com.sg.pnx.util

import com.sg.pnx.graphics.util.DisplayUtil
import DisplayUtil._

object DestroyUtil {
  def destroy( ) {
    destroyDisplay( )
    sys exit 0
  }
}
