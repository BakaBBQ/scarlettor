package org.ninesyllables.scarlettor

import java.util.UUID

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.{TextureRegion, SpriteBatch}
import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.events.{BulletAllDissipateEvent, BulletDissipateEvent, PlayerCollisionEvent, Event}
import org.ninesyllables.scarlettor.utils.{GlobalInputTriggerDetector, RichVector2, RSpriteBatch, BulletGraphicsType}

/**
 * Created by LBQ on 2015/9/9.
 *
 * Two variants of bullets, this one are the plain round bullets that everyone loves
 */
case class ReifiedBullet(pos : Vector2, vel : Vector2, rotation : Float, collisionType : CollisionType, bulletGraphicsType: BulletGraphicsType, timer : Int, UUID : UUID = java.util.UUID.randomUUID(), dissipate : Int = 0) extends Bullet{
  override def detectCollide(p: Player): Boolean = {
    CollideDetector.detect(pos, p.pos, collisionType, p.collisionType)
  }

  override def rotate(angle : Float): Bullet = {
    copy(rotation = rotation + angle)
  }

  implicit private def convertToRichVector2(vector2: Vector2) : RichVector2 = new RichVector2(vector2)
  def bounce(wall : Wall, player: Player) : ReifiedBullet = {
    wall match {
      case WallLeft() => copy(vel = vel.reflect(false, true))
      case WallRight() => copy(vel = vel.reflect(false, true))
      case WallDown() => copy(vel = vel.reflect(true, false))
      case WallUp() => copy(vel = vel.reflect(true, false))
      case _ => ???
    }
  }

  lazy val radius : Float = collisionType.asInstanceOf[CircleType].radius

  def updateBounce(player : Player) : ReifiedBullet = {
    if(pos.x - Settings.STAGE_LEFT_BOUND <= radius) {
      return bounce(WallLeft(), player)
    }
    if((Settings.STAGE_RIGHT_BOUND - pos.x) <= radius) {
      return bounce(WallRight(), player)
    }
    if((pos.y - Settings.STAGE_LOWER_BOUND) <= radius) {
      return bounce(WallDown(), player)
    }
    if((Settings.STAGE_UPPER_BOUND - pos.y) <= radius) {
      return bounce(WallUp(), player)
    }
    this
  }

  override def renderUi(hubBatch: SpriteBatch, assetLoader: AssetLoader): Unit = ???

  implicit def convertToRichSpriteBatch(v : SpriteBatch) : RSpriteBatch = new RSpriteBatch(v)

  override def render(spriteBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {
      val texture = getBulletTexture(bulletGraphicsType, assetLoader)
      val opacity = opacityFormula(timer)
      val zxy = zxyFormula(timer)
      spriteBatch.setOpacity(opacity * getDissipateOpacity)
      spriteBatch.omnidraw(texture, pos.x, pos.y, atCenter = true, zx = zxy * getDissipateZxy, zy = zxy * getDissipateZxy)
      spriteBatch.setOpacity(1f)
  }

  override def isDead : Boolean = {
    super.isDead || (dissipate == DISSIPATE_MAX)
  }

  def reflect(xAxis : Boolean, yAxis : Boolean) : ReifiedBullet = {
    copy(vel = vel.reflect(xAxis, yAxis))
  }

  override def update(deltaTime: Float, entities: Vector[Entity]): (Entity, Vector[Entity], Vector[Event]) = {
    val player = entities.head.asInstanceOf[Player]
    val collided = detectCollide(player)
    val events = if (collided) Vector(new PlayerCollisionEvent(this, player.UUID)) else Vector()
    val updatedMe = copy(pos = new Vector2(pos.x + vel.x, pos.y + vel.y), timer = timer + 1)
    (updatedMe.updateDissipate(), Vector(), events)
  }

  def startDissipating() : ReifiedBullet = {
    if (dissipate > 0)
      this
    else
      copy(dissipate = 1)
  }

  def updateDissipate() : ReifiedBullet = {
    if (dissipate > 0)
      copy(dissipate = List(dissipate + 1, DISSIPATE_MAX).min)
    else
      this
  }

  override def listenEvents(deltaTime: Float, events: Vector[Event]): (Entity, Vector[Entity]) = {
    var dissipateFlag = false
    for (e <- events){
      e match {
        case BulletDissipateEvent(this.UUID) => dissipateFlag = true
        case BulletAllDissipateEvent() => dissipateFlag = true
        case _ => null
      }
    }
    val updatedMe = if (dissipateFlag) startDissipating() else this
    (updatedMe, Vector())
  }

  override def rotateVel(angle: Float): Bullet = {
    copy(vel = new Vector2(vel).rotate(angle))
  }

  override def randUUID: Bullet = copy(UUID = java.util.UUID.randomUUID())
}
