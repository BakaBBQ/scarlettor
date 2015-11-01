package org.ninesyllables.scarlettor.scenes

import com.badlogic.gdx.graphics.{OrthographicCamera, GL20}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.{Game, Gdx, Screen}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.ninesyllables.scarlettor.AssetLoader
import org.ninesyllables.scarlettor.control.Scene
import org.ninesyllables.scarlettor.effects.InterpolatedEffect

/**
 * Created by LBQ on 2015/9/15.
 *
 * as its name suggests, this is the general wrapper around the title screen
 */
class TitleScreen(game : Game, assetLoader: AssetLoader) extends Scene with Screen{
  var timer = 0
  val orthoCam = new OrthographicCamera(960,720)
  orthoCam.translate(480,320)
  orthoCam.zoom = 0.5f
  orthoCam.update()

  val intEff = new InterpolatedEffect(new Vector2(0,0.5f), new Vector2(60, 1.0f))

  val spriteBatch = new SpriteBatch()
  acts += new ActTitleBackground(spriteBatch, assetLoader)
  acts += new ActTitleMain(spriteBatch, assetLoader, orthoCam, () => game.setScreen(new MainScreen(game,assetLoader)))
  override def update(deltaTime: Float): Unit = {
    orthoCam.zoom = intEff(timer)
    orthoCam.update()
    for (a <- acts){
      a.update(deltaTime)
    }
    timer = timer + 1
  }

  override def hide(): Unit = {}

  override def resize(i: Int, i1: Int): Unit = {}

  override def dispose(): Unit = {}

  override def pause(): Unit = {}

  override def render(v: Float): Unit = {
    Gdx.gl.glClearColor(0,0,0,0)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)
    update(v)
    spriteBatch.setProjectionMatrix(orthoCam.combined)
    spriteBatch.begin()
    for (a <- acts){
      a.render(v, 1f)
    }
    spriteBatch.end()
  }

  override def show(): Unit = {}

  override def resume(): Unit = {}
}
