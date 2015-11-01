package org.ninesyllables.scarlettor.scenes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.ninesyllables.scarlettor.AssetLoader
import org.ninesyllables.scarlettor.control.Act

/**
 * Created by LBQ on 2015/9/25.
 *
 * Self-explanatory, the gameover act
 */
class ActGameover(uiBatch : SpriteBatch, assetLoader: AssetLoader) extends Act{
  var timer = 1
  override def update(deltaTime: Float): Unit = {
    timer += 1
  }

  override def render(deltaTime: Float, percentage: Float): Unit = {
    uiBatch.begin()
    uiBatch.setColor(1f,1f,1f,percentage * 0.5f)
    uiBatch.draw(assetLoader.allBlack,0,0)
    uiBatch.setColor(1f,1f,1f,1f)
    uiBatch.end()
  }
}
