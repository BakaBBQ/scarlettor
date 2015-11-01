package org.ninesyllables.scarlettor.dialogs

import org.ninesyllables.scarlettor.backgrounds.Background
import org.ninesyllables.scarlettor.scenes.{LeftSide, Side}

/**
 * Created by LBQ on 2015/10/14.
 */
abstract class EmotionState
case class HappyState() extends EmotionState
case class CalmState() extends EmotionState
case class SurpriseState() extends EmotionState
case class DefeatState() extends EmotionState

abstract class DialogSpeaker {
  val name : String
  val englishName : String
  val side : Side
}

case class KochiyaSanaeSpeaker() extends DialogSpeaker {
  val name = "东风谷早苗"
  val englishName = "Kochiya Sanae"
  val side = LeftSide()
}

case class DialogContent(speaker : DialogSpeaker, speakerState : EmotionState, contents : String)
trait DialogScript {
  def backgroundName : String
  def contents : List[DialogContent]
}
