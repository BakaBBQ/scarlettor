package org.ninesyllables.scarlettor.utils

/**
 * Created by LBQ on 2015/9/25.
 *
 *
 */
class ActivatedValue(min : Float, max : Float, valueFunc : (Float => Float)) {
  var activated = false
  var timer = 0
  def update() : Unit = {
    if(activated)
      timer += 1
  }

  def get() : Float = {
    if (activated){
      List(List(min,valueFunc(timer)).max, max).min
    } else{
      List(List(min,valueFunc(0)).max, max).min
    }
  }



  def determineMaxed() : Boolean = get() == max

  def activate() : Unit = {
    activated = true
  }
}
