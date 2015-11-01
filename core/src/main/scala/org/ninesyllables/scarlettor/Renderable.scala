package org.ninesyllables.scarlettor

import com.badlogic.gdx.graphics.g2d.SpriteBatch

/**
 * Created by LBQ on 2015/9/10.
 *
 * represents something that could be rendered on a spritebatch
 */
trait Renderable {
  def render(spriteBatch: SpriteBatch, assetLoader: AssetLoader) : Unit
  def renderUi(hubBatch: SpriteBatch, assetLoader: AssetLoader) : Unit
}
