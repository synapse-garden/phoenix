package com.sg.dg.graphics.shaders

import org.lwjgl.opengl.{GL11, GL20}
import com.sg.dg.util.IOUtil

/**
 * Created by bodie on 7/20/14.
 */
object ShaderHandler {
  def attachAndLinkShaders( vShaderId: Int = 0, fShaderId: Int = 0 ): Int = {
    val shaderProgramId = GL20.glCreateProgram( )

    if( shaderProgramId == 0 ) {
      throw new RuntimeException("Error creating shader program: " + getProgramLogInfo(shaderProgramId))
      return 0
    }

    if( vShaderId != 0 ) {
      GL20.glAttachShader( shaderProgramId, vShaderId )
    }

    if( fShaderId != 0 ) {
      GL20.glAttachShader( shaderProgramId, fShaderId )
    }

    GL20.glLinkProgram( shaderProgramId )
    if( GL20.glGetProgrami( shaderProgramId, GL20.GL_LINK_STATUS) == GL11.GL_FALSE ) {
      System.err.println( getProgramLogInfo( shaderProgramId ) )
      throw new RuntimeException( "Error linking shader program: " + getProgramLogInfo( shaderProgramId ) )
    }

    GL20.glValidateProgram( shaderProgramId )
    if( GL20.glGetProgrami( shaderProgramId, GL20.GL_VALIDATE_STATUS ) == GL11.GL_FALSE ) {
      System.err.println( )
      throw new RuntimeException( "Error validating shader program: " + getProgramLogInfo( shaderProgramId ) )
    }

    shaderProgramId
  }

  def getProgramLogInfo( p: Int ) = GL20.glGetProgramInfoLog( p, GL20.glGetProgrami( p, GL20.GL_INFO_LOG_LENGTH ) )

  def createVertexShader( filename: String ): Int = {
    val shaderCode = IOUtil.readFileAsString( filename )
    createShader( shaderCode, GL20.GL_VERTEX_SHADER )
  }

  def createFragmentShader( filename: String ): Int = {
    val shaderCode = IOUtil.readFileAsString( filename )
    createShader( shaderCode, GL20.GL_FRAGMENT_SHADER )
  }

  private def createShader( shaderCode: String, shaderType: Int ): Int = {
    val shader = GL20.glCreateShader( shaderType )

    if( shader == 0 )
      return 0

    try {
      GL20.glShaderSource( shader, shaderCode )
      GL20.glCompileShader( shader )

      if( GL20.glGetShaderi( shader, GL20.GL_COMPILE_STATUS ) == GL11.GL_FALSE )
        throw new RuntimeException( "Error creating shader: " + GL20.glGetShaderInfoLog( shader, GL20.glGetShaderi( shader, GL20.GL_INFO_LOG_LENGTH ) ) )

      shader
    }
    catch {
      case e: Exception =>
        GL20.glDeleteShader( shader )
        throw e
    }
  }

  def useProgram( programId: Int ) {
    GL20.glUseProgram( programId )
  }

  def endProgram( ) {
    useProgram( 0 )
  }
}
