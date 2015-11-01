package org.ninesyllables.scarlettor

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.effects.InterpolatedEffect
import org.ninesyllables.scarlettor.utils.{BigBulletType, BulletGraphicsType, SmallBulletType}

/**
 * Created by LBQ on 2015/9/9.
 *
 * The common bullet interface
 */
trait Bullet extends Entity with Posed with PlayerCollidable with Timed {
  val vel: Vector2
  val rotation: Float
  val bulletGraphicsType: BulletGraphicsType
  val dissipate: Int
  val DISSIPATE_MAX = 30

  def isDissipating: Boolean = dissipate > 0

  def getDissipateZxy: Float = {
    1 + dissipate / 60f
  }

  def getDissipateOpacity: Float = {
    (30 - dissipate) / 30f
  }

  val zxyFormula = new InterpolatedEffect(new Vector2(0, 2f), new Vector2(30, 1f))
  val opacityFormula = new InterpolatedEffect(new Vector2(0, 0f), new Vector2(30, 1.0f))

  def velAngle: Float = vel.angle()

  def updateNewPos(): Vector2 = {
    vel add pos
  }

  def getBulletTexture(bulletGraphicsType: BulletGraphicsType, assetLoader: AssetLoader): TextureRegion = bulletGraphicsType match {
    case SmallBulletType(c, r) => new TextureRegion(assetLoader.smallBulletSheet, c * 24, r * 24, 24, 24)
    case BigBulletType(c, r) => new TextureRegion(assetLoader.bigBulletSheet, c * 48, r * 48, 48, 48)
  }

  def randUUID(): Bullet

  def nwayShoot(n: Int): Vector[Bullet] = {
    (for (x <- 0 until n) yield rotate(360 / n * x).randUUID().rotateVel(360 / n * x)).toVector
  }

  def expandTo(n: Int, spreadAngle: Int): Vector[Bullet] = {
    val startingAngleD = (n - 1) / 2
    //TODO finish expand to implementation
    Vector(this)
  }



  def rotate(angle: Float): Bullet

  def rotateVel(angle: Float): Bullet
}
