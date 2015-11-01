package org.ninesyllables.scarlettor.utils

import com.badlogic.gdx.math.Vector2

/**
 * Created by LBQ on 2015/9/10.
 *
 * Convenient class for creating polar vectors
 */
object PolarVector2 {
  def polarConstruct(r: Float, angle: Float): Vector2 = {
    val vec: Vector2 = new Vector2(r, 0)
    vec.setAngle(angle)
    return vec
  }
}

class PolarVector2(r : Float, theta : Float) extends Vector2 {
  val pc = PolarVector2.polarConstruct(r, theta)
  x = pc.x
  y = pc.y
}