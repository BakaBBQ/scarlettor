package org.ninesyllables.scarlettor.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.backgrounds.EienteiStageBackground
import org.ninesyllables.scarlettor.effects.InterpolatedEffect
import org.ninesyllables.scarlettor.ui.{BossHealthBar, PlayerAttributes, ScoreDisplay}
import org.ninesyllables.scarlettor.utils.TransitionedGroup
import org.ninesyllables.scarlettor.{Player, AssetLoader, GameState}
import org.ninesyllables.scarlettor.control.Act

/**
 * Created by LBQ on 2015/9/11.
 *
 * The main act..
 */
class ActBattle(spriteBatch: SpriteBatch, uiBatch : SpriteBatch, shapeRenderer: ShapeRenderer, gameState: GameState, assetLoader: AssetLoader) extends Act{
  lazy val background = new EienteiStageBackground
  val fadeInEff = new InterpolatedEffect(new Vector2(0f,1f),new Vector2(60f,0f))
  var timer = 0
  override def update(deltaTime : Float): Unit = {
    background.update(deltaTime)
    gameState.update(deltaTime)
    BossHealthBar.update(gameState.getBoss.isJust)
    timer = timer + 1
  }

  override def render(deltaTime : Float, i : Float): Unit = {
    background.render(deltaTime)
    spriteBatch.begin()
    for (e <- gameState.entities) {
      e.render(spriteBatch, assetLoader)
    }
    spriteBatch.draw(assetLoader.frontTex, 0, 0)
    spriteBatch.setColor(1,1,1,fadeInEff.apply(timer))
    spriteBatch.draw(assetLoader.allBlack,0,0)
    spriteBatch.setColor(1,1,1,1)
    drawDebugInfo()
    spriteBatch.end()

    uiBatch.begin()
    val pl = gameState.entities.head.asInstanceOf[Player]
    ScoreDisplay.drawScore(pl.score, assetLoader, uiBatch)
    PlayerAttributes.drawPlayerAttributes(pl, assetLoader, uiBatch)
    uiBatch.end()

    gameState.getBoss.map(it => BossHealthBar.drawBossHp(1.0f, assetLoader, shapeRenderer))
  }

  def drawDebugInfo(): Unit ={
    val c = gameState.entities.length
    val fps = Gdx.graphics.getFramesPerSecond
    val info = "cnt: %s, fps: %s".format(c,fps)
    assetLoader.defaultFont.draw(spriteBatch, info, 30, 30)
  }
}
