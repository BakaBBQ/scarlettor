package org.ninesyllables.scarlettor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys

/**
 * Created by LBQ on 2015/9/9.
 */
object InputWrapper {
  def mapDirections(): (Int, Int) ={
    val leftPressed: Boolean = Gdx.input.isKeyPressed(Keys.LEFT)
    val rightPressed = Gdx.input.isKeyPressed(Keys.RIGHT)
    val upPressed = Gdx.input.isKeyPressed(Keys.UP)
    val downPressed = Gdx.input.isKeyPressed(Keys.DOWN)

    val dir1 = (if (leftPressed) -1 else 0) | (if (rightPressed) 1 else 0) | 0
    val dir2 = (if (downPressed) -1 else 0) | (if (upPressed) 1 else 0) | 0
    (dir1, dir2)
  }
}
