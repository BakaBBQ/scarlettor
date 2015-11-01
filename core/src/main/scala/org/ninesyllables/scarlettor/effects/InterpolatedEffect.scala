package org.ninesyllables.scarlettor.effects

import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.utils.LinearFunction

/**
 * Created by LBQ on 2015/9/16.
 *
 * a linear interpolated function...
 */
class InterpolatedEffect(p0 : Vector2, p1 : Vector2) extends (Float => Float){
  val k = (p1.y - p0.y) / (p1.x - p0.x)
  val b = p0.y - p0.x * k
  val f = new LinearFunction(k,b)
  override def apply(v1: Float): Float = {
    if (v1 > p1.x) {
      p1.y
    } else {
      f(v1)
    }
  }
}
