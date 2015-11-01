package org.ninesyllables.scarlettor.control

import scala.collection.mutable.ArrayBuffer


/**
 * Created by LBQ on 2015/9/11.
 *
 * RPG Maker inspired scenes
 */
trait Scene {
  val acts = new ArrayBuffer[Act]()
  def update(deltaTime : Float) : Unit
}
