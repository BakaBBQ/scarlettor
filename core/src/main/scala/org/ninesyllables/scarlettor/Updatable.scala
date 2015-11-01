package org.ninesyllables.scarlettor

import org.ninesyllables.scarlettor.events.Event

/**
 * Created by LBQ on 2015/9/9.
 *
 * All those things that could update.......
 *
 *
 */

// TODO: This is not working well, I should fix this stupid Entity and Updatable Division
trait Updatable {
  def update(deltaTime : Float, entities : Vector[Entity]) : (Entity, Vector[Entity], Vector[Event])
  def listenEvents(deltaTime : Float, events : Vector[Event]) : (Entity, Vector[Entity])
}
