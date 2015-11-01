package org.ninesyllables.scarlettor.scenes

import java.io.{File, FilenameFilter}

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{FPSLogger, GL20}
import com.badlogic.gdx.{Game, Gdx, Screen}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.googlecode.scalascriptengine.ScalaScriptEngine
import org.ninesyllables.scarlettor.effects.InterpolatedEffect
import org.ninesyllables.scarlettor.events.Event
import org.ninesyllables.scarlettor.io.FileList
import org.ninesyllables.scarlettor.temps.TestStrategy
import org.ninesyllables.scarlettor.utils._
import org.ninesyllables.scarlettor._
import org.ninesyllables.scarlettor.control.{Factory, Scene}

import scala.collection.immutable.HashMap
import scalaz.Maybe.Just

/**
 * Created by LBQ on 2015/9/11.
 *
 * The main game screen, everything important happens here
 */
class MainScreen(game : Game, assetLoader: AssetLoader) extends Scene with Screen {
  ScriptLoader.load()

  val mainBatch = new SpriteBatch()
  val uiBatch = new SpriteBatch()
  val shapeRenderer = new ShapeRenderer()
  val fpsLogger = new FPSLogger
  val ACT_BATTLE = 0
  val ACT_PAUSE = 1
  val ACT_GAMEOVER = 2
  val actGroups = new TransitionedGroup(3,ACT_BATTLE,20,0)

  val toTitleFadeEff = new InterpolatedEffect(new Vector2(0,0f), new Vector2(60,1f))

  val goBackToTitle = new ActivatedTimer()
  val retry = new ActivatedTimer()

  val defaultEntityList = {
    val player = Factory.player
    val bulletShooter = NormalEnemy(new Vector2(400, 400), 0, TestStrategy, {i : Int => new Vector2(1,0)})
    val item = Item(new Vector2(400, 400), new Vector2(0, 1), 0, PointItemType(), false, new HashMap[String, Boolean])
    val bullet = ReifiedBullet(new Vector2(300, 300), new Vector2(0,2), 0, CircleType(10), SmallBulletType(0, SmallBulletTypes.STAR),0)
    val boss = new BossShooter(new Vector2(400,400), 0, List(), BossProperties("Kaguya"))
    Vector(player, bulletShooter)
  }

  val gameState = new GameState(Vector(), defaultEntityList)

  acts += new ActBattle(mainBatch, uiBatch, shapeRenderer, gameState, assetLoader)
  acts += new ActPause(uiBatch, assetLoader, {
    case 0 => actGroups.select(ACT_BATTLE)
    case 1 => retry.activate()
    case 2 => goBackToTitle.activate()
  })
  acts += new ActGameover(uiBatch, assetLoader)

  override def hide(): Unit = {

  }

  override def resize(i: Int, i1: Int): Unit = {

  }

  override def dispose(): Unit = {

  }

  override def pause(): Unit = {

  }

  override def update(v : Float): Unit = {
    retry.update()
    goBackToTitle.update()

    goBackToTitle.map{it : Int =>
      if (it > 60)
        game.setScreen(new TitleScreen(game,assetLoader))
    }
    retry.map{it : Int =>
      if (it > 60)
        game.setScreen(new MainScreen(game, assetLoader))
    }
    if (retry.activated || goBackToTitle.activated)
      return
    actGroups.update()
    actGroups.currentChoice map (it => acts(it).update(v))
    fpsLogger.log()
    if (actGroups.currentChoice == Just(ACT_BATTLE) && GlobalInputTriggerDetector.isTrigger(Keys.ESCAPE)){
      actGroups.select(ACT_PAUSE)
    }

    if (actGroups.currentChoice == Just(ACT_PAUSE) && GlobalInputTriggerDetector.isTrigger(Keys.ESCAPE)){
      actGroups.select(ACT_BATTLE)
    }

    if (gameState.gameovered){
      actGroups.select(ACT_GAMEOVER)
    }
    GlobalInputTriggerDetector.update()
  }
  override def render(v: Float): Unit = {
    Gdx.gl.glClearColor(0,0,0,0)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)
    update(v)

    for(i <- acts.indices){
      acts(i).render(v,actGroups.percentage(i))
    }
    (goBackToTitle |> retry).map{it =>
      val op = toTitleFadeEff(it)
      mainBatch.begin()
      mainBatch.setColor(1,1,1,op)
      mainBatch.draw(assetLoader.allBlack,0,0)
      mainBatch.setColor(1,1,1,1)
      mainBatch.end()
    }
  }

  override def show(): Unit = {

  }

  override def resume(): Unit = {

  }
}
