package org.ninesyllables.scarlettor.utils

/**
 * Created by LBQ on 2015/9/24.
 *
 */
class MinMaxState(var value : Int, min : Int, max : Int) {
  def decrease(x : Int): Unit = {
    value = List(value - x, min).max
  }

  def increase(x : Int) : Unit = {
    value = List(value + x, max).min
  }
}
