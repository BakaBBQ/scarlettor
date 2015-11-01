package org.ninesyllables.scarlettor.utils

/**
 * Created by LBQ on 2015/9/16.
 *
 * y = kx + b
 */
class LinearFunction(k : Float, b : Float) extends (Float => Float){
  override def apply(x: Float): Float = k * x + b
}
