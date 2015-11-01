package org.ninesyllables.scarlettor

import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.backgrounds.Background

import scalaz.Maybe

/**
 * Created by LBQ on 2015/10/9.
 */
abstract class PatternType
case class NonspellPatternType() extends PatternType
case class SpellcardPatternType() extends PatternType
abstract class Spellcard(name : String, duration : Int, var timer : Int, hp : Int = 3000, UUID : java.util.UUID = java.util.UUID.randomUUID()) {
  def initialPosition : Maybe[Vector2]
  def shoot(me: Posed, target : Posed) : List[Bullet]
  def movement() : Vector2
  def onDeathEntities : List[Entity]
  def calcBonus(): Int
  def background: Maybe[Background]
  def patternType: PatternType
}
