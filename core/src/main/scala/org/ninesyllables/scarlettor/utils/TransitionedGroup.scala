package org.ninesyllables.scarlettor.utils


import scalaz.Maybe
import scalaz.Maybe.{Empty, Just}

/**
 * Created by LBQ on 2015/9/15.
 *
 * A generalized selection group
 */
class TransitionedGroup(num : Int, var currentSelected : Int, max : Int, min : Int) {
  var groupTimers = new Array[Int](num)
  for (x <- groupTimers.indices){
    groupTimers(x) = min
  }
  groupTimers(currentSelected) = max
  def update(ticks : Int = 1) : Unit = {
    for (t <- 0 until ticks){
      for(i <- 0 until num){
        if (i == currentSelected){
          groupTimers(i) = Mathy.incUntil(groupTimers(i), max)
        } else {
          groupTimers(i) = Mathy.decUntil(groupTimers(i), min)
        }
      }
    }
  }

  def next() : Unit = {
    currentSelected = Mathy.incUntil(currentSelected, num-1)
  }

  def prev() : Unit = {
    currentSelected = Mathy.decUntil(currentSelected, 0)
  }

  def select(i : Int) : Boolean = {
    if(0 <= i && i < num){
      currentSelected = i
      true
    } else false
  }

  def currentChoice : Maybe[Int] = {
    if(groupTimers(currentSelected) == max)
      Just(currentSelected)
    else
      Empty[Int]()
  }

  def percentage(i : Int) : Float = groupTimers(i).toFloat / max
}
