package org.ninesyllables.scarlettor

/**
 * Created by LBQ on 2015/9/9.
 */
object Settings {
  val GAME_WIDTH = 960
  val GAME_HEIGHT = 720

  val STAGE_LEFT_BOUND = 69
  val STAGE_RIGHT_BOUND = 905
  val STAGE_UPPER_BOUND = 675
  val STAGE_LOWER_BOUND = 40

  val PLAYER_SPEED = (2, 5)

  val CAMERA_OFFSET_X = GAME_WIDTH/2 - STAGE_LEFT_BOUND
  val CAMERA_OFFSET_Y = GAME_HEIGHT/2 - STAGE_LOWER_BOUND

  val INVINCIBLE_TIME = 180
}
