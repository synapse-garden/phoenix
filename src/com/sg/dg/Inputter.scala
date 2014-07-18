/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg

import org.lwjgl.input.Keyboard.getEventKey
import org.lwjgl.input.Keyboard

object Inputter {
  def die(): Boolean = Keyboard.next && Keyboard.getEventKey == Keyboard.KEY_ESCAPE
}
