package org.ninesyllables.scarlettor.control

/**
 * Created by LBQ on 2015/9/11.
 *
 * Control structures smaller than scenes
 */
trait Act {
  def update(deltaTime : Float) : Unit
  def render(deltaTime : Float, percentage : Float) : Unit
}
