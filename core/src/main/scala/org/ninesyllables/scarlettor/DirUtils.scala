package org.ninesyllables.scarlettor

import java.io.File

/**
 * Created by LBQ on 2015/9/16.
 *
 * Create config files, read config files, blah... translated from the java version
 */
object DirUtils {

  val OS = System.getProperty("os.name").toUpperCase
  def getResFolder : String = {
    val directory = if(OS.contains("WIN")){
      System.getenv("AppData")
    } else {
      System.getProperty("user.home") + "Library/Application Support"
    }
    directory
  }

  def writeToFile(p: File, s: String): Unit = {
    val pw = new java.io.PrintWriter(p)
    try pw.write(s) finally pw.close()
  }

  val cr = new File(getResFolder, "/CascadingRed/data/")
  val p = new File(getResFolder + "/CascadingRed/config.toml")
  def prepare() : Unit = {
    val f = new File(getResFolder + "/CascadingRed/")
    var a = false
    if (!p.exists()){
      try {
        f.mkdirs()
        p.createNewFile()
      } catch {
        case e : Exception => {
          println("Cannot create file")
          a = true
        }
      }
      if (!a){
        val s =
          """
            |#CascadingRed Configuration File
            |[window]
            |resolution = [960, 720]
            |title = 'Cascading Red Alpha'
            |fullscreen = false
          """.stripMargin
        writeToFile(p, s)
      }
    }
    f.mkdirs()
  }
}
