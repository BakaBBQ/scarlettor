package org.ninesyllables.scarlettor.io

import java.io.{FileFilter, File}

import com.badlogic.gdx.Gdx
import org.ninesyllables.scarlettor.DirUtils

/**
 * Created by LBQ on 2015/9/25.
 *
 *
 */
class FileList(path : File = DirUtils.cr) {
  val fList = path.listFiles().toList
}
