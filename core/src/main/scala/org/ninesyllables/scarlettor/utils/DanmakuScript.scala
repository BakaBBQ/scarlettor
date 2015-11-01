package org.ninesyllables.scarlettor.utils

import org.jruby.parser.StaticScope
import org.jruby.runtime.Block.Type
import org.jruby.runtime.builtin.IRubyObject
import org.jruby.runtime.{Binding, ThreadContext, BlockBody, Block}
import org.ninesyllables.scarlettor.Entity

import scala.collection.mutable.ArrayBuffer

/**
 * Created by LBQ on 2015/9/18.
 */
class DanmakuScript(rawDanmakuScript: RawDanmakuScript) {
  def getInitEntities : Vector[Entity] = {
    val e = rawDanmakuScript.getInitEntities
    val l = e.getLength
    val arrayBuffer = new ArrayBuffer[Entity]()
    for (i <- 0 until l){
      val ie = e.get(i)
      assert(ie.isInstanceOf[Entity])
      arrayBuffer += e.get(i).asInstanceOf[Entity]
    }
    arrayBuffer.toVector
  }
}
