package org.ninesyllables.scarlettor.scenes

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.ninesyllables.scarlettor.{AssetLoader, DialogState}
import org.ninesyllables.scarlettor.control.Scene
import org.ninesyllables.scarlettor.dialogs.{HappyState, KochiyaSanaeSpeaker, DialogContent, DialogScript}

/**
 * Created by LBQ on 2015/10/15.
 */
class TestActDialogScreen(assetLoader: AssetLoader) extends Scene with Screen{
  val dialogScript = new DialogScript {
    override def backgroundName: String = "test.png"

    override def contents: List[DialogContent] = List(
      DialogContent(KochiyaSanaeSpeaker(), HappyState(), "hello, world!")
    )
  }

  val spriteBatch = new SpriteBatch()
  acts += new ActDialog(spriteBatch, new DialogState(dialogScript), assetLoader)

  override def update(deltaTime: Float): Unit = {
    for (i <- acts) {

      i.update(deltaTime)
    }
  }

  override def hide(): Unit = {

  }

  override def resize(i: Int, i1: Int): Unit = {

  }

  override def dispose(): Unit = {

  }

  override def pause(): Unit = {

  }

  override def render(v: Float): Unit = {
    Gdx.gl.glClearColor(0,0,0,0)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)
    update(v)
    spriteBatch.begin()
    for (i <- acts) {
      i.render(v,0)
    }
    spriteBatch.end()
  }

  override def show(): Unit = {

  }

  override def resume(): Unit = {

  }
}
