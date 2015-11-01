package org.ninesyllables.scarlettor.events

import java.util.UUID

import org.ninesyllables.scarlettor.{AmountType, Entity}

/**
 * Created by LBQ on 2015/9/9.
 *
 * Just the normal programmer's events we are all used to
 */
abstract class Event
case class PlayerCollisionEvent(creator : Entity, player : UUID) extends Event
case class EnemyHitByPlayerBulletEvent(enemy : UUID, damage : Int) extends Event
case class GameoverEvent() extends Event
case class BulletAllDissipateEvent() extends Event
case class BulletDissipateEvent(target : UUID) extends Event
case class PlayerItemCollectEvent(target : UUID, accu : Int, accuType : AmountType) extends Event