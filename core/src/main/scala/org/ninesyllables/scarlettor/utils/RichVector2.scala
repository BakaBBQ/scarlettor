package org.ninesyllables.scarlettor.utils

import com.badlogic.gdx.math.Vector2

/**
 * Created by LBQ on 2015/9/17.
 *
 * Pimp my own Vector2
 */
class RichVector2(v2 : Vector2) {
  def +(target : Vector2) : Vector2 = {
    new Vector2(v2.x + target.x, v2.y + target.y)
  }

  def -(target : Vector2) : Vector2 = {
    new Vector2(v2.x - target.x, v2.y - target.y)
  }

  def reflect(xAxis : Boolean, yAxis : Boolean) : Vector2 = {
    val xAxisMultiplier = if (yAxis) -1 else 1
    val yAxisMultiplier = if (xAxis) -1 else 1
    return new Vector2(v2.x * xAxisMultiplier, v2.y * yAxisMultiplier)
  }
}

object RichVector2 extends Function2[Float,Float,Vector2]{
  override def apply(v1: Float, v2: Float): Vector2 = {
    new Vector2(v1, v2)
  }
}