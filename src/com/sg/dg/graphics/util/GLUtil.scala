package com.sg.dg.graphics.util

import org.lwjgl.opengl.GL11
import org.lwjgl.util.glu.GLU

/**
 * Created by bodie on 7/20/14.
 */
object GLUtil {
  def setupGL( w: Int, h: Int ) {
    println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION))

    GL11 glViewport( 0, 0, w, h )
    GL11 glMatrixMode( GL11.GL_PROJECTION )
    GL11 glLoadIdentity( )
    GLU gluPerspective( 45.0f, w.toFloat / h.toFloat, 0.1f, 100.0f )
    GL11 glMatrixMode( GL11 GL_MODELVIEW )
    GL11 glLoadIdentity( )
    GL11 glShadeModel( GL11 GL_SMOOTH )
    GL11 glClearColor( 0.0f, 0.0f, 0.0f, 0.0f )
    GL11 glClearDepth( 1.0f )
    GL11 glEnable( GL11 GL_DEPTH_TEST )
    GL11 glDepthFunc( GL11 GL_LEQUAL )
    GL11 glHint( GL11 GL_PERSPECTIVE_CORRECTION_HINT,
      GL11 GL_NICEST )
  }
}
