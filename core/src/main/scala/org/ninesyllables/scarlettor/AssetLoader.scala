package org.ninesyllables.scarlettor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.{AssetDescriptor, AssetManager}
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.utils.Array
import org.ninesyllables.scarlettor.utils.ClassCheater
import scala.collection.JavaConversions._
import scala.collection.immutable

/**
 * Created by LBQ on 2015/9/9.
 *
 * wraps the asset manager, easily retrieve textures
 */
class AssetLoader(val assetManager: AssetManager){
  lazy val defaultFont = new BitmapFont()
  def load(): Unit ={
    val textures = List(
      "lbq-flavored-bullets.png",
      "sanae-sprite.png",
      "big-bullets.png",
      "etama2.png",
      "front.png",
      "sanae-shots.png",
      "enemy.png",
      "sanae-bomb.png",
      "reimu-shot.png",
      "all-black.png",
      "hexagram.png",
      "title/copyright.png"
    )
    textures.foreach{assetManager.load(_, classOf[Texture])}

    val titleTextures =
        (for (i <- 1 until 7) yield "title/logo-frame-" + i + ".png") ++
        (for (i <- 1 until 7) yield "title/choice1-frame-" + i + ".png") ++
        (for (i <- 1 until 7) yield "title/choice2-frame-" + i + ".png") ++
        (for (i <- 1 until 7) yield "title/choice3-frame-" + i + ".png")

    titleTextures.foreach{assetManager.load(_, classOf[Texture])}

    val infoTextures =
      for (i <- 1 to 3) yield "title/info%d.png" format i

    infoTextures.foreach{assetManager.load(_, classOf[Texture])}

    val particles = List(
      "magical-flame.pt",
      "sanae-option.pt",
      "title-particle.pt",
      "maple-blue.pt",
      "sanae-hit.pt"
    )

    particles.foreach{assetManager.load(_, classOf[ParticleEffect])}


    val bitmapfonts = List(
      "pron-18.fnt",
      "adobe-arabic-40.fnt",
      "adobe-arabic-60.fnt",
      "adobe-arabic-96.fnt"
    )
    bitmapfonts.foreach{assetManager.load(_, classOf[BitmapFont])}
    assetManager.finishLoading()
  }

  def update() = assetManager.update()

  def finishLoading() : Unit = {
    assetManager.finishLoading()
  }

  def getTexture(name : String) : Texture = {
    assetManager.get(name, classOf[Texture])
  }

  def infoTexs : IndexedSeq[Texture] = (for (i <- 1 to 3) yield "title/info%d.png" format i) map getTexture

  def sanaeShots : Texture = assetManager.get("sanae-shots.png", classOf[Texture])
  def playerTex : Texture = assetManager.get("sanae-sprite.png", ClassCheater.getTextureClass)
  def smallBulletSheet : Texture = assetManager.get("lbq-flavored-bullets.png", ClassCheater.getTextureClass)
  def bigBulletSheet : Texture = assetManager.get("big-bullets.png",ClassCheater.getTextureClass)
  def itemAndEffectSheet : Texture = assetManager.get("etama2.png", classOf[Texture])
  def focusTex : TextureRegion = new TextureRegion(itemAndEffectSheet, 0, 24, 96, 96)
  def frontTex : Texture = assetManager.get("front.png", classOf[Texture])
  def sanaeOptionParticle : ParticleEffect = assetManager.get("sanae-option.pt", classOf[ParticleEffect])
  def optionEffects : Vector[ParticleEffect] = ???
  def mapleBlueParticle : ParticleEffect = assetManager.get("maple-blue.pt", classOf[ParticleEffect])
  def sanaeBombStar : Texture = getTexture("sanae-bomb.png")
  def reimuShot : Texture = getTexture("reimu-shot.png")
  def lList[A](ori : IndexedSeq[A]) : Array[A] = {
    val newArray = new Array[A]()
    ori.foreach(newArray.add)
    newArray
  }
  def copyright : Texture = getTexture("title/copyright.png")
  lazy val allBlack : Texture = getTexture("all-black.png")

  def sanaeHitParticle : ParticleEffect = assetManager.get("sanae-hit.pt", classOf[ParticleEffect])

  def titleParticle : ParticleEffect = assetManager.get("title-particle.pt", classOf[ParticleEffect])
  lazy val logoFrameAnim = {
    val texs = (for {i <- 1 until 7} yield new TextureRegion(assetManager.get("title/logo-frame-"+i+".png", classOf[Texture])))
    new Animation(0.15f,lList(texs), PlayMode.LOOP_PINGPONG)
  }

  lazy val adobeArabicFontGroup = for(i <- List(40,60,96)) yield assetManager.get("adobe-arabic-" + i + ".fnt", classOf[BitmapFont])
  lazy val scoreFont : BitmapFont = assetManager.get("pron-18.fnt", classOf[BitmapFont])

  lazy val choiceAnims: immutable.IndexedSeq[Animation] = {
    val animStack = for {i <- 1 until 4} yield (for {j <- 1 until 7} yield new TextureRegion((assetManager.get("title/choice" + i + "-frame-" + j + ".png", classOf[Texture]))))
    val tanimStack = animStack map lList
    tanimStack map {it => new Animation(0.15f, it, PlayMode.LOOP_PINGPONG)}
  }
  lazy val hexagram = getTexture("hexagram.png")

  def getBigFairyTexture(etama : Texture, index : Int) : TextureRegion = {
    index match {
      case 8 => new TextureRegion(etama, 576, 480, 96, 96)
      case 9 => new TextureRegion(etama, 672, 480, 96, 96)
      case 10 => new TextureRegion(etama, 576, 384, 96, 96)
      case 11 => new TextureRegion(etama, 672, 384, 96, 96)
      case _ => new TextureRegion(etama, 96*index, 576, 96, 96)
    }
  }

  lazy val allEnemyTextures = {
    val tex = assetManager.get("enemy.png", classOf[Texture])
    for (i <- 0 until 11)
      yield for (j <- 0 until 5)
        yield j match {
          case 4 => new TextureRegion(tex, 48*i, 48*j+384,48,48)
          case _ => getBigFairyTexture(tex, i)
        }
  }
}
