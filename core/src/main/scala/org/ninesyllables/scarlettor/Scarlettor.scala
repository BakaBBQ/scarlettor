package org.ninesyllables.scarlettor

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import org.ninesyllables.scarlettor.scenes.{TestActDialogScreen, TitleScreen, MainScreen}
import org.ninesyllables.scarlettor.temps.ParticleWarehouse

class Scarlettor extends Game {
  val assetManager = new AssetManager()
  val assetLoader = new AssetLoader(assetManager)

  override def create(): Unit = {
    assetLoader.load()
    ParticleWarehouse.init(assetLoader)
    val s = new TitleScreen(this, assetLoader)
    setScreen(s)
  }
}
