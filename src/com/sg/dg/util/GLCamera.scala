package com.sg.dg.util

import com.sg.dg.Inputter
import org.lwjgl.opengl.GL11

/**
 * Created by kevin on 7/21/14.
 */

object GLCamera {
  var cameraX = 0f
  var cameraY = 0f
  var cameraZ = 0f
  //the rotation around the Y axis of the camera
  var yaw = 0.0f
  //the rotation around the X axis of the camera
  var pitch = 0.0f

  def setupCamera( x: Float, y: Float, z: Float ){
    cameraX = x
    cameraY = -y
    cameraZ = z
    // yaw = -60f
    // pitch = 30f
  }

  def updateCamera( ){
    yaw += Inputter.mouseModDX * 0.06f
    pitch += Inputter.mouseModDY * -0.06f
  }

  def lookThrough( ){
    //roatate the pitch around the X axis
    GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f)
    //roatate the yaw around the Y axis
    GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f)
    //translate to the position vector's location
    GL11.glTranslatef(cameraX, cameraY, cameraZ)
  }
}