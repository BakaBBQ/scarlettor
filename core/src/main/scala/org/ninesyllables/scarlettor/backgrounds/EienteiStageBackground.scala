package org.ninesyllables.scarlettor.backgrounds

import com.badlogic.gdx.graphics.{VertexAttributes, Texture, Color, PerspectiveCamera}
import com.badlogic.gdx.graphics.g3d.attributes.{TextureAttribute, ColorAttribute}
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.graphics.g3d.{ModelBatch, Material, ModelInstance, Environment}
import com.badlogic.gdx.graphics.g3d.environment.PointLight
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Array
import org.ninesyllables.scarlettor.Settings
import scala.collection.JavaConversions._
/**
 * Created by LBQ on 2015/9/10.
 *
 * Actual background object for stage 3c
 */
class EienteiStageBackground extends Background{
  val modelBatch = new ModelBatch()
  val pointLights = new Array[PointLight]()
  val sceneInstances = new Array[ModelInstance]
  val cameraInitPos = new Vector3(10,15,0)
  var timer = 0
  val speedRate = 0.1f
  val luminousity = 0.01f
  val perspectiveCamera = new PerspectiveCamera(67, Settings.GAME_WIDTH, Settings.GAME_HEIGHT)
  perspectiveCamera.position.set(cameraInitPos)
  perspectiveCamera.near = 1
  perspectiveCamera.far = 300
  perspectiveCamera.rotate(-90,0,0,1)
  perspectiveCamera.lookAt(1,15,10)
  perspectiveCamera.update()


  val modelBuilder = new ModelBuilder
  val environment = new Environment()
  val environmentLight = 0.0f
  val eL = environmentLight
  environment.set(new ColorAttribute(ColorAttribute.AmbientLight, eL, eL, eL, 100))


  val modelSide = modelBuilder.createBox(30,1,30,
    new Material(new TextureAttribute(TextureAttribute.Diffuse, new Texture("side.png"))),
    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates)
  val modelFloor = modelBuilder.createBox(1,30,30,
    new Material(new TextureAttribute(TextureAttribute.Diffuse, new Texture("floor.png"))),
    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates)

  val pos = List(new Vector3(8,15,15), new Vector3(8,15,15))
  for (p <- pos){
    pointLights.add(new PointLight().set(Color.BLUE, p, luminousity))
    pointLights.add(new PointLight().set(Color.BLUE, new Vector3(p).add(0,0,30), luminousity))
    pointLights.add(new PointLight().set(Color.BLUE, new Vector3(p).add(0,0,60), luminousity))
  }
  for (pl : PointLight <- pointLights){
    environment.add(pl)
  }

  for (i <- 0 until 10){
    val newOne = new ModelInstance(modelSide)
    //(z,x,y)
    newOne.transform.setToTranslation(0,0,i*30)
    val newOne2 = new ModelInstance(modelFloor)
    newOne2.transform.setToTranslation(-15,15,i*30)
    val newOne3 = new ModelInstance(modelSide)
    newOne3.transform.setToTranslation(0,30,i*30)
    List(newOne, newOne2, newOne3).foreach(sceneInstances.add)
  }
  override def render(deltaTime : Float): Unit = {

    perspectiveCamera.position.set(new Vector3(cameraInitPos.x, cameraInitPos.y, cameraInitPos.z + (timer * speedRate) % 30))
    perspectiveCamera.update()
    pointLights.foreach{environment.add(_)}
    modelBatch.begin(perspectiveCamera)
    for(i : ModelInstance<- sceneInstances){
      modelBatch.render(i, environment)
    }
    modelBatch.end()
    for(pl : PointLight <- pointLights){
      environment.remove(pl)
    }

  }

  override def update(deltaTime : Float): Unit = {
    timer = timer + 1
  }
}
