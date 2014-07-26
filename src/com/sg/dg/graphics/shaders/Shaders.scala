package com.sg.dg.graphics.shaders

import com.sg.dg.Inputter
import com.sg.dg.graphics.util.DisplayUtil

import scala.collection.mutable
import org.lwjgl.opengl._

/**
 * Created by bodie on 7/21/14.
 */
object Shaders {
  var useShaders: Boolean = false

  private var vShaderId: Int = 0
  private var fShaderId: Int = 0
  private var shaderProgramId: Int = 0

  var uniformIds: Array[Int] = Array( 0, 0, 0, 0, 0, 0 )

  var vertShaderInUse: String = ""
  var fragShaderInUse: String = ""

  private val vertShaderSources = mutable.HashMap[String, String](
    "default" -> "res/shaders/screen.vert"
  ).withDefaultValue("ABSENT")

  private val fragShaderSources = mutable.HashMap[String, String](
    "default" -> "res/shaders/screen.frag",
    "sky" -> "res/shaders/sky.frag"
  ).withDefaultValue("ABSENT")

  def vertexShaderNames: List[String] = vertShaderSources.keys.toList
  def fragmentShaderNames: List[String] = fragShaderSources.keys.toList

  def loadAndUseShaders( vShaderName: String = "default", fShaderName: String = "default" ) {
    // TODO: Handle exceptions
    vShaderId = loadVertexShader( vShaderName )
    fShaderId = loadFragmentShader( fShaderName )
    shaderProgramId = ShaderHandler.attachAndLinkShaders( vShaderId, fShaderId )
    if( shaderProgramId != 0 )
      useShaders = true
  }

  private def loadVertexShader( name: String ): Int = {
    val filename = vertShaderSources(name)
    if( filename == "ABSENT" )
      return 0

    vertShaderInUse = name
    ShaderHandler.createVertexShader(filename)
  }

  private def loadFragmentShader(name: String): Int = {
    val filename = fragShaderSources(name)
    if( filename == "ABSENT" )
      return 0

    fragShaderInUse = name
    ShaderHandler.createFragmentShader(filename)
  }

  def useProgram() {
    ShaderHandler.useShader( shaderProgramId )
  }

  def setUniforms( ) {
    GL20.glUseProgram( shaderProgramId )
    uniformIds(0) = GL20.glGetUniformLocation(shaderProgramId, "time" )
    uniformIds(1) = GL20.glGetUniformLocation(shaderProgramId, "resolution" )
    uniformIds(2) = GL20.glGetUniformLocation(shaderProgramId, "mouse" )
  }

  def updateUniforms( ) {
    GL20.glUniform1f( uniformIds(0), System.currentTimeMillis( )/1000.0f )
    GL20.glUniform2i( uniformIds(1), DisplayUtil.width, DisplayUtil.height )
    GL20.glUniform2f( uniformIds(2), Inputter.mouseModDX, Inputter.mouseModDY )
  }

  def endShaders() {
    ShaderHandler.endShader( )
  }
}



