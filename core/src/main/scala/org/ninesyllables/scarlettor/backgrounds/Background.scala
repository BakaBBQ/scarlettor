package org.ninesyllables.scarlettor.backgrounds

/**
 * Created by LBQ on 2015/9/10.
 *
 * the common shared interface between backgrounds
 */
trait Background {
  def render(deltaTime : Float): Unit
  def update(deltaTime : Float): Unit
}
