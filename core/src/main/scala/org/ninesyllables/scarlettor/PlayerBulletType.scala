package org.ninesyllables.scarlettor

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

/**
 * Created by LBQ on 2015/9/14.
 *
 * definitions for player bullets
 */
abstract class PlayerBulletType {
  def collisionType : CollisionType
  def opacity : Float
  def damage : Int
  def graphicsType(assetLoader: AssetLoader) : TextureRegion
}

case class SanaeNormalBullet() extends PlayerBulletType{
  val collisionType = RectType(4,15)
  val damage = 3
  def graphicsType(assetLoader: AssetLoader) : TextureRegion = {
//    val t = assetLoader.sanaeShots
//    new TextureRegion(t, 192, 240, 24, 24)
    new TextureRegion(assetLoader.reimuShot)
  }

  val opacity = 0.4f
}
