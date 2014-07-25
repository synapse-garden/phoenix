package com.sg.dg.graphics.shaders

import scala.collection.mutable

/**
 * Created by bodie on 7/21/14.
 */
object Shaders {
  var useShaders: Boolean = false

  private var vShaderId: Int = 0
  private var fShaderId: Int = 0
  private var shaderProgramId: Int = 0

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

  def endShaders() {
    ShaderHandler.endShader( )
  }
}



