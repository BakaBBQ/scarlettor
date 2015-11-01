package org.ninesyllables.scarlettor

import java.util.UUID

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import org.ninesyllables.scarlettor.events.{PlayerCollisionEvent, Event}
import org.ninesyllables.scarlettor.utils.{RSpriteBatch, BulletGraphicsType, Line, Mathy}

/**
 * Created by LBQ on 2015/9/10.
 *
 * every bullet except round bullet
 */
@deprecated
case class PolygonBullet(vectors : Vector[Vector2], pos : Vector2, vel : Vector2, rotation : Float, bulletGraphicsType: BulletGraphicsType, timer : Int, UUID : UUID = java.util.UUID.randomUUID(), dissipate : Int = 0) extends Bullet{
  val radius = calcRadius()

  def calcRadius() : Float = {
    (vectors map {Mathy.distance(new Vector2(0,0), _)}).max
  }

  def foldVectorsIntoLines : Vector[Line] = {
    val grouped = vectors.grouped(2).toVector
    val l = Line(new Vector2(0,1), new Vector2(0,1))
    val x = grouped map {it => Line(it.head, it.last)}
    x
  }

  override def rotate(angle: Float): Bullet = {
    copy(vectors = vectors map {it => it.rotate(angle)}, rotation = rotation + angle)
  }

  override def update(deltaTime: Float, entities: Vector[Entity]): (PolygonBullet, Vector[Entity], Vector[Event]) = {
    val player = entities.head.asInstanceOf[Player]
    val collided = detectCollide(player)
    val events = if (collided) Vector(new PlayerCollisionEvent(this, player.UUID)) else Vector()
    (this.copy(vel = updateNewPos()), Vector(), events)
  }

  override def listenEvents(deltaTime: Float, events: Vector[Event]): (PolygonBullet, Vector[Entity]) = {
    (this, Vector())
  }

  def broadPhaseDetectCheck(p : Player) : Boolean = {
    val totalRadius = p.radius + radius
    val broadPhaseD = distanceTo(p)
    broadPhaseD <= totalRadius
  }

  def narrowPhaseDetectCheck(p : Player) : Boolean = {
    foldVectorsIntoLines exists {it : Line => it.isCollideWithCircle(p.pos, p.radius)}
  }


  override def detectCollide(p: Player): Boolean = {
    // first execute the broad check, then try the narrow one
    lazy val narrowPhase = narrowPhaseDetectCheck(p)
    broadPhaseDetectCheck(p) && narrowPhase
  }

  override def renderUi(hubBatch: SpriteBatch, assetLoader: AssetLoader): Unit = ()

  implicit def convertToRichSpriteBatch(v : SpriteBatch) : RSpriteBatch = new RSpriteBatch(v)
  override def render(spriteBatch: SpriteBatch, assetLoader: AssetLoader): Unit = {
    val tex = getBulletTexture(bulletGraphicsType, assetLoader)
    val opacity = opacityFormula(timer)
    val zxy = zxyFormula(timer)
    spriteBatch.setOpacity(opacity * getDissipateOpacity)
    spriteBatch.omnidraw(tex, pos.x, pos.y, atCenter = true, zx = getDissipateZxy, zy = getDissipateZxy)
    spriteBatch.setOpacity(1f)
  }

  override def rotateVel(angle: Float): Bullet = {
    copy(vel = new Vector2(vel).rotate(angle))
  }
  override def randUUID: Bullet = copy(UUID = java.util.UUID.randomUUID())
}
