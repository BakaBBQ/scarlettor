package org.ninesyllables.scarlettor.rivermist

import org.ninesyllables.scarlettor.events.Event
import org.ninesyllables.scarlettor.{Entity, BossShooter}

/**
 * Created by LBQ on 2015/10/15.
 *
 * generic controller for bossshoters
 */
trait Phase {
  def bossShooter : BossShooter
  def isFinished : Boolean
  def exert : (BossShooter, Vector[Entity])
  def emit : Vector[Event]
  def update : Phase
  def nextPhase : Phase
  def onFinished : (Vector[Entity], Vector[Event])
}