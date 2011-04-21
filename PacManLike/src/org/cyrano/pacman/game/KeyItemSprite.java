package org.cyrano.pacman.game;

import java.awt.event.ActionEvent;
import java.io.IOException;

import org.cyrano.pacman.base.BaseBean;
import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.util.base.ImageCache;
import org.cyrano.util.game.Timer;

public class KeyItemSprite extends BaseSprite {

  // --------------------------------------------------------------------------------

  public KeyItemSprite() {

    //super(grdX, grdY, 0, null);

  }

  // --------------------------------------------------------------------------------

  protected void loadImgs() throws IOException {
    bimgList.add(ImageCache.getInstance().getImage("key.png"));
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void calcLook() {
  }

  @Override
  protected void calcNext() {
  }

  @Override
  public void init(LevelExec levelExec, BaseBean baseBean, String[] parmArray) {
    speed = Integer.parseInt(parmArray[0]);
    init(baseBean.getX(), baseBean.getY(), speed, levelExec);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void stepOn(BaseSprite wootwoot, Timer timer) {
    if (wootwoot instanceof PacManSprite) {
      actionListenerProxy.fireActionEvent(new ActionEvent(this, 0, "die"));
      ((PacManSprite) wootwoot).yellowKey++;
    }

    //  wootwoot.stepOn(this); // XXX: This will cause problem if two pacmans!!!
  }

}
