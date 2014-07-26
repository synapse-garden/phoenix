package com.sg.dg.graphics.shaders

import com.sg.dg.Inputter
import com.sg.dg.graphics.util.{GLUtil, DisplayUtil}

import scala.collection.mutable
import org.lwjgl.opengl.GL20

/**
 * Created by bodie on 7/21/14.
 */
object Shaders {
  private var vShaderId: Int = 0
  private var fShaderId: Int = 0
  private var shaderProgramId: Int = 0

  var uniforms = mutable.HashMap[String, Int](
    "time" -> 0,
    "mouse" -> 0,
    "resolution" -> 0
  ).withDefaultValue( 0 )

  private val vertShaderSources = mutable.HashMap[String, String](
    "default" -> "res/shaders/screen.vert"
  ).withDefaultValue( "ABSENT" )

  private val fragShaderSources = mutable.HashMap[String, String](
    "default" -> "res/shaders/screen.frag",
    "sky" -> "res/shaders/sky.frag"
  ).withDefaultValue( "ABSENT" )

  var useShaders: Boolean = false

  var vertShaderInUse: String = ""
  var fragShaderInUse: String = ""

  def vertexShaderNames: List[String] = vertShaderSources.keys.toList
  def fragmentShaderNames: List[String] = fragShaderSources.keys.toList

  def loadAndUseShaders( vShaderName: String = "default", fShaderName: String = "default" ) {
    try {
      vShaderId = loadVertexShader( vShaderName )
      fShaderId = loadFragmentShader( fShaderName )
      shaderProgramId = ShaderHandler.attachAndLinkShaders( vShaderId, fShaderId )
    } catch {
      case e: Exception =>
        System.err.println( "Shader linking error: " + e.getMessage )
        System.exit( 0 )
    }
    if( shaderProgramId != 0 )
      useShaders = true
  }

  private def loadVertexShader( name: String ): Int = {
    val filename = vertShaderSources( name )
    if( filename == "ABSENT" )
      return 0

    vertShaderInUse = name
    ShaderHandler.createVertexShader( filename )
  }

  private def loadFragmentShader( name: String ): Int = {
    val filename = fragShaderSources( name )
    if( filename == "ABSENT" )
      return 0

    fragShaderInUse = name
    ShaderHandler.createFragmentShader( filename )
  }

  def useProgram( ) {
    ShaderHandler.useProgram( shaderProgramId )
  }

  def setUniforms( ) {
    for( uniformName <- uniforms.keys ) {
      uniforms( uniformName ) = GL20.glGetUniformLocation( shaderProgramId, uniformName )
    }
  }

  def updateUniforms( ) {
    GL20.glUniform1f( uniforms( "time" ), System.currentTimeMillis( ) / 1000.0f )
    GL20.glUniform2i( uniforms( "resolution" ), DisplayUtil.width, DisplayUtil.height )
    GL20.glUniform2f( uniforms( "mouse" ), Inputter.mouseModDX, Inputter.mouseModDY )
    GLUtil.exitOnGLError( "Error in updateUniforms" )
  }

  def endProgram( ) {
    ShaderHandler.endProgram( )
  }
}



