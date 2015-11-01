package org.ninesyllables.scarlettor.utils

/**
 * Created by LBQ on 2015/9/11.
 *
 * enums for all the bullet looks
 */

abstract class BulletGraphicsType
case class SmallBulletType(column : Int, row : Int) extends BulletGraphicsType
case class BigBulletType(column : Int, row : Int) extends BulletGraphicsType


object SmallBulletColors {
  val GRAY = 0
  val RED = 1
  val PURPLE = 3
  val BLUE = 5
  val SKYBLUE = 7
  val GREEN = 10
  val GREENYELLOW = 13
  val YELLOW = 14
  val WHITE = 15
}

object SmallBulletTypes {
  val RING = 2
  val ROUND = 3
  val RICE = 4
  val STAR = 10
}