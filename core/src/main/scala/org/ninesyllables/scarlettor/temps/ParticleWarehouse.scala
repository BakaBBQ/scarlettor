package org.ninesyllables.scarlettor.temps

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool
import org.ninesyllables.scarlettor.AssetLoader


/**
 * Created by LBQ on 2015/9/16.
 *
 * Stateful approach to storing particles
 */
object ParticleWarehouse {
  var playerParticles : List[ParticleEffectPool#PooledEffect] = List()
  var playerBulletParticles : List[ParticleEffectPool#PooledEffect] = List()
  var mapleEffectPool : ParticleEffectPool = null
  var sanaeHitEffectPool : ParticleEffectPool = null
  var playerParticleEffectPool : ParticleEffectPool = null
  def init(assetLoader: AssetLoader): Unit ={
    val ptEffect = assetLoader.sanaeOptionParticle
    val particleEffectPool = new ParticleEffectPool(ptEffect, 4, 4)
    playerParticles = (for(i <- 0 until 4) yield particleEffectPool.obtain()).toList

    playerParticleEffectPool = new ParticleEffectPool(ptEffect, 100, 100)
    playerBulletParticles = (for(i <- 0 until 100) yield obtainBulletEffect).toList

    val mapleEffect = assetLoader.mapleBlueParticle
    mapleEffectPool = new ParticleEffectPool(mapleEffect, 30, 30)

    val sanaeHitEffect = assetLoader.sanaeHitParticle
    sanaeHitEffectPool = new ParticleEffectPool(sanaeHitEffect, 20, 50)
  }

  def obtainBulletEffect : ParticleEffectPool#PooledEffect = playerParticleEffectPool.obtain()
}
