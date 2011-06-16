package org.cyrano.pacman.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.cyrano.pacman.base.BaseBean;
import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Constants;
import org.cyrano.pacman.base.LayerMatrix;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.util.game.Timer;
import org.cyrano.util.game.TimerBean;

public class SwitchItemSprite extends BaseSprite {

  private int count;

  // --------------------------------------------------------------------------------

  public SwitchItemSprite() {

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

  int tgtX;
  int tgtY;

  @Override
  public void init(LevelExec levelExec, BaseBean baseBean, String[] parmArray) {
    speed = Integer.parseInt(parmArray[0]);
    tgtX = Integer.parseInt(parmArray[1]);
    tgtY = Integer.parseInt(parmArray[2]);
    init(baseBean.getX(), baseBean.getY(), speed, levelExec);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void execStepOn(BaseSprite wootwoot, Timer timer) {
    count++;

    TimerBean tb = new TimerBean(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        count--;

        LayerMatrix layerArray = levelExec.getLayerArray();

        for (BaseSprite baseSprite : layerArray.get(tgtX, tgtY)) {
          if (baseSprite instanceof SwitchDoorSprite) {
            SwitchDoorSprite switchDoorSprite = (SwitchDoorSprite) baseSprite;
            switchDoorSprite.close();
            break;
          }
        }
      }
    }, new ActionEvent(this, 0, null), 10);
    timer.addTimerBean(tb);

    // XXX: Fix this, will handle only a door there, was done this way just to proof it works
    LayerMatrix layerArray = levelExec.getLayerArray();

    for (BaseSprite baseSprite : layerArray.get(tgtX, tgtY)) {
      if (baseSprite instanceof SwitchDoorSprite) {
        SwitchDoorSprite switchDoorSprite = (SwitchDoorSprite) baseSprite;
        switchDoorSprite.open();
        break;
      }
    }

    //    if (wootwoot instanceof PacManSprite) {
    //      actionListenerProxy.fireActionEvent(new ActionEvent(this, 0, "die"));
    //    }

    //  wootwoot.stepOn(this); // XXX: This will cause problem if two pacmans!!!
  }

  @Override
  public void paint(Graphics2D g2d) {
    if (count > 0) {
      g2d.setColor(Color.GREEN.darker());
      g2d.fill3DRect((int) scrCurr.x, (int) scrCurr.y, Constants.TILE_W, Constants.TILE_H, false);
    } else {
      g2d.setColor(Color.GREEN.darker().darker());
      g2d.fill3DRect((int) scrCurr.x, (int) scrCurr.y, Constants.TILE_W, Constants.TILE_H, true);
    }
  }
}
