package org.ninesyllables.scarlettor
import com.badlogic.gdx.utils.Array
import org.ninesyllables.scarlettor.EntityCleaner
import org.ninesyllables.scarlettor.events.{GameoverEvent, Event}

import scalaz.Maybe

/**
 * Created by LBQ on 2015/9/9.
 *
 * The entire batteries included gamestate object
 *
 * uses mutable state
 */
class GameState(var events : Vector[Event], var entities : Vector[Entity]) {
  var timer = 0
  var gameovered = false
  def update(deltaTime : Float): Unit = {
    timer += 1
    val results = entities map {it => it.update(deltaTime, entities)}
    events = results flatMap {it => it._3}
    for(e <- events){
      e match {
        case GameoverEvent() => gameovered = true
        case _ => null
      }
    }
    val oldEntities = results map {it => it._1}
    val newEntities = oldEntities
    val finalOnes: Vector[(Entity, Vector[Entity])] = newEntities map {it => it.listenEvents(deltaTime, events)}
    val entitiesBeforeCleaning = finalOnes map {it => it._1}
    entities = (entitiesBeforeCleaning filter EntityCleaner) ++ EntityCreator.get(timer) ++ (finalOnes flatMap {it => it._2}) ++ (results flatMap {it => it._2})
  }

  def player : Player = entities.head.asInstanceOf[Player]

  def getBoss : Maybe[BossShooter] = {
    Maybe.fromOption(entities.find(it => it.isInstanceOf[BossShooter]).map(it => it.asInstanceOf[BossShooter]))
  }
}
