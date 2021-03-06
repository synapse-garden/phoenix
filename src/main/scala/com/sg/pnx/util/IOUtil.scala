package com.sg.pnx.util

import java.io._
import java.util.jar.JarFile
import java.nio.file.NotLinkException

/**
 * Created by bodie on 7/20/14.
 */
object IOUtil {
  def readFileAsString( filename: String ): String = {
    val source = io.Source.fromFile( filename )
    val lines = source.getLines mkString "\n"
    source.close( )
    lines
  }

  def checkAndMakeDir( dir: File ) {
    if( !dir.exists ) {
      val parent = dir.getParentFile
      if( parent.exists ) { dir.mkdir( ) } // done
      else checkAndMakeDir( parent )
      dir.mkdir( )
    }
  }

  def writeToFile( path: String, data: Array[Byte] ): Unit =
    using( new BufferedOutputStream( new FileOutputStream( new File( path ) ) ) )( _.write( data ) )

  def writeToFile( path: String, data: String ): Unit =
    using( new FileWriter( path ) )( _.write(data) )

  def appendToFile( path: String, data: String ): Unit =
    using( new PrintWriter( new FileWriter(path, true) ) )( _.println(data) )

  // extractJar dumps the contents of the jar at src to the folder dst.
  def extractJar( src: File, dst: File ) {
    checkAndMakeDir( dst )
    val basename = src.getName.substring( 0, src.getName.lastIndexOf(".") )
    dst.mkdirs( )

    val jar = new JarFile( src )
    val enu = jar.entries
    while( enu.hasMoreElements ) {
      val entry = enu.nextElement
      val entryPath =
        if( entry.getName.startsWith( basename ) ) entry.getName.substring( basename.length )
        else entry.getName

      if( entry.isDirectory ) {
        new File( dst, entryPath ).mkdirs( )
      } else {
        val istream = jar.getInputStream( entry )
        val ostream = new FileOutputStream( new File( dst, entryPath ) )
        copyStream( istream, ostream )
        ostream.close( )
        istream.close( )
      }
    }
  }

  def extractJarLibToPath( src: String, dst: File ) = {
    checkAndMakeDir( dst )
    val ext = SysUtil.nativeLibExtension
    val toFile = new File( dst, src )
    if( toFile.isFile ) try{ toFile.delete( ) } catch { case e: NotLinkException => println( "failed to delete lib " + toFile + ": " + e.getMessage ); throw e }
    val istream = getClass.getClassLoader.getResourceAsStream( src )
    val ostream = new FileOutputStream( toFile )
    copyStream( istream, ostream )
    ostream.close( )
    istream.close( )
  }

  private def copyStream( istream: InputStream, ostream: OutputStream ): Unit = {
    var bytes =  new Array[Byte](1024)
    var len = -1
    while({ len = istream.read( bytes, 0, 1024 ); len != -1 })
      ostream.write( bytes, 0, len )
  }

  private def using[A <: {def close( ): Unit}, B]( resource: A )( f: A => B ): B =
    try f( resource ) finally resource.close( )
}
