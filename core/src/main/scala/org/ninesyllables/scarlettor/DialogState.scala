package org.ninesyllables.scarlettor

import org.ninesyllables.scarlettor.dialogs.DialogScript

/**
 * Created by LBQ on 2015/10/14.
 */
class DialogState(val dialogScript: DialogScript) {
  var timer = 0
  def update() : Unit = {
    timer += 1
  }
}
