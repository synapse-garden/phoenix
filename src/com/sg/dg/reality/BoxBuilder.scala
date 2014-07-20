package com.sg.dg.reality

import com.sg.dg.util.ShaderUtil
import org.lwjgl.opengl.{GL11, ARBShaderObjects, ARBFragmentShader, ARBVertexShader}

/**
 * Created by bodie on 7/20/14.
 */
object BoxBuilder {

  def buildBox(): Box = {
    val vShaderId = ShaderUtil.createShader("res/shaders/screen.vert", ARBVertexShader.GL_VERTEX_SHADER_ARB)
    val fShaderId = ShaderUtil.createShader("res/shaders/screen.frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB)
    val programShaderId = ARBShaderObjects.glCreateProgramObjectARB()

    if (programShaderId == 0)
      return null

    /*
    * if the vertex and fragment shaders were set up successfully,
    * attach them to the shader programShaderId, link the shader programShaderId
    */
    ARBShaderObjects.glAttachObjectARB(programShaderId, vShaderId)
    ARBShaderObjects.glAttachObjectARB(programShaderId, fShaderId)

    ARBShaderObjects.glLinkProgramARB(programShaderId)
    if (ARBShaderObjects.glGetObjectParameteriARB(programShaderId, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
      java.lang.System.err.println(ShaderUtil.getLogInfo(programShaderId))
      return null
    }

    ARBShaderObjects.glValidateProgramARB(programShaderId)
    if (ARBShaderObjects.glGetObjectParameteriARB(programShaderId, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
      System.err.println(ShaderUtil.getLogInfo(programShaderId))
      return null
    }

    val useShader = true

    new Box( programShaderId, vShaderId, fShaderId, useShader )
  }
}
