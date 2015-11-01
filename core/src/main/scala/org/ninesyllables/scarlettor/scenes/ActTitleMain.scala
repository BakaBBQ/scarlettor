package org.ninesyllables.scarlettor.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.{OrthographicCamera, Camera}
import com.badlogic.gdx.graphics.g2d.{TextureRegion, Animation, SpriteBatch}
import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.AssetLoader
import org.ninesyllables.scarlettor.control.Act
import org.ninesyllables.scarlettor.effects.{InterpolatedEffect, FadeInEffect}
import org.ninesyllables.scarlettor.utils._

import scala.collection.immutable.IndexedSeq
import scalaz.Maybe
import scalaz.Maybe.{Empty, Just}

/**
 * Created by LBQ on 2015/9/15.
 *
 * Act that handles various title screen sprites
 */
class ActTitleMain(spriteBatch: SpriteBatch, assetLoader: AssetLoader, camera: OrthographicCamera, callBack: (() => Unit)) extends Act{
  lazy val inputTriggerDetector = new InputTriggerDetector()
  var realTime = 0f
  var timer = 0
  var choiceGroup = new TransitionedGroup(3,0,60,0)
  val fadeIn = new FadeInEffect(60)
  val zoomInValue = new ActivatedValue(0.2f,1,new InterpolatedEffect(new Vector2(0f,1f), new Vector2(30f,0.2f)))
  val opacityValue = new ActivatedValue(0f,1f, new InterpolatedEffect(new Vector2(0f,0f), new Vector2(30f,1f)))
  var chosen : Maybe[Int] = Empty()
  implicit def convertToRichSpriteBatch(v : SpriteBatch) : RSpriteBatch = new RSpriteBatch(v)
  override def update(deltaTime: Float): Unit = {
    if (zoomInValue.activated){
      camera.zoom = zoomInValue.get()
      camera.update()
    }

    realTime = realTime + deltaTime
    timer = timer + 1

    choiceGroup.update(2)
    if (inputTriggerDetector.isTrigger(Keys.UP)){
      choiceGroup.prev()
    }
    if (inputTriggerDetector.isTrigger(Keys.DOWN)){
      choiceGroup.next()
    }
    if (inputTriggerDetector.isTrigger(Keys.Z)){
      choiceGroup.currentChoice match {
        case Just(0) if timer >= 60 => {
          chosen = Just(0)
          zoomInValue.activate()
          opacityValue.activate()
        }
        case Just(1) => println("hi")
        case Just(2) => {
          chosen = Just(2)
          opacityValue.activate()
        }
        case Empty() => println("no choice selected")
        case _ =>
      }
    }

    if (opacityValue.get() == 1f && chosen.isJust){
      chosen match {
        case Just(0) => callBack()
        case Just(2) => {
          assetLoader.assetManager.dispose()
          System.exit(0)
        }
      }
    }
    inputTriggerDetector.update()
    zoomInValue.update()
    opacityValue.update()
  }

  override def render(deltaTime: Float, i : Float): Unit = {
    val animLogo: Animation = assetLoader.logoFrameAnim
    val f = animLogo.getKeyFrame(realTime)
    val choiceAnims = assetLoader.choiceAnims
    val infoTexs = assetLoader.infoTexs
    val positions = List( (480, 279), (480, 239), (480, 199) )
    val choices: IndexedSeq[(Animation, (Int, Int))] = choiceAnims zip positions
    val copyrightTex = assetLoader.copyright

    val globalMultiplier = 1f
    val blackOpacity = opacityValue.get()

    for ((singleChoice, i) <- choices.view.zipWithIndex){
      val keyFrame = singleChoice._1.getKeyFrame(realTime)
      val infoTex = infoTexs(i)
      val pos = singleChoice._2
      spriteBatch.setColor(1,1,1,(choiceGroup.percentage(i) * 0.8f + 0.2f) * fadeIn(timer) * globalMultiplier)
      spriteBatch.draw(keyFrame, pos._1 - keyFrame.getRegionWidth/2, pos._2 - keyFrame.getRegionHeight/2)
      spriteBatch.setColor(1,1,1,choiceGroup.percentage(i) * fadeIn(timer) * globalMultiplier)
      spriteBatch.drawAtCenter(new TextureRegion(infoTex), 480, 148)
      spriteBatch.setColor(1,1,1,fadeIn(timer) * globalMultiplier)
    }

    spriteBatch.draw(f, 480 - f.getRegionWidth/2, 341)
    spriteBatch.draw(copyrightTex, 480 - copyrightTex.getWidth/2, 71)
    spriteBatch.setColor(1,1,1,blackOpacity)
    spriteBatch.draw(assetLoader.allBlack,0,0)
    spriteBatch.setColor(1,1,1,1)

  }
}
