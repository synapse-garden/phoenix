/**
 * Created by bodie on 7/19/14.
 */

package com.sg.pnx

import org.lwjgl.input.Mouse
import org.lwjgl.input.Keyboard
import collection.mutable
import com.sg.pnx.graphics.util.DisplayUtil
import com.sg.pnx.math.Math

object Inputter {
  private object KeyToggle extends Enumeration {
    type KeyToggle = Value
    val s0, s1, s2, s3 = Value
  }

  import KeyToggle._

  val keysDown = mutable.HashMap[Int, Boolean]( Keyboard.KEY_ESCAPE -> false ).withDefaultValue(false)

  private var _timeNs: Long = 0
  private var _frameTime: Long = 0

  private def updateTime( ) {
    val nowTime = System.nanoTime
    _frameTime = nowTime - timeNs
    _timeNs = nowTime
  }

  private def initTime( ) {
    _timeNs = System.nanoTime
  }

  private def updateMouseGrabbed( ) {
    mouseDX = Mouse.getX - DisplayUtil.halfWidth
    mouseDY = Mouse.getY - DisplayUtil.halfHeight
    mouseX = mouseX + mouseDX
    mouseY = mouseY + mouseDY
    mouseModX = Math.lerp( mouseModX, mouseX, 0.2f )
    mouseModY = Math.lerp( mouseModY, mouseY, 0.2f )
    mouseModDX = Math.lerp( mouseModDX, mouseDX, 0.2f )
    mouseModDY = Math.lerp( mouseModDY, mouseDY, 0.2f )
    Mouse.setCursorPosition( DisplayUtil.halfWidth, DisplayUtil.halfHeight )
  }

  private def unsetMouseMotion( ) {
    mouseDX = 0
    mouseDY = 0
    mouseX = DisplayUtil.halfWidth
    mouseY = DisplayUtil.halfHeight
    mouseModX = 0
    mouseModY = 0
    mouseModDX = 0
    mouseModDY = 0
    Mouse.setCursorPosition( DisplayUtil.halfWidth, DisplayUtil.halfHeight )
  }

  private def toggle( state: KeyToggle, keyDown: Boolean ): KeyToggle = state match {
    case `s0` => if(  keyDown ) s1 else s0
    case `s1` => if( !keyDown ) s2 else s1
    case `s2` => if(  keyDown ) s3 else s2
    case `s3` => if( !keyDown ) s0 else s3
  }

  private def updateState( ) {
    _mouseGrabbed = toggle( _mouseGrabbed, keysDown( Keyboard.KEY_ESCAPE ) )
    if( _mouseGrabbed == s1 ) { unsetMouseMotion( ) }
  }

  // Die if you press ESC.
  def exitRequested: Boolean = keysDown( Keyboard.KEY_Q )

  private var _mouseGrabbed = s2
  def mouseGrabbed = _mouseGrabbed == s1 || _mouseGrabbed == s2

  private var (_mouseX, _mouseY) = (0, 0)
  var (mouseDX, mouseDY) = (0, 0)
  var (mouseModDX: Float, mouseModDY: Float) = (0f, 0f)
  var (mouseModX: Float, mouseModY: Float) = (0f, 0f)

  def mouseX = _mouseX
  def mouseY = _mouseY
  private def mouseX_=( newX: Int ) = _mouseX = Math.clamp( newX, 0, DisplayUtil.width )
  private def mouseY_=( newY: Int ) = _mouseY = Math.clamp( newY, 0, DisplayUtil.width )

  def timeNs: Long = _timeNs
  def frameTime: Long = _frameTime

  def init( ) {
    println( "Press Q to quit, ESC to release or grab mouse" )
    mouseX = DisplayUtil.halfWidth
    mouseY = DisplayUtil.halfHeight
    mouseDX = mouseX
    mouseDY = mouseY
    Mouse.setCursorPosition( DisplayUtil.halfWidth, DisplayUtil.halfHeight )
    initTime( )
  }

  def update( ) {
    updateTime( )
    while( Keyboard.next ) keysDown += Keyboard.getEventKey -> Keyboard.getEventKeyState
    updateState( )
    if( mouseGrabbed ) updateMouseGrabbed( )
  }
}
