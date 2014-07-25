package com.sg.dg.graphics.util

import org.lwjgl.opengl.GL11
import org.lwjgl.util.glu.GLU
import com.sg.dg.graphics.Displayer

/**
 * Created by bodie on 7/20/14.
 */
object GLUtil {
  def setupGL( w: Int, h: Int ) {
    println( "OpenGL version: " + GL11.glGetString( GL11.GL_VERSION ) )
    GL11.glClearColor( 0.4f, 0.6f, 0.9f, 0f )
  }

  def exitOnGLError( errorMessage: String ) {
    val errorValue = GL11.glGetError

    if( errorValue != GL11.GL_NO_ERROR ) {
      val errorString = GLU.gluErrorString( errorValue )
      System.err.println( "ERROR - " + errorMessage + ": " + errorString )

      if( DisplayUtil.isCreated ) DisplayUtil.destroyDisplay( )
      System.exit(-1)
    }
  }
}
