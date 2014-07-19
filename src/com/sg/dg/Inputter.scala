/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg

import org.lwjgl.input.Keyboard.getEventKey
import org.lwjgl.input.Keyboard
import collection.mutable.HashMap

object Inputter {

  val keysDown = HashMap[Int, Boolean](Keyboard.KEY_ESCAPE -> false).withDefaultValue(false)

  var (mouseX, mouseY) = (0, 0)

  def exitRequested: Boolean = keysDown(Keyboard.KEY_ESCAPE)

  def update {
    while( Keyboard.next ) keysDown += Keyboard.getEventKey() -> true
  }
}
