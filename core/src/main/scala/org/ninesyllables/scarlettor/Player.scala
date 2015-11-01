package org.ninesyllables.scarlettor

import java.util.UUID

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.events._
import org.ninesyllables.scarlettor.temps.ParticleWarehouse
import org.ninesyllables.scarlettor.utils.{RichVector2, Mathy, PolarVector2, RSpriteBatch}

import scala.collection.immutable.HashMap
import scala.collection.mutable.ArrayBuffer

/**
 * Created by LBQ on 2015/9/9.
 *
 * The Player Definition, bear in mind that players are designed to be ONLY ONE
 */
case class Player(pos: Vector2, radius: Float, timer: Int, power: Int, invincibleTimer: Int, focusTimer: Int, bcd: Int, bombTimer: Int, deadTimer: Int, horzVel: Int, score: Int, lives: Int = 2, UUID: UUID = java.util.UUID.randomUUID()) extends Entity with Posed with Timed {
  // this looks awfully like a state monad
  val DEAD_TIMER_MAX = 60
  override def update(deltaTime: Float, entities: Vector[Entity]): (Player, Vector[Entity], Vector[Event]) = {
    val movement = if (isCanMove) InputWrapper.mapDirections() else (0, 0)
    val slowMode = Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)
    val speedMultiplier = if (slowMode) Settings.PLAYER_SPEED._1 else Settings.PLAYER_SPEED._2
    val offsets = Mathy.seqToVector2(List(movement._1, movement._2) map (_ * speedMultiplier.toFloat))
    val pl = this.copy(timer = timer + 1, pos = pos add offsets, focusTimer = (if (slowMode) Mathy.inBound(0, focusTimer + 1, 20) else Mathy.inBound(0, focusTimer - 1, 20)).toInt)
    val plAndEv = pl updateHorzVel movement updateDeathTimer()
    val plAfter = plAndEv._1
    val evAfter = plAndEv._2
    val plABomb = if (isCanBomb && Gdx.input.isKeyPressed(Keys.X)) plAfter.doBomb() else plAfter
    val eventsBombing = if (isBombing) {
      entities filter { it => it.isInstanceOf[Bullet] } map { it => it.asInstanceOf[Bullet] } filter { it: Bullet => it.distanceTo(this) < 100 } map { it => it.UUID } map {
        BulletDissipateEvent(_)
      }
    } else Vector()
    val particles = ParticleWarehouse.playerParticles
    for (p <- getOptionPos.indices) {
      val pt = particles(p)
      pt.update(Gdx.graphics.getDeltaTime)
    }
    val eDissipate = if (deadTimer == DEAD_TIMER_MAX - 1) Vector(BulletAllDissipateEvent()) else Vector()
    (plABomb.mutBombTimer(), getPlayerBullets, evAfter ++ eventsBombing ++ eDissipate)
  }

  def isCanMove: Boolean = !isDead

  implicit private def convertToRichVector2(vector2: Vector2) : RichVector2 = new RichVector2(vector2)
  def getPlayerBullets: Vector[PlayerBullet] = {
    if (Gdx.input.isKeyPressed(Keys.Z) && timer % 5 == 0 && isCanShoot) {
      getOptionPos.map(it => pos + it).flatMap(it => Vector(
        PlayerBullet(it + RichVector2(7,0), new Vector2(0, 16), 0, SanaeNormalBullet()),
        PlayerBullet(it + RichVector2(-7,0), new Vector2(0, 16), 0, SanaeNormalBullet())
      ))
    } else {
      Vector()
    }
  }

  def doBomb(): Player = {
    copy(bombTimer = 300, invincibleTimer = 360)
  }

  lazy val collisionType = CircleType(radius)


  def isBombing: Boolean = bombTimer > 0

  def renderBomb(spriteBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {
    // destructively renders the bomb animation to the spritebatch
    val starRot = { t: Int => Math.pow(300f - t, 1.4f) }.andThen { it => it.toFloat }
    val starSize = { t: Int => {
      val rt = 300 - t
      if (rt < 60) rt * 1 / 60f
      else if (rt > 240) t * 1 / 60f
      else 1.0f
    }
    }
    val t = assetLoader.sanaeBombStar
    spriteBatch.omnidraw(new TextureRegion(t), pos.x, pos.y, rot = starRot(timer), zx = starSize(timer), zy = starSize(timer), atCenter = true)
  }

  override def listenEvents(deltaTime: Float, events: Vector[Event]): (Player, Vector[Entity]) = {
    var flagCollided = false
    var powerAccumulated = 0
    var scoreAccumulated = 0
    for (i <- events) {
      i match {
        case PlayerCollisionEvent(c, p) => flagCollided = true
        case PlayerItemCollectEvent(this.UUID, amount, PowerAmountType()) => powerAccumulated += amount
        case PlayerItemCollectEvent(this.UUID, amount, PointAmountType()) => scoreAccumulated += amount
        case _ => null
      }
    }
    if (isDead || isInvincible)
      flagCollided = false
    if (flagCollided)
      powerAccumulated -= powerDropAmount()
    val newPlayer = if (flagCollided) goDead else this
    val pAccu = if (flagCollided)
      onDeathPowerEntities()
    else
      Vector()

    (newPlayer copy (power = newPlayer.power + powerAccumulated, score = newPlayer.score + scoreAccumulated), Vector() ++ pAccu)
  }

  def powerDropAmount() : Int = {
    List(power,255).min
  }

  def onDeathPowerEntities() : Vector[Item] = {
    val amount = powerDropAmount()
    val bigP = amount / 100
    val smallP = (amount % 100) / 5
    val totalCount = bigP + smallP
    val angleSlice = 80 / (totalCount - 1)
    val angles = for (i <- 0 until totalCount) yield i*angleSlice + 50
    val vectors = angles map {new PolarVector2(15,_)}
    val ungroupedBigP = for (i <- 0 until bigP) yield Item(pos, vectors(i),0,BigPowerItemType(),false,new HashMap[String,Boolean])
    val ungroupedSmallP = for (i <- 0 until smallP) yield Item(pos, vectors(bigP + i),0,PowerItemType(),false,new HashMap[String,Boolean])
    (ungroupedSmallP ++ ungroupedBigP).toVector
  }

  def goDead: Player = {
    copy(deadTimer = DEAD_TIMER_MAX)
  }

  def updateDeathTimer(): (Player, Vector[Event]) = {
    /*
    Let's see the logic here:
      our player has one attribute : deathTimer
      associated with two other attributes : lives, invincibleTimer

      update = () ->
        if playerDead
          deathTimer --
        if deathTimer == 1
          lives --
          invincibleTimer = 80
          deathTimer = 0
          if lives == -1
            raiseEvent "gameover"
        if invincible
          invincibleTimer --
     */
    var newDeathTimer = deadTimer
    var newLives = lives
    var newInvincibleTimer = invincibleTimer
    var events = ArrayBuffer[Event]()
    if (isDead) {
      newDeathTimer -= 1
      if (newDeathTimer == 1) {
        newLives -= 1
        newInvincibleTimer = 180
        newDeathTimer = 0
        if (newLives == -1)
          events += GameoverEvent()
      }
    }
    if (isInvincible)
      newInvincibleTimer -= 1
    val newPlayer = copy(deadTimer = newDeathTimer, lives = newLives, invincibleTimer = newInvincibleTimer)
    val e = events.toVector
    return (newPlayer, e)
  }

  override def isDead: Boolean = deadTimer > 0



  def updateHorzVel(mv: (Int, Int)): Player = {
    val dx = mv._1
    val fn = dx match {
      case 0 => Mathy.decAbs
      case 1 => Mathy.inc
      case -1 => Mathy.dec
    }
    copy(horzVel = Mathy.inBoundI(-7, fn(horzVel), 7))
  }

  def isCanBomb: Boolean = {
    val p = power
    p >= 100 && bcd == 0 && bombTimer <= 0 && !isDead
  }

  def isCanShoot : Boolean = !isDead

  def isInvincible: Boolean = invincibleTimer > 0

  def isVisible: Boolean = {
    if (isDead)
      false
    else if (isInvincible) {
      val t = timer / 20
      t % 2 == 0
    } else
      true
  }

  def mutX(fn: Float => Float): Player = {
    val oriX = pos.x
    val newX = Mathy.inBound(Settings.STAGE_LEFT_BOUND, fn(oriX), Settings.STAGE_RIGHT_BOUND)
    val newVec = new Vector2(pos)
    newVec.x = newX
    copy(pos = newVec)
  }

  def mutY(fn: Float => Float): Player = {
    val oriY = pos.y
    val newY = Mathy.inBound(Settings.STAGE_LOWER_BOUND, fn(oriY), Settings.STAGE_UPPER_BOUND)
    val newVec = new Vector2(pos)
    newVec.y = newY
    copy(pos = newVec)
  }

  def getOptionPos: Vector[Vector2] = {
    if (power < 100)
      Vector()
    else {
      val dis = 60 - (30 * focusTimer / 20)
      val optionCount = power / 100
      val divAngle = 360 / optionCount
      val angles = optionCount match {
        case 1 => Vector(270)
        case 2 => Vector(315, 225)
        case 3 => Vector(315, 225, 90)
        case 4 => Vector(315, 225, 0, 180)
      }
      angles map {
        new PolarVector2(dis, _)
      }
    }
  }

  def mutBombTimer(): Player = {
    if (isBombing) copy(bombTimer = bombTimer - 1) else this
  }

  override def render(spriteBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {
    val t = assetLoader.playerTex
    val bounds = playerTextureBounds
    val flipped = isPlayerTextureFlipped
    val exactRegion = new TextureRegion(t, bounds._1, bounds._2, 48, 72)
    exactRegion.flip(flipped, false)
    if (isVisible) {
      spriteBatch.draw(exactRegion, pos.x - exactRegion.getRegionWidth / 2, pos.y - exactRegion.getRegionHeight / 2)
      drawOptions(spriteBatch, assetLoader)
      drawFocusEffect(spriteBatch, assetLoader)
    }
    if (isBombing)
      renderBomb(spriteBatch, assetLoader)
  }

  def drawOptions(spriteBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {
    val optionPos = getOptionPos
    val particles = ParticleWarehouse.playerParticles
    for (p <- optionPos.indices) {
      val ppos = optionPos(p)
      val pt = particles(p)
      pt.setPosition(pos.x + ppos.x, pos.y + ppos.y)
      pt.draw(spriteBatch)
    }
  }

  implicit def convertToRichSpriteBatch(v: SpriteBatch): RSpriteBatch = new RSpriteBatch(v)

  def drawFocusEffect(spriteBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {
    val t = assetLoader.focusTex
    val rot = timer % 360
    val op = focusTimer / 40f
    spriteBatch.setColor(1, 1, 1, op)
    spriteBatch.drawAtCenterWithRotation(t, pos.x, pos.y, rot)
    spriteBatch.drawAtCenterWithRotation(t, pos.x, pos.y, 360 - rot)
    spriteBatch.setColor(1, 1, 1, 1)
  }

  def playerTextureBounds: (Int, Int) = {
    val absVel = Math.abs(horzVel)
    val rawBounds = horzVel.compare(0) match {
      case 1 => if (horzVel == 7) (horzVel + ((timer / 5) % 15) / 3 - 4, 1) else (horzVel, 1)
      case 0 => (((timer / 2) % 24) / 3, 0)
      case -1 => if (absVel == 7) (absVel + (((timer / 5) % 15) / 4) - 4, 1) else (absVel, 1)
    }
    (48 * rawBounds._1, 72 * rawBounds._2)
  }

  def isPlayerTextureFlipped: Boolean = {
    horzVel > 0
  }

  override def renderUi(hubBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {

  }
}
