package com.sg.pnx.graphics.glbuffers

import org.lwjgl.opengl._
import scala.collection.mutable
import org.lwjgl.BufferUtils
import com.sg.pnx.graphics.shaders.Shaders
import com.sg.pnx.graphics.util.GLUtil

/**
 * Created by bodie on 7/24/14.
 */
object BufferHandler {
  private val stride = 0
  private val normalized = false
  private var vaoIndexTracker = mutable.HashMap[Int, Int]( )

  def bufferVerts( attrIndex: Int = Shaders.vertexAttribs( "inPosition" ),
                   vertices: Array[Float],
                   vaoId: Int = genVAO( ),
                   vboId: Int = genVBO( ) ): ( Int, Int, Int ) = {

    var thisAttrIndex = attrIndex

    bindVAO( vaoId )
    bindVBO( vboId )
    putVBOVertexData( vertices, thisAttrIndex )
    unbindVBO( )
    unbindVAO( )

    ( vaoId, thisAttrIndex, vboId )
  }

  def putVBOVertexData( vertices: Array[Float], attrIndex: Int ) {
    // Sending data to OpenGL requires the usage of (flipped) byte buffers
    val verticesBuffer = BufferUtils.createFloatBuffer( vertices.length )
    verticesBuffer.put( vertices )
    verticesBuffer.flip( )

    // Use the buffer data
    GL15.glBufferData( GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW )
    GLUtil.exitOnGLError( )
    // Bind to the point in the currently selected VAO
    GL20.glVertexAttribPointer( attrIndex, 3, GL11.GL_FLOAT, normalized, stride, 0 )
  }

  def genVBO( ): Int = {
    GL15.glGenBuffers( )
  }

  def bindVBO( id: Int ) {
    GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, id )
  }

  def unbindVBO( ) {
    bindVBO( 0 )
  }

  def genVAO( ): Int = {
    GL30.glGenVertexArrays( )
  }

  def enableVAO( id: Int ) {
    GL20.glEnableVertexAttribArray( id )
  }

  def disableVAO( id: Int ) {
    GL20.glDisableVertexAttribArray( id )
  }

  def bindVAO( id: Int ) {
    GL30.glBindVertexArray( id )
  }

  def unbindVAO( ) {
    bindVAO( 0 )
  }

  def disableVBOAt( index: Int ) {
    GL20.glDisableVertexAttribArray( index )
  }

  def deleteVBO( id: Int ) {
    GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, id )
    GL15.glDeleteBuffers( id )
  }

  def deleteVAO( id: Int ) {
    GL30.glBindVertexArray( id )
    GL30.glDeleteVertexArrays( id )
  }

  def disposeBuffer( vboId: Int, vaoId: Int, vaoIndex: Int) {
    disableVBOAt( vaoIndex )
    deleteVBO( vboId )
    deleteVAO( vaoId )
  }
}
/*

Request a memory location (returns integer number, the ID, of the object)
Bind the object using the ID
Manipulate the object (the object is whichever object OpenGL is currently bound to)
Unbind the object using the ID
first bind the VAO and then call “glVertexAttributePointer”

Linking a VBO with a VAO's attribute list:
 - “glVertexAttribPointer” as parameters it needs to know the following:
 - Index of the attribute list (0 in our case)
 - How many values define one data definition (3 floats is 1 position definition in our case)
 - What type the values have (floats in our case)
 - The stride and the offset (we are not using those yet, they have to do with interleaving data)

To put the data in the VBO we’ll use the “glBufferData” method. It has also a number of parameters:
 - The type (we’re using a GL_ARRAY_BUFFER, a default definition for generic data)
 - The buffer (our FloatBuffer that holds the vertices positions)
 - The usage (our vertices will not move or change so the usage is simply GL_STATIC_DRAW)

To actually draw our quad we’ll use, as mentioned before, the “glDrawArrays” method. It needs to know the following:
How to draw these vertices (we’re using simple GL_TRIANGLES)
The first index (we’ll start from the beginning at 0)
The vertex count (we’ve held that number in our vertexCount variable)
OpenGL must also have the VAO (and as a result the linked VBO’s) active in memory so we’ll have to bind them before
drawing. Our VBO is linked with the attribute list 0 of our VAO, so we’ll have to enable that list as well. When we’ve
drawn our quad we’ll unbind and disable everything again. The render code is this:

GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

// Bind to the VAO that has all the information about the quad vertices
GL30.glBindVertexArray(vaoId);
GL20.glEnableVertexAttribArray(0);

// Draw the vertices
GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);

// Put everything back to default (deselect)
GL20.glDisableVertexAttribArray(0);
GL30.glBindVertexArray(0);


------ memory cleanup ------


// Disable the VBO index from the VAO attributes list
GL20.glDisableVertexAttribArray(0);

// Delete the VBO
GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
GL15.glDeleteBuffers(vboId);

// Delete the VAO
GL30.glBindVertexArray(0);
GL30.glDeleteVertexArrays(vaoId);

*/