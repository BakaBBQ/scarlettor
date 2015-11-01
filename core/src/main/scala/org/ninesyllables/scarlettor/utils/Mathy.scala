package org.ninesyllables.scarlettor.utils

import com.badlogic.gdx.math.Vector2

import scala.collection.immutable.IndexedSeq


/**
 * Created by LBQ on 2015/9/10.
 *
 * some Maths related methods for convenience
 */
object Mathy {
  def distance(v1 : Vector2, v2 : Vector2) : Float = {
    (new Vector2(v2) sub v1).len
  }

  def incUntil(x : Int, bounds : Int) = List(x + 1, bounds).min
  def decUntil(x : Int, bounds : Int) = List(x - 1, bounds).max
  val inc = {i : Int => i + 1}
  val dec = {i : Int => i - 1}
  val inBound = {(lb : Float, v : Float, rb : Float) => if (v >= rb) rb else if (v <= lb) lb else v}
  val inBoundI = {(lb : Int, v : Int, rb : Int) => if (v >= rb) rb else if (v <= lb) lb else v}
  val decAbs = {v : Int => if (v > 0) v - 1 else if (v < 0) v + 1 else v}
  val seqToVector2 = {(seq : List[Float]) => new Vector2(seq.head, seq.last)}

  def boundedFunc[V <: Number](min : V, max : V, fn : (Int => V)) : (Int => V) = {
    new Function1[Int,V] {
      override def apply(v1: Int): V = {
        val result = fn(v1)
        if (result.floatValue() < min.floatValue())
          return min
        if (result.floatValue() > max.floatValue())
          return max
        return result
      }
    }
  }

  def pDistance(point : Vector2, line : Line) : Float = {
    val x = point.x
    val y = point.y
    val (x1, y1) = (line.p1.x, line.p1.y)
    val (x2, y2) = (line.p2.x, line.p2.y)
    val a = x - x1
    val b = y - y1
    val c = x2 - x1
    val d = y2 - y1
    val dot = a*c + b*d
    val lenSq = c*c + d*d
    val param = if (lenSq == 0) -1 else dot/lenSq
    val xxyy = if (param < 0) (x1, y1) else (if (param > 1) (x2, y2) else (x1 + param*c, y1 + param*d))
    val xx = xxyy._1
    val yy = xxyy._2
    val dx = x - xx
    val dy = y - yy
    Math.hypot(dx, dy).toFloat
  }

  def updateSpeed(v : Vector2, fn : Float => Float) : Vector2 = {
    new PolarVector2(fn(v.len()), v.angle())
  }

  def resizeVector(v : Vector2, t : Float) : Vector2 = {
    updateSpeed(v, {it => t})
  }

  def ellipseApproximate(a : Int, b : Int, pCount : Int) : Vector[Vector2] = {
    val step = 2 * Math.PI / pCount
    val points: IndexedSeq[Vector2] = for (i <- 0 until pCount) yield new Vector2(a * Math.cos(i*step).toFloat, b * Math.sin(i*step).toFloat)
    points.toVector
  }
}
