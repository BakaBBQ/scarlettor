package org.ninesyllables.scarlettor

import com.badlogic.gdx.Files
import com.badlogic.gdx.backends.lwjgl._
import com.moandjiezana.toml.Toml

object Main extends App {
  DirUtils.prepare()
  val toml = new Toml().parse(DirUtils.p)
  val res = toml.getTable("window").getList[Long]("resolution")
  val title = toml.getTable("window").getString("title")
  val cfg = new LwjglApplicationConfiguration
  cfg.title = title
  val (w, h) = (res.get(0), res.get(1))
  cfg.width = w.toInt; cfg.height = h.toInt
  cfg.resizable = false
  cfg.forceExit = false
  for(i <- List(16,32,64,128)){
    cfg.addIcon("icon%d.png" format i, Files.FileType.Internal)
  }
  new LwjglApplication(new Scarlettor, cfg)
}
