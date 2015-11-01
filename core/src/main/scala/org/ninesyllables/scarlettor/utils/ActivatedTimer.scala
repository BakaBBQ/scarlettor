package org.ninesyllables.scarlettor.utils

/**
 * Created by LBQ on 2015/10/21.
 */
class ActivatedTimer{
  var t = 0
  var activated = false
  def activate() : Unit = {
    activated = true
  }

  def update() : Unit = {
    if (activated)
      t += 1
  }

  def map(fn : Int => Unit) = {
    if (activated)
      fn(t)
  }

  def >(that : ActivatedTimer) : Boolean = this.get > that.get
  def <(that : ActivatedTimer) : Boolean = this.get < that.get

  def |>(that : ActivatedTimer) : ActivatedTimer = {
    if (that > this) that
    else this
  }

  def get: Int = t
}
