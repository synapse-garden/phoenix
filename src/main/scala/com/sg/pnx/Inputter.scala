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
  }; import KeyToggle._

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
    _mouseX = Mouse.getX
    _mouseY = Mouse.getY
    _mouseDX = Mouse.getDX
    _mouseDY = Mouse.getDY
    _mouseModX  = Math.lerp( _mouseModX, _mouseX, 0.2f )
    _mouseModY  = Math.lerp( _mouseModY, _mouseY, 0.2f )
    _mouseModDX = Math.lerp( _mouseModDX, _mouseDX, 0.2f )
    _mouseModDY = Math.lerp( _mouseModDY, _mouseDY, 0.2f )
  }

  private def unsetMouseMotion( ) {
    _mouseDX = 0
    _mouseDY = 0
    _mouseModX = 0
    _mouseModY = 0
    _mouseModDX = 0
    _mouseModDY = 0
  }

  private def toggle( state: KeyToggle, keyDown: Boolean ): KeyToggle = state match {
    case `s0` => if(  keyDown ) s1 else s0
    case `s1` => if( !keyDown ) s2 else s1
    case `s2` => if(  keyDown ) s3 else s2
    case `s3` => if( !keyDown ) s0 else s3
  }

  private def updateState( ) {
    mouseGrabToggle = toggle( mouseGrabToggle, keysDown( Keyboard.KEY_ESCAPE ) )
    if( mouseGrabToggle == s1 ) { Mouse.setGrabbed( true ); mouseGrabbed = false }
    if( mouseGrabToggle == s3 ) { Mouse.setGrabbed( false ); mouseGrabbed = true }
  }

  // Die if you press ESC.
  def exitRequested: Boolean = keysDown( Keyboard.KEY_Q )

  private var mouseGrabToggle = s2
  private var mouseGrabbed = true

  private var (_mouseX, _mouseY) = (0, 0)
  private var (_mouseModX, _mouseModY) = (0f, 0f)

  private var (_mouseDX, _mouseDY) = (0, 0)
  private var (_mouseModDX, _mouseModDY) = (0f, 0f)

  def mouseX = _mouseX; def mouseY = _mouseY
  def mouseDX = _mouseDX; def mouseDY = _mouseDY
  def mouseModX = _mouseModX; def mouseModY = _mouseModY
  def mouseModDX = _mouseModDX; def mouseModDY = _mouseModDY

  def timeNs: Long = _timeNs
  def frameTime: Long = _frameTime

  def init( ) {
    println( "Press Q to quit, ESC to release or grab mouse" )
    Mouse.setGrabbed( true )
    initTime( )
  }

  def update( ) {
    updateTime( )
    while( Keyboard.next ) keysDown += Keyboard.getEventKey -> Keyboard.getEventKeyState
    updateState( )
    updateMouseGrabbed( )
  }
}
