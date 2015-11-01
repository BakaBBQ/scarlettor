package org.ninesyllables.scarlettor.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.ninesyllables.scarlettor.{AssetLoader, Player}

/**
 * Created by LBQ on 2015/9/16.
 *
 * Draw the player's power
 */
object PlayerAttributes {
  def drawPlayerAttributes(pl : Player, assetLoader : AssetLoader, uiBatch : SpriteBatch): Unit ={
    val fontBig = assetLoader.adobeArabicFontGroup(2)
    val fontMiddle = assetLoader.adobeArabicFontGroup(1)
    val fontSmall = assetLoader.adobeArabicFontGroup(0)

    val x1 = 418
    val y = 57
    val x2 = 469
    val y2 = 40
    val x3 = 518
    val y3 = 31

    val power = pl.power
    val firstDigit = power / 100
    val secondDigit = power % 100
    val lives = pl.lives
    val firstStr = "%d." format firstDigit
    val secondStr = "%02d" format secondDigit
    val thirdStr = "x" + lives
    fontBig.draw(uiBatch,firstStr, x1, y)
    fontMiddle.draw(uiBatch, secondStr, x2, y2)
    fontSmall.draw(uiBatch, thirdStr, x3, y3)
  }
}
