package org.ninesyllables.scarlettor

import java.util.UUID

import com.badlogic.gdx.graphics.g2d.{TextureRegion, SpriteBatch}
import com.badlogic.gdx.math.{MathUtils, Vector2}
import org.ninesyllables.scarlettor.events.Event
import org.ninesyllables.scarlettor.utils.RSpriteBatch

import scala.collection.immutable.Stack
import scalaz.Maybe
import scalaz.Maybe.{Empty, Just}

/**
 * Created by LBQ on 2015/9/25.
 *
 * BulletShooter specialized
 */
case class BossShooter(pos : Vector2, timer : Int, spellcards : List[Spellcard], bossProperty : BossProperties, UUID : UUID = java.util.UUID.randomUUID()) extends Entity with BulletShooter{

  override def collisionType: CollisionType = new CircleType(20)

  override def renderUi(hubBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {

  }

  implicit def convertToRichSpriteBatch(v: SpriteBatch): RSpriteBatch = new RSpriteBatch(v)

  override def render(spriteBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {
    // (+ (* (Math/sin (Math/toRadians (mod t 360))) 0.2) 0.6)
    val opacity = (MathUtils.sinDeg(timer % 360) * 0.2f + 0.6).toFloat
    val hexagramSize = MathUtils.sinDeg(timer % 360) * 0.1f + 1
    val hexagramRot = (List(Math.pow(timer, 1.1).toFloat, timer * 3600f).min * 2) % 360
    spriteBatch.setOpacity(opacity)
    spriteBatch.omnidraw(new TextureRegion(assetLoader.hexagram),pos.x,pos.y,atCenter = true, zx = hexagramSize, zy = hexagramSize, rot = hexagramRot)
    spriteBatch.setOpacity(1f)
  }

  override def update(deltaTime: Float, entities: Vector[Entity]): (Entity, Vector[Entity], Vector[Event]) = {
    (this.copy(timer = timer + 1), Vector(), Vector())
  }

  def nextSpellcard : Maybe[Spellcard] = ???


  override def listenEvents(deltaTime: Float, events: Vector[Event]): (Entity, Vector[Entity]) = {
    (this, Vector())
  }
}
