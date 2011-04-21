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
import org.cyrano.util.game.Timer;

public class TeleportPadSprite extends BaseSprite {

  private int count;

  // --------------------------------------------------------------------------------

  public TeleportPadSprite() {

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
  public void stepOn(BaseSprite wootwoot, Timer timer) {
    wootwoot.init(tgtX, tgtY);
  }

  @Override
  public void paint(Graphics2D g2d) {
      g2d.setColor(Color.BLUE.darker().darker());
      g2d.fill3DRect((int) scrCurr.x, (int) scrCurr.y, Constants.TILE_W, Constants.TILE_H, true);
  }
}
