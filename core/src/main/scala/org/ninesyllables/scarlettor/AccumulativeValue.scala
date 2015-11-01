package org.ninesyllables.scarlettor

/**
 * Created by LBQ on 2015/10/13.
 */
class AccumulativeValue(var min : Int, var max : Int) {
  var currentVal = min
  def accu(v : Int = 1) : Unit = {
    currentVal = Math.min(currentVal + v, max)
  }

  def block(v : Int = 1) : Unit = {
    currentVal = Math.max(currentVal - v, min)
  }

  def percentage : Float = (currentVal - min).toFloat / (max - min)
}
