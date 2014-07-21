/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg

import org.lwjgl.input.Mouse
import org.lwjgl.input.Keyboard
import collection.mutable.HashMap
import com.sg.dg.util.DisplayUtil
import com.sg.dg.util.Math

object Inputter {
  val keysDown = HashMap[Int, Boolean](Keyboard.KEY_ESCAPE -> false).withDefaultValue(false)

  var (mouseX, mouseY) = (0, 0)
  var (mouseDX, mouseDY) = (0, 0)
  var (mouseModDX, mouseModDY) = (0f, 0f)

  def exitRequested: Boolean = keysDown(Keyboard.KEY_ESCAPE)

  def update {
    while( Keyboard.next ) keysDown += Keyboard.getEventKey() -> true
    mouseX = Mouse.getX - DisplayUtil.halfWidth
    mouseY = Mouse.getY - DisplayUtil.halfHeight
    mouseDX = Mouse.getDX
    mouseDY = Mouse.getDY
    mouseModDX = Math.lerp( mouseModDX, mouseDX, 0.2f )
    mouseModDY = Math.lerp( mouseModDY, mouseDY, 0.2f )
    //Mouse.setCursorPosition(-mouseDX, -mouseDY)
  }
}
