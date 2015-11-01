package org.ninesyllables.scarlettor.scenes

import javafx.animation.Transition

import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.ninesyllables.scarlettor.AssetLoader
import org.ninesyllables.scarlettor.control.Act
import org.ninesyllables.scarlettor.utils.{GlobalInputTriggerDetector, TransitionedGroup}

/**
 * Created by LBQ on 2015/9/24.
 *
 * The pause act
 */
class ActPause(mainBatch : SpriteBatch, assetLoader: AssetLoader, cb : Int => Unit) extends Act{
  val pauseChoices = new TransitionedGroup(3,0,60,20)
  val CHOICE_RESUME = 0
  val CHOICE_RETRY = 1
  val CHOICE_TITLE = 2

  override def update(deltaTime: Float): Unit = {
    pauseChoices.update(2)
    if(GlobalInputTriggerDetector.isTrigger(Input.Keys.UP)){
      pauseChoices.prev()
    }
    if(GlobalInputTriggerDetector.isTrigger(Input.Keys.DOWN)){
      pauseChoices.next()
    }
    if(Gdx.input.isKeyPressed(Input.Keys.Z)){
      pauseChoices.currentChoice.map{it : Int =>
        it match {
          case CHOICE_RESUME => cb(0)
          case CHOICE_RETRY => cb(1)
          case CHOICE_TITLE => {
            cb(2)
          }
        }
      }
    }
  }

  override def render(deltaTime: Float, percentage : Float): Unit = {
    mainBatch.begin()
    mainBatch.setColor(1f,1f,1f,percentage * 0.5f)
    mainBatch.draw(assetLoader.allBlack,0,0)
    mainBatch.setColor(1f,1f,1f,1f)
    mainBatch.end()
  }
}
