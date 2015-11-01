package org.ninesyllables.scarlettor

/**
 * Created by LBQ on 2015/9/9.
 *
 * Represents those that could possibly collide with the player
 */
trait PlayerCollidable {
  def detectCollide(p : Player) : Boolean
}
