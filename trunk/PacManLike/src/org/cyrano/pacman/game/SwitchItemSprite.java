package org.cyrano.pacman.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.cyrano.pacman.base.BaseBean;
import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Constants;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.pacman.base.SpriteMatrix;
import org.cyrano.util.game.TimerBean;

public class SwitchItemSprite extends BaseSprite {

  private int tgtX;
  private int tgtY;

  // --------------------------------------------------------------------------------

  public SwitchItemSprite() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
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
    tgtX = Integer.parseInt(parmArray[0]);
    tgtY = Integer.parseInt(parmArray[1]);

    init(baseBean.getX(), baseBean.getY(), speed, levelExec);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void execStepOn(BaseSprite wootwoot) {
    TimerBean tb = new TimerBean(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        getTarget().unLock();
      }
    }, new ActionEvent(this, 0, null), 10);

    levelExec.getTimer().addTimerBean(tb);

    // ----------------------------------------

    getTarget().doLock();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void paint(Graphics2D g2d) {
    if (getTarget().isLocked()) {
      g2d.setColor(Color.GREEN.darker());
      g2d.fill3DRect( //
          (int) scrCurr.x, (int) scrCurr.y, //
          Constants.TILE_W, Constants.TILE_H, true);
    } else {
      g2d.setColor(Color.GREEN.darker().darker());
      g2d.fill3DRect( //
          (int) scrCurr.x, (int) scrCurr.y, //
          Constants.TILE_W, Constants.TILE_H, false);
    }
  }

  // --------------------------------------------------------------------------------

  private SwitchDoorSprite getTarget() {
    SpriteMatrix layerArray = levelExec.getSpriteMatrix();

    for (BaseSprite baseSprite : layerArray.get(tgtX, tgtY)) {
      if (baseSprite instanceof SwitchDoorSprite) {
        return (SwitchDoorSprite) baseSprite;
      }
    }

    throw new IllegalStateException();
  }
}
