package org.ninesyllables.scarlettor.effects

/**
 * Created by LBQ on 2015/9/15.
 *
 * Functions that convert to opacity according to time
 */
class FadeInEffect(totalTime : Int) extends (Int => Float){
  override def apply(timer: Int): Float = {
    val rate = 1.0f / totalTime
    return List(timer * rate, 1.0f).min
  }
}