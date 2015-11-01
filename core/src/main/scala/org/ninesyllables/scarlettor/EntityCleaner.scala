package org.ninesyllables.scarlettor

/**
 * Created by LBQ on 2015/9/10.
 *
 * filters out those bullets that goes out of bounds
 */
object EntityCleaner extends (Entity => Boolean){
  override def apply(ori: Entity): Boolean = {
    !judgeDead(ori) && !isOutOfBound(ori)
  }

  def judgeDead(ori : Entity) : Boolean = {
    !ori.isInstanceOf[Player] && ori.isDead
  }

  def isOutOfBound(ori : Entity) : Boolean = {
    if (ori.isInstanceOf[Posed]) {
      val pOri = ori.asInstanceOf[Posed]
        pOri.pos.x < Settings.STAGE_LEFT_BOUND - 100 ||
        pOri.pos.x  > Settings.STAGE_RIGHT_BOUND + 100 ||
        pOri.pos.y  > Settings.STAGE_UPPER_BOUND + 100 ||
        pOri.pos.y  < Settings.STAGE_LOWER_BOUND - 100
    } else {
      false
    }
  }
}
