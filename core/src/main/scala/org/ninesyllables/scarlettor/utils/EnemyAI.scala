package org.ninesyllables.scarlettor.utils

import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.events.Event
import org.ninesyllables.scarlettor._

/**
 * Created by LBQ on 2015/9/24.
 *
 * Note: Enemy AI, instead of Boss AI
 *       This should successfully describe all enemy's behavior
 */
trait EnemyAI {
  def getInitPos : Vector2
  def getNewBullets : Function3[NormalEnemy, Player, Vector[Event], Vector[Entity]]
  def getDeathItems : Function1[NormalEnemy, Item]
  def getMovementStrategy : Function1[Int,Vector2]
}
