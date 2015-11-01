package org.ninesyllables.scarlettor

import java.util.UUID

import org.ninesyllables.scarlettor.events.Event

import scala.collection.immutable.HashMap
import scala.util.Random

/**
 * Created by LBQ on 2015/9/10.
 *
 * an updatable with flags
 */
abstract class Entity extends Updatable with Renderable{
  val UUID : UUID
  val flags : HashMap[String, Boolean] = HashMap("dead" -> false)
  def isDead : Boolean = {
    flags.get("dead").isEmpty || flags.get("dead").get
  }

  def mark(key : String, value : Boolean) : HashMap[String, Boolean] = {
    flags + (key -> value)
  }
}
