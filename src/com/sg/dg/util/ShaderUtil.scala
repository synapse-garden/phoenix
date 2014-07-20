package com.sg.dg.util

import org.lwjgl.opengl.{GL11, ARBShaderObjects}

/**
 * Created by bodie on 7/20/14.
 */
object ShaderUtil {
  def getLogInfo(obj: Int) = ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB))

  /*
  * With the exception of syntax, setting up vertex and fragment shaders
  * is the same.
  * @param the name and path to the vertex shader
  */
  def createShader(filename: String, shaderType: Int): Int = {
    val shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType)

    if(shader == 0)
      return 0

    try {
      ARBShaderObjects.glShaderSourceARB(shader, IOUtil.readFileAsString(filename))
      ARBShaderObjects.glCompileShaderARB(shader)

      if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
        throw new RuntimeException("Error creating shader: " + getLogInfo(shader))

      shader
    }
    catch {
      case e: Exception =>
        ARBShaderObjects.glDeleteObjectARB(shader)
        throw e
    }
  }
}
