package org.cyrano.pacman.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import org.cyrano.pacman.base.BaseBean;
import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Constants;
import org.cyrano.pacman.base.LevelExec;

public class SwitchDoorSprite extends BaseSprite {

  private int lockCount;

  // --------------------------------------------------------------------------------

  public SwitchDoorSprite() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void loadImgs() throws IOException {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void calcLook() {
    // Empty
  }

  @Override
  protected void calcNext() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void init(LevelExec levelExec, BaseBean baseBean, String[] parmArray) {
    init(baseBean.getX(), baseBean.getY(), speed, levelExec);
  }

  // --------------------------------------------------------------------------------

  @Override
  public boolean testStepOn(BaseSprite source) {
    return !isLocked();
  }

  // --------------------------------------------------------------------------------

  public boolean isLocked() {
    return lockCount == 0;
  }

  // --------------------------------------------------------------------------------

  public void unLock() {
    lockCount++;
  }

  public void doLock() {
    lockCount--;
  }

  // --------------------------------------------------------------------------------

  @Override
  public void paint(Graphics2D g2d) {
    if (isLocked()) {
      g2d.setColor(Color.RED.darker());
      g2d.fill3DRect( //
          (int) scrCurr.x, (int) scrCurr.y, //
          Constants.TILE_W, Constants.TILE_H, true);
    } else {
      g2d.setColor(Color.RED.darker().darker());
      g2d.fill3DRect( //
          (int) scrCurr.x, (int) scrCurr.y, //
          Constants.TILE_W, Constants.TILE_H, false);
    }
  }
}
