package org.ninesyllables.scarlettor.utils

import com.badlogic.gdx.math.Vector2

/**
 * Created by LBQ on 2015/10/14.
 */
object Vec2 extends Function2[Float,Float,Vector2]{
  override def apply(v1: Float, v2: Float): Vector2 = new Vector2(v1,v2)
}