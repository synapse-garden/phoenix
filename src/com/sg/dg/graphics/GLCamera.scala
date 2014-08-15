package com.sg.dg.graphics

import com.sg.dg.Inputter
import com.sg.dg.math.Math

import org.lwjgl.util.vector.{Vector3f, Matrix4f}
import org.lwjgl.BufferUtils
import org.lwjgl.input.Keyboard

/**
  * Created by kevin on 7/21/14.
  */

object GLCamera {
  private val ( xAxis, yAxis, zAxis ) = (
    new Vector3f( 1f, 0f, 0f ),
    new Vector3f( 0f, 1f, 0f ),
    new Vector3f( 0f, 0f, 1f )
  )

  private var _cameraMatrix = new Matrix4f( )
  private var _cameraRaw = BufferUtils.createFloatBuffer( 16 )

  def cameraMatrix = _cameraMatrix
  def cameraBuffer = _cameraRaw

  var cameraX = 0f
  var cameraY = 0f
  var cameraZ = 0f

  var yaw = 0.0f
  var pitch = 0.0f
  var roll = 0.0f

  def setupCamera( x: Float = 0f, y: Float = 0f, z: Float = 0f ){
    cameraX =  x
    cameraY = -y
    cameraZ =  z
  }

  def updateCamera( ){
    _cameraRaw.clear( )
    _cameraMatrix.setIdentity( )

    yaw += Inputter.mouseModDX * 0.005f
    pitch += Inputter.mouseModDY * -0.005f

    pitch = Math.clamp( pitch, Math.HALF_PI * -1f, Math.HALF_PI )

    if( Inputter.keysDown( Keyboard KEY_W ) ) {
      cameraX += 0.05f * Math.fastSin( Math.toRad(yaw) )
      cameraZ += -0.05f * Math.fastCos( Math.toRad(yaw) )
    }

    if( Inputter.keysDown( Keyboard KEY_S ) ) {
      cameraX -= 0.03f * Math.fastSin( Math.toRad(yaw) )
      cameraZ -= -0.03f * Math.fastCos( Math.toRad(yaw) )
    }

    if( Inputter.keysDown( Keyboard KEY_A ) ) {
      cameraX += 0.04f * Math.fastCos( Math.toRad(yaw) )
      cameraZ += 0.04f * Math.fastSin( Math.toRad(yaw) )
    }

    if( Inputter.keysDown( Keyboard KEY_D ) ) {
      cameraX -= 0.04f * Math.fastCos( Math.toRad(yaw) )
      cameraZ -= 0.04f * Math.fastSin( Math.toRad(yaw) )
    }

    _cameraMatrix.rotate( yaw, yAxis )
    _cameraMatrix.rotate( pitch, xAxis )

    _cameraMatrix.invert( )
    _cameraMatrix.store( _cameraRaw )
    _cameraRaw.flip( )
  }
}
