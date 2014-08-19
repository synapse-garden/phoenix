package com.sg.pnx.util

/**
 * Created by bodie on 7/20/14.
 */
object IOUtil {
  def readFileAsString(filename: String): String = {
    val source = io.Source.fromFile( filename )
    val lines = source.getLines mkString "\n"
    source.close()
    lines
  }
}
