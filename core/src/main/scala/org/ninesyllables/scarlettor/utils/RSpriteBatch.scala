package org.ninesyllables.scarlettor.utils

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{TextureRegion, SpriteBatch}

/**
 * Created by LBQ on 2015/9/12.
 *
 * Enriched sprite batch
 */
class RSpriteBatch(sp : SpriteBatch) {
  def drawAtCenter(tex : TextureRegion, x : Float, y : Float): Unit ={
    sp.draw(tex, x - tex.getRegionWidth/2, y - tex.getRegionHeight / 2)
  }

  def drawAtCenterWithRotation(tex : TextureRegion, x : Float, y : Float, rot : Float) : Unit = {
    sp.draw(tex, x - tex.getRegionWidth/2, y - tex.getRegionHeight/2, tex.getRegionWidth/2, tex.getRegionHeight/2, tex.getRegionWidth, tex.getRegionHeight, 1f, 1f, rot, true)
  }

  def setOpacity(opacity : Float) : Unit = {
    val c = sp.getColor
    sp.setColor(c.r, c.g, c.b, opacity)
  }

  def omnidraw(tex : TextureRegion, x : Float, y : Float, rot : Float = 90, atCenter : Boolean = false, zx : Float = 1, zy : Float = 1, flipX : Boolean = false, flipY : Boolean = false): Unit ={
    val offsetX = if (atCenter) tex.getRegionWidth/2 else 0
    val offsetY = if (atCenter) tex.getRegionHeight/2 else 0
    sp.draw(tex, x - offsetX, y - offsetY, offsetX, offsetY, tex.getRegionWidth, tex.getRegionHeight, zx, zy,rot, true)
  }
}
