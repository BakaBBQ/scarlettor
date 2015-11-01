package org.ninesyllables.scarlettor.scenes

import java.io.{File, FilenameFilter}

import com.badlogic.gdx.{Gdx, Screen}
import org.ninesyllables.scarlettor.AssetLoader
import org.ninesyllables.scarlettor.control.Scene
import org.ninesyllables.scarlettor.utils.TransitionedGroup

/**
 * Created by LBQ on 2015/9/24.
 *
 * A Scene for script testing
 */
class TestScene(assetLoader: AssetLoader) extends Scene with Screen{
  val num = 100 // TODO: fill out the dummy NUM
  val transitionedGroup = new TransitionedGroup(num,0,60,0)

  override def update(deltaTime: Float): Unit = {

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
    update(v)
  }

  override def show(): Unit = {

  }

  override def resume(): Unit = {

  }
}
