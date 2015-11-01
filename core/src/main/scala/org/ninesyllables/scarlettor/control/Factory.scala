package org.ninesyllables.scarlettor.control

import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.utils.EnemyAI
import org.ninesyllables.scarlettor.{NormalEnemy, Settings, Player}

/**
 * Created by LBQ on 2015/9/12.
 *
 * Common methods for creating the entities
 */
object Factory {
  def centerOfStage : Int = (Settings.STAGE_RIGHT_BOUND - Settings.STAGE_LOWER_BOUND) / 2
  def player : Player = Player(new Vector2(centerOfStage, 100),2,0,300,0,0,0,0,0,0,0)
  def bulletShooter(enemyAI: EnemyAI) : NormalEnemy = {
    NormalEnemy(enemyAI.getInitPos,0,enemyAI.getNewBullets,enemyAI.getMovementStrategy)
  }
}
