package org.ninesyllables.scarlettor

import java.util.UUID

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.events.{EnemyHitByPlayerBulletEvent, Event}
import org.ninesyllables.scarlettor.utils.RSpriteBatch

import scalaz.Maybe

/**
 * Created by LBQ on 2015/9/10.
 *
 * Bullet shooters, those who shoot bullets
 */
case class NormalEnemy(pos : Vector2, timer : Int,  shootStrategy : Function3[NormalEnemy, Player, Vector[Event],Vector[Entity]], movementStrategy : Function1[Int, Vector2], deathEntities : Vector[Entity] = Vector(), hp : Int = 100, radius : Float = 10, UUID : UUID = java.util.UUID.randomUUID()) extends Entity with Posed with Timed with BulletShooter{
  implicit def convertToRichSpriteBatch(v : SpriteBatch) : RSpriteBatch = new RSpriteBatch(v)

  override def update(deltaTime: Float, entities : Vector[Entity]): (Entity, Vector[Entity], Vector[Event]) = {
    (copy(timer = timer + 1), shootStrategy(this,entities.head.asInstanceOf[Player],Vector()), Vector())
  }

  override def listenEvents(deltaTime: Float, events: Vector[Event]): (Entity, Vector[Entity]) = {
    var accuDmg = 0
    for (i <- events){
      i match {
        case EnemyHitByPlayerBulletEvent(this.UUID, d) => accuDmg += d
        case _ => null
      }
    }
    this.receiveDamage(accuDmg)
  }

  def receiveDamage(x : Int) : (NormalEnemy, Vector[Entity])= {
    val ents = if (hp < x)
      deathEntities
    else
      Vector()
    (copy(hp = hp - x), ents)
  }

  override def isDead : Boolean = super.isDead || hp < 0

  def getDx : Float = {
    val t = timer
    movementStrategy(t).x
  }

  def isHorzMovingAtFrameBeforeTicksWithDir(dir : Float, f : Int) : Boolean = {
    val ticks = 3 * f
    val t = timer - ticks
    val p = movementStrategy(t)
    val (dx, dy) = (p.x, p.y)
    val rt = t / 3
    val isHorzMoving = Math.abs(dx) > 0.5
    val r = if (isHorzMoving) (dx > 0) == (dir > 0) else false
    return r
  }

  def judgeMovement : Int = {
    val currentMovDir = getDx
    List(1,2,3,4,5) map {isHorzMovingAtFrameBeforeTicksWithDir(currentMovDir, _)} count {_ == true}
  }

  override def render(spriteBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {
    val movement = judgeMovement
    val texs = assetLoader.allEnemyTextures
    val rt = timer / 3
    val isHorzMoving = Math.abs(movementStrategy(timer).x) > 0.5
    val tindex = if (isHorzMoving){
      5 + (if (movement >= 4) 3 + (rt % 3) else movement)
    } else {
      rt % 5
    }
    val flipped = if (isHorzMoving) movementStrategy(timer).x < 0 else false
    val tex = texs(tindex)(0)
    spriteBatch.omnidraw(tex, pos.x, pos.y, atCenter = true, flipX = flipped)
  }

  override def renderUi(hubBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {

  }

  val collisionType = CircleType(10)
}
