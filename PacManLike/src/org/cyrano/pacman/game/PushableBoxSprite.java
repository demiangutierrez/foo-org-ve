package org.cyrano.pacman.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import org.cyrano.pacman.base.BaseBean;
import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Constants;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.util.game.Timer;

public class PushableBoxSprite extends BaseSprite {

  private int count;

  // --------------------------------------------------------------------------------

  public PushableBoxSprite() {

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
  public boolean canMove(BaseSprite wootwoot, Timer timer) {

    int direction;

    if (wootwoot.getGrdCurr().x < grdCurr.x) {
      direction = Constants.DIR_RG;
    } else if (wootwoot.getGrdCurr().x > grdCurr.x) {
      direction = Constants.DIR_LF;
    } else if (wootwoot.getGrdCurr().y < grdCurr.y) {
      direction = Constants.DIR_DW;
    } else {
      direction = Constants.DIR_UP;
    }
    
    // --------------------------------------------------------------------------------

    // This code is redundant, mostly copied from PacManSprite
    char[][] data = levelExec.getData();

    BaseSprite[][] dyna = levelExec.getDyna();

    
    BaseSprite toBs = null;

    switch (direction) {
      case Constants.DIR_LF :
        toBs = dyna[grdCurr.x - 1][grdCurr.y];
        if (data[grdCurr.x - 1][grdCurr.y] != 'X') {
          if (toBs == null || toBs.checkStackMoveOn(this)) {
            return true;
          }
        }
        break;
      case Constants.DIR_RG :
        toBs = dyna[grdCurr.x + 1][grdCurr.y];
        if (data[grdCurr.x + 1][grdCurr.y] != 'X') {
          if (toBs == null || toBs.checkStackMoveOn(this)) {
            return true;
          }
        }
        break;
      case Constants.DIR_UP :
        toBs = dyna[grdCurr.x][grdCurr.y - 1];
        if (data[grdCurr.x][grdCurr.y - 1] != 'X') {
          if (toBs == null || toBs.checkStackMoveOn(this)) {
            return true;
          }
        }
        break;
      case Constants.DIR_DW :
        toBs = dyna[grdCurr.x][grdCurr.y + 1];
        if (data[grdCurr.x][grdCurr.y + 1] != 'X') {
          if (toBs == null || toBs.checkStackMoveOn(this)) {
            return true;
          }
        }
        break;
    }

    return false;
  }

  // XXX: there is no way to do this!!!
  // Specially slowly (I.E moving the box little by little in a countinuous way
  @Override
  public void stepOn(BaseSprite wootwoot, Timer timer) {
    int direction;
    
    if (wootwoot.getGrdCurr().x < grdCurr.x) {
      direction = Constants.DIR_RG;
    } else if (wootwoot.getGrdCurr().x > grdCurr.x) {
      direction = Constants.DIR_LF;
    } else if (wootwoot.getGrdCurr().y < grdCurr.y) {
      direction = Constants.DIR_DW;
    } else {
      direction = Constants.DIR_UP;
    }

  }

  @Override
  public void paint(Graphics2D g2d) {
    g2d.setColor(Color.BLUE.darker().darker());
    g2d.fill3DRect((int) scrCurr.x, (int) scrCurr.y, Constants.TILE_W, Constants.TILE_H, true);
  }
}
