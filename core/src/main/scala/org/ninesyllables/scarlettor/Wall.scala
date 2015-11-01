package org.ninesyllables.scarlettor

/**
 * Created by LBQ on 2015/9/25.
 */
abstract class Wall
case class WallLeft() extends Wall
case class WallRight() extends Wall
case class WallUp() extends Wall
case class WallDown() extends Wall