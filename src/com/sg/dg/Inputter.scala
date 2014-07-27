/**
 * Created by bodie on 7/19/14.
 */

package com.sg.dg

import org.lwjgl.input.Mouse
import org.lwjgl.input.Keyboard
import collection.mutable.HashMap
import com.sg.dg.graphics.util.DisplayUtil
import com.sg.dg.math.Math
import org.joda.time.DateTime

object Inputter {
  val keysDown = HashMap[Int, Boolean](Keyboard.KEY_ESCAPE -> false).withDefaultValue(false)

  private var _timeMs: Long = 0
  private var _frameTime: Long = 0

  private def updateTime( ) {
    val nowTime = DateTime.now.getMillis( )
    _frameTime = nowTime - timeMs
    _timeMs = nowTime
  }

  private def initTime( ) {
    _timeMs = DateTime.now.getMillis( )
  }

  private def updateMouse( ) {
    mouseDX = Mouse.getX - DisplayUtil.halfWidth
    mouseDY = Mouse.getY - DisplayUtil.halfHeight
    mouseX = mouseX + mouseDX
    mouseY = mouseY + mouseDY
    mouseModDX = Math.lerp( mouseModDX, mouseDX, 0.2f )
    mouseModDY = Math.lerp( mouseModDY, mouseDY, 0.2f )
    Mouse.setCursorPosition( DisplayUtil.halfWidth, DisplayUtil.halfHeight )
  }

  private var (_mouseX, _mouseY) = (0, 0)
  var (mouseDX, mouseDY) = (0, 0)
  var (mouseModDX: Float, mouseModDY: Float) = (0f, 0f)

  def mouseX = _mouseX
  def mouseY = _mouseY
  def mouseX_=( newX: Int ) = _mouseX = Math.clampI( newX, 0, DisplayUtil.width )
  def mouseY_=( newY: Int ) = _mouseY = Math.clampI( newY, 0, DisplayUtil.width )

  def exitRequested: Boolean = keysDown( Keyboard.KEY_ESCAPE )
  def timeMs: Long = _timeMs
  def frameTime: Long = _frameTime

  def init( ) {
    mouseX = DisplayUtil.halfWidth
    mouseY = DisplayUtil.halfHeight
    mouseDX = mouseX
    mouseDY = mouseY
    Mouse.setCursorPosition( DisplayUtil.halfWidth, DisplayUtil.halfHeight )
    initTime( )
  }

  def update( ) {
    updateTime( )
    while( Keyboard.next ) keysDown += Keyboard.getEventKey -> true
    updateMouse( )
  }
}
