package org.ninesyllables.scarlettor.utils

import com.badlogic.gdx.math.Vector2

/**
 * Created by LBQ on 2015/9/10.
 *
 * Two points
 */
case class Line(p1 : Vector2, p2 : Vector2) {
  def disToPoint(p : Vector2): Float ={
    Mathy.pDistance(p, this)
  }

  def isCollideWithCircle(p : Vector2, radius : Float) : Boolean = {
    radius < disToPoint(p)
  }
}
