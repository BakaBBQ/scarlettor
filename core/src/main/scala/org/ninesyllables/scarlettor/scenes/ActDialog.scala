package org.ninesyllables.scarlettor.scenes

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, BitmapFont}
import org.ninesyllables.scarlettor.{AssetLoader, AccumulativeValue, DialogState}
import org.ninesyllables.scarlettor.control.Act
import org.ninesyllables.scarlettor.dialogs.{CalmState, EmotionState, DialogScript}

import scala.collection.immutable.HashMap
import scala.collection.mutable.ArrayBuffer

/**
 * Created by LBQ on 2015/10/14.
 *
 * A lot of mutable state
 */

class BufferedDialogWindow(font : BitmapFont) {
  val WORD_SPEED = 2
  var contents : String = ""
  var timer = 0

  def update(): Unit ={
    timer = timer + 1
  }

  def reset() : Unit = {
    contents = ""
    timer = 0
  }

  def currentWordCount : Int = {
    Math.min(timer / WORD_SPEED, contents.length)
  }

  def currentNewWordPercentage : Float = {
    (timer % WORD_SPEED).toFloat / WORD_SPEED
  }

  def render(spriteBatch: SpriteBatch) : Unit = {
    val completePart = contents.substring(0,currentWordCount)
    font.drawMultiLine(spriteBatch,completePart,100,100)
    if (currentWordCount > 0 && currentWordCount < contents.length){
      val incompletePart = contents.charAt(currentWordCount)
      val widthDet = completePart.lines.toArray.last
      val heightDet = completePart
      val xOff = font.getBounds(widthDet)
      val yOff = font.getBounds(heightDet)
      font.setColor(1,1,1,currentNewWordPercentage)
      font.draw(spriteBatch, incompletePart.toString, 100 + xOff.width,100 - yOff.height)
      font.setColor(1,1,1,1)
    }
  }

  def push(txt : String) : Unit = {
    contents += txt
  }
}

abstract class Side
case class LeftSide() extends Side
case class RightSide() extends Side

class ActorSpeaker(texs : HashMap[EmotionState, Texture], side : Side){
  val accuValue = new AccumulativeValue(0,30)
  var active = false
  var currentEmotion : EmotionState = CalmState()

  def update() : Unit = {
    if (active) accuValue.accu() else accuValue.block()
  }

  def render(spriteBatch: SpriteBatch) : Unit = {
//    val currentTex = texs(currentEmotion)
//    spriteBatch.setColor(1,1,1,accuValue.percentage)
//    spriteBatch.draw(currentTex,0,0)
    spriteBatch.setColor(1,1,1,1)
  }
}

class ActDialog(spriteBatch : SpriteBatch, dialogState: DialogState, assetLoader: AssetLoader) extends Act{
  def script : DialogScript = dialogState.dialogScript

  // FIXME: read the actual HashMap of textures
  val speakerSprites = for (i <- script.contents.map(it => it.speaker)) yield new ActorSpeaker(HashMap(),i.side)
  val dialogWindow = new BufferedDialogWindow(assetLoader.defaultFont)
  dialogWindow.push("This is something I do not like to talk about\nThis is something I do not like to talk about")

  override def update(deltaTime: Float): Unit = {
    dialogState.update()
    dialogWindow.update()
    speakerSprites.foreach(it => it.update())
  }

  override def render(deltaTime: Float, percentage: Float): Unit = {
    dialogWindow.render(spriteBatch)
    speakerSprites.foreach(it => it.render(spriteBatch))
  }
}
