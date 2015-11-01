package org.ninesyllables.scarlettor.utils

import com.badlogic.gdx.{Gdx, Input}

import scala.collection.mutable
import scala.collection.mutable.HashMap

/**
 * Created by LBQ on 2015/9/15.
 *
 * attempt to recreate the RPG Maker Input.trigger? functionality
 *
 * not "plug and play" style
 */
class InputTriggerDetector {
  var prevState = new mutable.HashMap[Int, Boolean]
  record()
  def record() : Unit = {
    for (i <- 0 until 300){
      prevState += (i -> Gdx.input.isKeyPressed(i))
    }
  }
  def update(): Unit ={
    record()
  }

  def isTrigger(key : Int) : Boolean={
    Gdx.input.isKeyPressed(key) && (!prevState(key))
  }
}

object GlobalInputTriggerDetector extends InputTriggerDetector
