package com.sg.pnx.graphics.shaders

import com.sg.pnx.Inputter
import com.sg.pnx.graphics.GLCamera
import com.sg.pnx.graphics.util.{GLUtil, DisplayUtil}

import org.lwjgl.opengl.{GL40, GL20}
import org.lwjgl.BufferUtils

import scala.collection.mutable
import scala.collection.immutable

/**
 * Created by bodie on 7/21/14.
 */
object Shaders {
  private var vShaderId: Int = 0
  private var fShaderId: Int = 0
  private var shaderProgramId: Int = 0

  private var uniforms = mutable.HashMap[String, Int](
    "time" -> 0,
    "mouse" -> 0,
    "resolution" -> 0,
    "cameraPos" -> 0,
    "cameraRot" -> 0,
    "camera" -> 0
  ).withDefaultValue( 0 )

  private var _vertexAttribs = mutable.HashMap[String, Int](
    "inPosition" -> 0
  )

  private var vertexSubroutines = mutable.HashMap[String, mutable.HashMap[String, Int]](
    "vertexCamera" -> mutable.HashMap[String, Int] (
      "self" -> 0,
      "fsQuad" -> 0,
      "world" -> 0
    )
  )

  private val defaultVertexSubroutines = immutable.HashMap[String, String](
    "vertexCamera" -> "fsQuad"
  )

  private var fragmentSubroutines = mutable.HashMap[String, mutable.HashMap[String, Int]](
    "fragmentProcess" -> mutable.HashMap[String, Int] (
      "self" -> 0,
      "fsQuad" -> 0,
      "world" -> 0
    )
    // subroutines for frag shader go here
  )

  private val defaultFragmentSubroutines = immutable.HashMap[String, String](
    "fragmentProcess" -> "fsQuad"
  )

  private var vertexSubroutineBuffer = BufferUtils.createIntBuffer( vertexSubroutines.keys.size )
  private var fragmentSubroutineBuffer = BufferUtils.createIntBuffer( fragmentSubroutines.keys.size )

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
  def vertexAttribs = _vertexAttribs

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

  def endProgram( ) {
    ShaderHandler.endProgram( )
  }

  def getUniforms( ) {
    for( uniformName <- uniforms.keys ) {
      uniforms( uniformName ) = GL20.glGetUniformLocation( shaderProgramId, uniformName )
      GLUtil.exitOnGLError( "error in getUniforms: " + uniformName + ": id = " + uniforms( uniformName ) )
    }
  }

  def updateUniforms( ) {
    GL20.glUniform1f( uniforms( "time" ), Inputter.timeNs / 1000000000.0f )
    GL20.glUniform2i( uniforms( "resolution" ), DisplayUtil.width, DisplayUtil.height )
    GL20.glUniform2f( uniforms( "mouse" ), Inputter.mouseModX, Inputter.mouseModY )
    GL20.glUniform3f( uniforms( "cameraPos" ), GLCamera.cameraX, GLCamera.cameraY, GLCamera.cameraZ )
    GL20.glUniform3f( uniforms( "cameraRot" ), GLCamera.yaw, -1.0f*GLCamera.pitch, GLCamera.roll )
    GL20.glUniformMatrix4( uniforms( "camera" ), false, GLCamera.cameraBuffer )
    GLUtil.exitOnGLError( )
  }

  def getAttribs( ) {
    for( attrName <- vertexAttribs.keys ) {
      vertexAttribs( attrName ) = GL20.glGetAttribLocation( shaderProgramId, attrName )
      GLUtil.exitOnGLError( "error in getAttribs: " + attrName + ": id = " + vertexAttribs( attrName ) )
    }
  }

  def getSubroutines( ) {
    getStageSubroutines( GL20.GL_VERTEX_SHADER )
    getStageSubroutines( GL20.GL_FRAGMENT_SHADER )
  }

  private def getStageSubroutines( target: Int ) {
    val (subroutineMap, defaultTargetSubroutines, targetBuffer) = target match {
      case GL20.GL_VERTEX_SHADER => (vertexSubroutines, defaultVertexSubroutines, vertexSubroutineBuffer)
      case GL20.GL_FRAGMENT_SHADER => (fragmentSubroutines, defaultFragmentSubroutines, fragmentSubroutineBuffer)
      case _ => (null, null, null)
    }

    // Go through the list of subroutine uniforms.
    for( subUniformName <- subroutineMap.keys ) {
      // First, get the ID of the uniform name itself, as "self".
      subroutineMap( subUniformName )( "self" ) = GL40.glGetSubroutineUniformLocation( shaderProgramId, target, subUniformName )
      // Then, for each subroutine for the uniform,
      for( subName <- subroutineMap( subUniformName ).keys if subName != "self" ) {
        // Get the ID for that subroutine and attach it to the named key in the map.
        subroutineMap( subUniformName )( subName ) = GL40.glGetSubroutineIndex( shaderProgramId, target, subName )
        GLUtil.exitOnGLError( "error in getStageSubroutines for " + target + ": " + subName + ": id = " + subroutineMap( subUniformName )( subName ) )
      }
      // Set the default value in the buffer
      targetBuffer.put( subroutineMap( subUniformName )( "self" ), subroutineMap( subUniformName )( defaultTargetSubroutines( subUniformName ) ) )
    }
  }

  def setVertexSubroutines( subs: (String, String)* ) {
    setSubroutine( GL20.GL_VERTEX_SHADER, subs )
  }

  def setFragmentSubroutines( subs: (String, String)* ) {
    setSubroutine( GL20.GL_FRAGMENT_SHADER, subs )
  }

  private def setSubroutine( target: Int, subs: Seq[(String, String)] ) {
    val (subroutineMap, buf) = target match {
      case GL20.GL_VERTEX_SHADER => (vertexSubroutines, vertexSubroutineBuffer)
      case GL20.GL_FRAGMENT_SHADER => (fragmentSubroutines, fragmentSubroutineBuffer)
      case _ => (null, null)
    }

    for( (uniform, sub) <- subs ) {
      buf.put( subroutineMap( uniform )( "self" ), subroutineMap( uniform )( sub ) )
    }

    GL40.glUniformSubroutinesu( target, buf )
    GLUtil.exitOnGLError( )
  }
}


