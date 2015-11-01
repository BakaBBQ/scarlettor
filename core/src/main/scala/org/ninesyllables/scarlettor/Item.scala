package org.ninesyllables.scarlettor

import java.util.UUID

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.events.{Event, PlayerItemCollectEvent}
import org.ninesyllables.scarlettor.utils.Mathy

import scala.collection.immutable.HashMap

/**
 * Created by LBQ on 2015/9/11.
 *
 * Classical Items
 */
case class Item(pos: Vector2, vel: Vector2, timer: Int, itemType: ItemType, autoAttract: Boolean, override val flags: HashMap[String, Boolean], UUID: UUID = java.util.UUID.randomUUID()) extends Entity with Timed with Posed {
  override def update(deltaTime: Float, entities: Vector[Entity]): (Entity, Vector[Entity], Vector[Event]) = {
    val magnitudeWt = { x: Float => 1 - Math.pow(x, 0.4).toFloat / 4 }
    val magnitude = magnitudeWt(timer)
    val canGetItem = { p: Player => !p.isDead }
    val player = entities.head.asInstanceOf[Player]
    val newSpeed = Mathy.updateSpeed(vel, {
      magnitude * _
    })
    val d = distanceTo(player)
    val didCollide = d < 5 && canGetItem(player)
    val isMoveCloser = (d < 40 || player.pos.y > 533 || autoAttract) && canGetItem(player)
    val attractiveForce = List[Float](d * 10, 7).min
    val attractiveSpeed = Mathy.resizeVector(vectorBetween(player), attractiveForce)
    val vx = if (isMoveCloser) attractiveSpeed.x else if (magnitude < 0) 0 else newSpeed.x
    val vy = if (isMoveCloser) attractiveSpeed.y else if (magnitude < 0) -1.4 else newSpeed.y
    val v = new Vector2(vx.toFloat, vy.toFloat)
    val finalEntity = copy(pos = new Vector2(pos) add v, flags = flags + ("dead" -> didCollide), autoAttract = isMoveCloser)
    val newEntities = Vector()
    val newEvents: Vector[Event] = if (didCollide) Vector(PlayerItemCollectEvent(player.UUID, itemType.getAmount(pos.y), itemType.getAmountType)) else Vector()
    (finalEntity.copy(timer = timer + 1), newEntities, newEvents)
  }

  override def listenEvents(deltaTime: Float, events: Vector[Event]): (Entity, Vector[Entity]) = {
    (this, Vector())
  }

  override def renderUi(hubBatch: SpriteBatch, assetLoader: AssetLoader): Unit = ???

  override def render(spriteBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {
    val tr = itemType.getTextureRegion(assetLoader)
    spriteBatch.draw(tr, pos.x - tr.getRegionWidth / 2, pos.y - tr.getRegionHeight / 2)
  }
}
