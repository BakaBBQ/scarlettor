package org.ninesyllables.scarlettor

import com.badlogic.gdx.graphics.g2d.TextureRegion

/**
 * Created by LBQ on 2015/9/11.
 *
 * Defining various items
 */

abstract class ItemType {
  val POWER_POS = List(0,13)
  val SCORE_POS = List(1,13)
  val BIG_POWER_POS = List(3,13)
  val SHARD_POS = List(7,13)
  val toRealPos = {it : Int => it * 24}
  def getTextureRegion(assetLoader: AssetLoader) : TextureRegion
  def getAmount(yPos : Float) : Int
  def getAmountType : AmountType
}

abstract class AmountType
case class PowerAmountType() extends AmountType
case class PointAmountType() extends AmountType

case class PowerItemType() extends ItemType {
  override def getTextureRegion(assetLoader: AssetLoader): TextureRegion = {
    val pos = POWER_POS map toRealPos
    new TextureRegion(assetLoader.itemAndEffectSheet, pos.head, pos.last, 24, 24)
  }

  override def getAmount(yPos: Float): Int = 5

  override def getAmountType: AmountType = PowerAmountType()
}

case class BigPowerItemType() extends ItemType {
  override def getTextureRegion(assetLoader: AssetLoader): TextureRegion = {
    val pos = BIG_POWER_POS map toRealPos
    new TextureRegion(assetLoader.itemAndEffectSheet, pos.head, pos.last, 24, 24)
  }

  override def getAmount(yPos: Float): Int = 100

  override def getAmountType: AmountType = PowerAmountType()
}

case class PointItemType() extends ItemType {
  override def getTextureRegion(assetLoader: AssetLoader): TextureRegion = {
    val pos = SCORE_POS map toRealPos
    new TextureRegion(assetLoader.itemAndEffectSheet, pos.head, pos.last, 24, 24)
  }

  override def getAmount(yPos: Float): Int = {
    if (yPos > 533) {
      500
    } else {
      Math.round(500 - 400f/533f * yPos)
    }
  }

  override def getAmountType: AmountType = PointAmountType()
}
case class ShardItemType() extends ItemType {
  override def getTextureRegion(assetLoader: AssetLoader): TextureRegion = {
    val pos = SHARD_POS map toRealPos
    new TextureRegion(assetLoader.itemAndEffectSheet, pos.head, pos.last, 24, 24)
  }

  override def getAmount(yPos: Float): Int = 10

  override def getAmountType: AmountType = PointAmountType()
}