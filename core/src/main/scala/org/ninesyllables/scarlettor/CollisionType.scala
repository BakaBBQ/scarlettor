package org.ninesyllables.scarlettor

import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.utils.{Line, Vec2, Mathy, RichVector2}

/**
 * Created by LBQ on 2015/9/25.
 *
 * Shape types
 */
abstract class CollisionType
case class CircleType(radius : Float) extends CollisionType
case class RectType(w : Float, h : Float) extends CollisionType
case class PolygonType(vertices: List[Vector2], radius: Float) extends CollisionType

object CollideDetector {
  implicit private def convertToRichVector2(vector2: Vector2) : RichVector2 = new RichVector2(vector2)
  def detect(pos1 : Vector2, pos2 : Vector2, shape1 : CollisionType, shape2 : CollisionType): Boolean ={
    (shape1, shape2) match {
      case (CircleType(r1), CircleType(r2)) => (pos1 - pos2).len < (r1 + r2)
      case (RectType(w,h), CircleType(r)) => (pos2 - new Vector2(pos1.x + w/2, pos1.y + h/2)).len < r
      case (PolygonType(vertices, polyR), CircleType(r)) => {
        if (broadPhasePolygonCircleDetect(pos1, pos2, polyR, r))
          narrowPhasePolygonCircleDetect(pos1, pos2, vertices, r)
        else
          false
      }
      case _ => ???
    }
  }

  def broadPhasePolygonCircleDetect(p1 : Vector2, p2 : Vector2, polyR : Float, circleR : Float) : Boolean = {
    val totalR = polyR + circleR
    val dis = Mathy.distance(p1,p2)
    dis < totalR
  }
  def narrowPhasePolygonCircleDetect(p1 : Vector2, p2: Vector2, polyV : List[Vector2], circleR : Float) : Boolean = {
    foldVectorsIntoLines(polyV.map{it => it + p1}) exists {it => it.isCollideWithCircle(p2, circleR)}
  }

  def foldVectorsIntoLines(vectors: List[Vector2]) : Vector[Line] = {
    val grouped = vectors.grouped(2).toVector
    val x = grouped map {it => Line(it.head, it.last)}
    x
  }
}