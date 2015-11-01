package org.ninesyllables.scarlettor.utils;

import com.badlogic.gdx.utils.Array;
import org.jruby.RubyArray;
import org.ninesyllables.scarlettor.Entity;

/**
 * Created by LBQ on 2015/9/18.
 *
 * Those extended by ruby
 */
public interface RawDanmakuScript {
    public RubyArray getInitEntities();
    public RubyArray getNewEntities(int gtimer);
}
