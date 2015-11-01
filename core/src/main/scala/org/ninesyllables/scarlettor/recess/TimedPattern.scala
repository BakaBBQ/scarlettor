package org.ninesyllables.scarlettor.recess

import org.ninesyllables.scarlettor.ReifiedBullet

import scala.collection.immutable.HashMap
import scalaz.Monad

/**
 * Created by LBQ on 2015/10/21.
 */
case class TimedPattern(contents : Map[Int,List[ReifiedBullet]]){
  def >>=(hashMap: Map[Int, List[ReifiedBullet]]) : TimedPattern = {
    copy(contents = contents ++ hashMap)
  }

  def ++(pt : TimedPattern) : TimedPattern = {
    copy(contents = contents ++ pt.contents)
  }

  def >>(i : Int) : TimedPattern = {
    val newContents = contents.toList.map{it => (it._1 + i, it._2)}.toMap
    copy(contents = newContents)
  }

//  def **(it : Float) : TimedPattern = {
//    copy(contents = contents.mapValues(it => it.map{it2 => it2.copy(vel = it2.vel.)}))
//  }

  def map(fn : ReifiedBullet => ReifiedBullet) : TimedPattern= lift(fn)(this)

  def lift(fn : ReifiedBullet => ReifiedBullet) : (TimedPattern => TimedPattern) = {
    {
      pt : TimedPattern =>
        pt.copy(contents = contents.mapValues(it => it.map(fn)))
    }
  }

  def liftList(fn : List[ReifiedBullet] => List[ReifiedBullet]) : (TimedPattern => TimedPattern) = {
    {
      pt : TimedPattern =>
        pt.copy(contents = contents.mapValues(it => fn(it)))
    }
  }
}
