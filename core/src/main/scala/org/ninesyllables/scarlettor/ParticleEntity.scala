package org.ninesyllables.scarlettor

import java.util.UUID

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, ParticleEffectPool}
import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.events.Event
import org.ninesyllables.scarlettor.temps.ParticleWarehouse

import scala.collection.immutable.HashMap

/**
 * Created by LBQ on 2015/9/16.
 *
 * Particle Entities, those entities that represent a particle effect, how fun...
 */

case class ParticleEntity(ptEffect : ParticleEffectPool#PooledEffect, pos : Vector2, override val flags : HashMap[String, Boolean] = new HashMap[String, Boolean](),UUID : UUID = java.util.UUID.randomUUID()) extends Entity with Posed{
  override def update(deltaTime: Float, entities: Vector[Entity]): (Entity, Vector[Entity], Vector[Event]) = {
    val newFlags = flags + ("dead" -> ptEffect.isComplete)
    val updatedMe = this.copy(flags = newFlags)
    ptEffect.update(deltaTime)
    (updatedMe, Vector(), Vector())
  }

  override def listenEvents(deltaTime: Float, events: Vector[Event]): (Entity, Vector[Entity]) = {
    (this, Vector())
  }

  override def renderUi(hubBatch: SpriteBatch, assetLoader: AssetLoader): Unit = ???

  override def render(spriteBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {
    ptEffect.setPosition(pos.x, pos.y)
    ptEffect.draw(spriteBatch)
  }
}
