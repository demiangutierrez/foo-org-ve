package org.cyrano.pacman.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import org.cyrano.pacman.base.BaseBean;
import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Constants;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.util.game.Timer;

public class SwitchDoorSprite extends BaseSprite {

  private int count;

  // --------------------------------------------------------------------------------

  public SwitchDoorSprite() {
    // empty
  }

  // --------------------------------------------------------------------------------

  protected void loadImgs() throws IOException {
    // empty
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void calcLook() {
    // empty
  }

  @Override
  protected void calcNext() {
    // empty
  }

  @Override
  public void init(LevelExec levelExec, BaseBean baseBean, String[] parmArray) {
    speed = Integer.parseInt(parmArray[0]);
    init(baseBean.getX(), baseBean.getY(), speed, levelExec);
  }

  // --------------------------------------------------------------------------------

  @Override
  public boolean testStepOn(BaseSprite wootwoot, Timer timer) {
    if (count == 0) {
      return false;
    }

    return true;
  }

  @Override
  public void execStepOn(BaseSprite wootwoot, Timer timer) {
  }

  public void open() {
    count++;
  }

  public void close() {
    count--;
  }

  @Override
  public void paint(Graphics2D g2d) {
    if (count > 0) {
      g2d.setColor(Color.RED.darker().darker());
      g2d.fill3DRect( //
          (int) scrCurr.x, (int) scrCurr.y, //
          Constants.TILE_W, Constants.TILE_H, false);
    } else {
      g2d.setColor(Color.RED.darker());
      g2d.fill3DRect( //
          (int) scrCurr.x, (int) scrCurr.y, //
          Constants.TILE_W, Constants.TILE_H, true);
    }
  }
}
