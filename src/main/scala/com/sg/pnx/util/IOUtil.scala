package com.sg.pnx.util

import java.io._
import java.util.jar.JarFile

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

  def writeToFile( path: String, data: Array[Byte] ): Unit =
    using( new BufferedOutputStream( new FileOutputStream( new File( path ) ) ) )( _.write( data ) )

  def writeToFile( path: String, data: String ): Unit =
    using( new FileWriter( path ) )( _.write(data) )

  def appendToFile( path: String, data: String ): Unit =
    using( new PrintWriter( new FileWriter(path, true) ) )( _.println(data) )

  // extractJar dumps the contents of the jar at src to the folder dst.
  def extractJar( src: File, dst: File ) {
    val basename = src.getName.substring( 0, src.getName.lastIndexOf(".") )
    dst.mkdirs( )

    val jar = new JarFile( src )
    val enu = jar.entries
    while( enu.hasMoreElements ) {
      val entry = enu.nextElement
      val entryPath =
        if( entry.getName.startsWith(basename) ) entry.getName.substring( basename.length )
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
    val ext = SysUtil.nativeLibExtension
    val toFile = new File( dst, src + ext )
    if( toFile.isFile ) { if( !toFile.delete( ) ) throw new Exception( "ailed to delete lib " + toFile + "." ) }
    val istream = getClass.getClassLoader.getResourceAsStream( src + ext )
    val ostream = new FileOutputStream(toFile)
    copyStream( istream, ostream )
    ostream.close( )
    istream.close( )
  }

  private def copyStream( istream: InputStream, ostream: OutputStream ): Unit = {
    var bytes =  new Array[Byte](1024)
    var len = -1
    while({ len = istream.read(bytes, 0, 1024); len != -1 })
      ostream.write(bytes, 0, len)
  }

  private def using[A <: {def close( ): Unit}, B]( resource: A )( f: A => B ): B =
    try f( resource ) finally resource.close( )
}
