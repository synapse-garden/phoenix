/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg

import org.lwjgl.input.Mouse
import org.lwjgl.input.Keyboard
import collection.mutable.HashMap

object Inputter {
  val keysDown = HashMap[Int, Boolean](Keyboard.KEY_ESCAPE -> false).withDefaultValue(false)

  var (mouseX, mouseY) = (0, 0)
  var (mouseDX, mouseDY) = (0, 0)

  def exitRequested: Boolean = keysDown(Keyboard.KEY_ESCAPE)

  def update {
    while( Keyboard.next ) keysDown += Keyboard.getEventKey() -> true
    mouseX = Mouse.getX
    mouseDX = Mouse.getDX
    mouseY = Mouse.getY
    mouseDY = Mouse.getDY
  }
}
