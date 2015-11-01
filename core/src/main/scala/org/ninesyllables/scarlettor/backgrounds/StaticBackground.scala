package org.ninesyllables.scarlettor.backgrounds

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.ninesyllables.scarlettor.effects.InterpolatedEffect
import org.ninesyllables.scarlettor.utils.Vec2

/**
 * Created by LBQ on 2015/10/14.
 */
class StaticBackground(tex : Texture, sp : SpriteBatch) extends Background{
  val transition = new InterpolatedEffect(Vec2(0,0),Vec2(15,1f))
  var timer = 0
  override def render(deltaTime: Float): Unit = {
    sp.begin()
    sp.setColor(1,1,1, transition(timer))
    sp.draw(tex,0,0)
    sp.setColor(1,1,1,1)
    sp.end()
  }

  override def update(deltaTime: Float): Unit = {
    timer = timer + 1
  }
}
