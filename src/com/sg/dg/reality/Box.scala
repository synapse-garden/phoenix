package com.sg.dg.reality

import org.lwjgl.opengl.{GL11, ARBShaderObjects, ARBFragmentShader, ARBVertexShader}
import java.lang.System.err

/**
 * Created by bodie on 7/20/14.
 */
class Box( private var programShaderId: Int,
           private var vShaderId: Int,
           private var fShaderId: Int,
           private var useShader: Boolean ) {

  def draw( ) = {
    if( useShader )
      ARBShaderObjects.glUseProgramObjectARB( programShaderId )

    GL11 glLoadIdentity( )
    GL11 glTranslatef( 0.0f, 0.0f, -10.0f )
    GL11 glColor3f( 1.0f, 1.0f, 1.0f )//white

    GL11 glBegin(GL11 GL_QUADS)
    GL11 glVertex3f( -1.0f,  1.0f, 0.0f )
    GL11 glVertex3f(  1.0f,  1.0f, 0.0f )
    GL11 glVertex3f(  1.0f, -1.0f, 0.0f )
    GL11 glVertex3f( -1.0f, -1.0f, 0.0f )
    GL11 glEnd()

    //release the shader
    if( useShader )
      ARBShaderObjects.glUseProgramObjectARB(0)
  }
}
