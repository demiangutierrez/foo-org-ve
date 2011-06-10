package org.cyrano.pacman.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.io.IOException;

import org.cyrano.pacman.base.BaseBean;
import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Constants;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.util.game.Timer;

public class KeyDoorItemSprite extends BaseSprite {

  // --------------------------------------------------------------------------------

  public KeyDoorItemSprite() {

    //super(grdX, grdY, 0, null);

  }

  // --------------------------------------------------------------------------------

  protected void loadImgs() throws IOException {
    //    bimgList.add(ImageCache.getInstance().getImage("key.png"));
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
  public boolean canMove(BaseSprite wootwoot, Timer timer) {
    if (!(wootwoot instanceof PacManSprite)) {
      return false; // Only character can open doors
    }

    PacManSprite pacManSprite = (PacManSprite) wootwoot;

    return pacManSprite.yellowKey > 0;
  }

  @Override
  public void stepOn(BaseSprite wootwoot, Timer timer) {

    // ----------------------------------------
    // only character can open doors
    // ----------------------------------------

    if (!(wootwoot instanceof PacManSprite)) {
      return;
    }

    PacManSprite pacManSprite = (PacManSprite) wootwoot;

    actionListenerProxy.fireActionEvent(new ActionEvent(this, 0, "die"));

    // TODO: A nice way to handle the keys
    pacManSprite.yellowKey--;
  }

  @Override
  public void paint(Graphics2D g2d) {
    g2d.setColor(Color.YELLOW.darker());
    g2d.fill3DRect((int) scrCurr.x, (int) scrCurr.y, Constants.TILE_W, Constants.TILE_H, true);
  }
}
