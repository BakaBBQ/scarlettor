package org.ninesyllables.scarlettor

import java.util.UUID

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{ParticleEffectPool, SpriteBatch}
import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.effects.InterpolatedEffect
import org.ninesyllables.scarlettor.events.{EnemyHitByPlayerBulletEvent, Event}
import org.ninesyllables.scarlettor.temps.ParticleWarehouse
import org.ninesyllables.scarlettor.utils.{Vec2, Mathy, RSpriteBatch}

import scala.util.Random
import scalaz.Maybe
import scalaz.Maybe.{Empty, Just}

/**
 * Created by LBQ on 2015/9/16.
 *
 * As it says, player bullets
 */
case class PlayerBullet(pos : Vector2, vel : Vector2, timer : Int, bulletType : PlayerBulletType,dissipate : Int = 0, particle : ParticleEffectPool#PooledEffect = ParticleWarehouse.obtainBulletEffect,UUID : UUID = java.util.UUID.randomUUID()) extends Entity with Posed{
  val opacityInterpolate = new InterpolatedEffect(Vec2(0,0), Vec2(15,1))
  val collideInterpolate = new InterpolatedEffect(Vec2(0,1), Vec2(5,0))
  override def update(deltaTime: Float, entities: Vector[Entity]): (Entity, Vector[Entity], Vector[Event]) = {
    val updatedMe = copy(pos = new Vector2(pos.x + vel.x, pos.y + vel.y), timer = timer + 1)
    val newEvents = entities filter {it => it.isInstanceOf[BulletShooter] && detectCollision(it.asInstanceOf[BulletShooter])} map {it => EnemyHitByPlayerBulletEvent(it.UUID, bulletType.damage)}
    val collided = newEvents.nonEmpty
    val updatedMe2 = if (collided && ! isDissipating){
      updatedMe.startDissipate
    } else if (isDissipating)
      updatedMe.copy(dissipate = updatedMe.dissipate + 1)
    else
      updatedMe
    val testParticle = ParticleEntity(ParticleWarehouse.sanaeHitEffectPool.obtain(), pos)
//    val p = Vector(ParticleEntity(ParticleWarehouse.sanaeHitEffectPool.obtain(), new Vector2(pos)))
    (updatedMe2,if (collided && ! isDissipating) Vector(testParticle) else Vector(), newEvents)
  }

  def detectCollision(enemy : BulletShooter) : Boolean = {
    val ctype = enemy.collisionType
    val ctype2 = bulletType.collisionType
    return CollideDetector.detect(pos,enemy.pos,ctype2,ctype)
  }

  override def isDead : Boolean = {
    super.isDead || dissipate > 5
  }

  def isDissipating : Boolean = {
    dissipate > 0
  }

  def startDissipate : PlayerBullet = {
    copy(dissipate = 1)
  }

  override def listenEvents(deltaTime: Float, events: Vector[Event]): (Entity, Vector[Entity]) = {
    (this, Vector())
  }
  implicit def convertToRichSpriteBatch(v : SpriteBatch) : RSpriteBatch = new RSpriteBatch(v)

  override def renderUi(hubBatch: SpriteBatch, assetLoader: AssetLoader): Unit = ???

  override def render(spriteBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {
    spriteBatch.setOpacity(bulletType.opacity * opacityInterpolate(timer) * collideInterpolate(dissipate))
    spriteBatch.drawAtCenter(bulletType.graphicsType(assetLoader), pos.x, pos.y)
    spriteBatch.setOpacity(1f)
  }
}
