package com.sg.dg.graphics.shaders

import com.sg.dg.Inputter
import com.sg.dg.graphics.GLCamera
import com.sg.dg.graphics.util.{GLUtil, DisplayUtil}
import org.joda.time

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
    "resolution" -> 0,
    "cameraPos" -> 0,
    "cameraRot" -> 0,
    "camera" -> 0
  ).withDefaultValue( 0 )

  private val vertShaderSources = mutable.HashMap[String, String](
    "default" -> "res/shaders/screen.vert"
  ).withDefaultValue( "ABSENT" )

  private val fragShaderSources = mutable.HashMap[String, String](
    "default" -> "res/shaders/screen.frag",
    "sky" -> "res/shaders/sky.frag",
    "look" -> "res/shaders/look.frag"
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
    GL20.glUniform1f( uniforms( "time" ), Inputter.timeMs / 1000.0f )
    GL20.glUniform2i( uniforms( "resolution" ), DisplayUtil.width, DisplayUtil.height )
    GL20.glUniform2f( uniforms( "mouse" ), Inputter.mouseModX, Inputter.mouseModY )
    GL20.glUniform3f( uniforms( "cameraPos" ), GLCamera.cameraX, GLCamera.cameraY, GLCamera.cameraZ )
    GL20.glUniform3f( uniforms( "cameraRot" ), GLCamera.yaw, -1.0f*GLCamera.pitch, GLCamera.roll )
    GL20.glUniformMatrix4( uniforms( "camera" ), false, GLCamera.cameraBuffer )
    GLUtil.exitOnGLError( )
  }

  def endProgram( ) {
    ShaderHandler.endProgram( )
  }
}


