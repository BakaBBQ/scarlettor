package org.ninesyllables.scarlettor.scenes

import com.badlogic.gdx.graphics.g2d.{ParticleEffectPool, SpriteBatch}
import org.ninesyllables.scarlettor.AssetLoader
import org.ninesyllables.scarlettor.control.Act
import org.ninesyllables.scarlettor.utils.RSpriteBatch

/**
 * Created by LBQ on 2015/9/15.
 *
 * the background particle effects
 */
class ActTitleBackground(spriteBatch: SpriteBatch, assetLoader: AssetLoader) extends Act{
  val particleEffectPool = new ParticleEffectPool(assetLoader.titleParticle, 1, 1)
  val peffect: ParticleEffectPool#PooledEffect = particleEffectPool.obtain()
  override def update(deltaTime: Float): Unit = {

  }
  implicit def convertToRichSpriteBatch(v: SpriteBatch): RSpriteBatch = new RSpriteBatch(v)
  override def render(deltaTime: Float, i : Float): Unit = {
    spriteBatch.setOpacity(i)
    peffect.draw(spriteBatch, deltaTime)
    spriteBatch.setOpacity(1f)
  }
}
