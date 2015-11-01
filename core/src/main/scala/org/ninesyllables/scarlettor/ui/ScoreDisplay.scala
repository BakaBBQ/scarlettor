package org.ninesyllables.scarlettor.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.ninesyllables.scarlettor.AssetLoader

/**
 * Created by LBQ on 2015/9/16.
 *
 * Displays the player's current score
 */
object ScoreDisplay {
  def drawScore(score : Int, assetLoader: AssetLoader, uiBatch : SpriteBatch): Unit ={
    val scoreFormatted = "%010d" format score
    val font = assetLoader.scoreFont
    font.draw(uiBatch, scoreFormatted, 60, 50)
  }
}
