package org.ninesyllables.scarlettor

import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.utils.Mathy

/**
 * Created by LBQ on 2015/9/9.
 *
 * Represents all entities that has a position
 */
trait Posed {
  val pos: Vector2

  def distanceTo(e : Posed) : Float = {
    Mathy.distance(e.pos, pos)
  }

  def vectorBetween(t : Posed) : Vector2 = {
    new Vector2(t.pos.x - pos.x, t.pos.y - pos.y)
  }

}
