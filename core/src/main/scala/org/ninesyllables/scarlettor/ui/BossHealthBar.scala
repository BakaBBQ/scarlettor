package org.ninesyllables.scarlettor.ui

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import org.ninesyllables.scarlettor.{AccumulativeValue, AssetLoader}

/**
 * Created by LBQ on 2015/9/16.
 *
 * Handles the display of the boss's hp bar, self-explanatory
 */
object BossHealthBar {
  val counter = new AccumulativeValue(0, 30)
  def update(isDrawing : Boolean): Unit = {
    if (isDrawing) counter.accu() else counter.block()
  }

  def drawBossHp(rate : Float, assetLoader : AssetLoader, shapeRenderer: ShapeRenderer): Unit ={
    shapeRenderer.begin(ShapeType.Filled)
    shapeRenderer.setColor(1,0.1f,0.1f,counter.percentage)
    shapeRenderer.rect(10, 400, 300, 10)
    shapeRenderer.end()
  }
}
