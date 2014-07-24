package com.sg.dg.graphics.shaders

import org.lwjgl.opengl.{ARBFragmentShader, ARBVertexShader, GL11, ARBShaderObjects}
import com.sg.dg.util.IOUtil

/**
 * Created by bodie on 7/20/14.
 */
object ShaderHandler {
  def attachAndLinkShaders( vShaderId: Int = 0, fShaderId: Int = 0 ): Int = {
    val shaderProgramId = ARBShaderObjects.glCreateProgramObjectARB()

    if (shaderProgramId == 0)
    // TODO: throw an exception
      return 0

    if (vShaderId != 0)
      ARBShaderObjects.glAttachObjectARB(shaderProgramId, vShaderId)

    if (fShaderId != 0)
      ARBShaderObjects.glAttachObjectARB(shaderProgramId, fShaderId)

    ARBShaderObjects.glLinkProgramARB(shaderProgramId)
    if (ARBShaderObjects.glGetObjectParameteriARB(shaderProgramId, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
      System.err.println(getLogInfo(shaderProgramId))
      // TODO: throw an exception
      return 0
    }

    ARBShaderObjects.glValidateProgramARB(shaderProgramId)
    if (ARBShaderObjects.glGetObjectParameteriARB(shaderProgramId, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
      System.err.println(getLogInfo(shaderProgramId))
      // TODO: throw an exception
      return 0
    }

    shaderProgramId
  }

  def getLogInfo(obj: Int) = ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB))

  def createVertexShader( filename: String ): Int = {
    val shaderCode = IOUtil.readFileAsString(filename)
    createShader( shaderCode, ARBVertexShader.GL_VERTEX_SHADER_ARB )
  }

  def createFragmentShader( filename: String ): Int = {
    val shaderCode = IOUtil.readFileAsString(filename)
    createShader( shaderCode, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB )
  }

  private def createShader(shaderCode: String, shaderType: Int): Int = {
    val shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType)

    if(shader == 0)
      return 0

    try {
      ARBShaderObjects.glShaderSourceARB(shader, shaderCode)
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

  def useShader( programId: Int ) {
    ARBShaderObjects.glUseProgramObjectARB( programId )
  }

  def endShader( ) {
    ARBShaderObjects.glUseProgramObjectARB( 0 )
  }
}
