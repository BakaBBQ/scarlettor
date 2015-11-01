package org.ninesyllables.scarlettor.dialogs

import org.ninesyllables.scarlettor.backgrounds.Background

/**
 * Created by LBQ on 2015/10/14.
 */
object TestDialogScript extends DialogScript{
  val backgroundName = "background.png"

  val contents: List[DialogContent] = List(
    DialogContent(KochiyaSanaeSpeaker(), HappyState(), "各位好")
  )
}
