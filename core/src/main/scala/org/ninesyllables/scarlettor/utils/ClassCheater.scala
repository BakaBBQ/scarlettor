package org.ninesyllables.scarlettor.utils

import com.badlogic.gdx.graphics.Texture

/**
 * Created by LBQ on 2015/9/11.
 *
 * Workarounds for scala classes
 */
object ClassCheater {
  def getTextureClass: Class[Texture] = {
    return classOf[Texture]
  }
}