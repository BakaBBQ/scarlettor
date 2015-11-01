package org.ninesyllables.scarlettor.temps

import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.events.Event
import org.ninesyllables.scarlettor.utils.{SmallBulletTypes, SmallBulletType, PolarVector2}
import org.ninesyllables.scarlettor._

/**
 * Created by LBQ on 2015/9/11.
 *
 * Test strategy for shooters
 */
object TestStrategy extends Function3[NormalEnemy, Player, Vector[Event],Vector[Entity]]{
  def apply(v1: NormalEnemy, p : Player, v : Vector[Event]): Vector[Entity] = {
    v1.timer % 60 match {
      case 0 => ReifiedBullet(v1.pos, new PolarVector2(2, 270), v1.timer % 360, CircleType(10), SmallBulletType(1, SmallBulletTypes.ROUND),0).nwayShoot(60)
      case 30 => ReifiedBullet(v1.pos,new PolarVector2(3, 270), (v1.timer % 180) * 2, CircleType(10), SmallBulletType(1, SmallBulletTypes.ROUND),0).nwayShoot(35)
      case _ => Vector()
    }
  }
}