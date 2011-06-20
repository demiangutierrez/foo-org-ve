package org.cyrano.pacman.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.pacman.base.BaseBean;
import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Constants;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.pacman.base.SpriteMatrix;
import org.cyrano.util.ImageCache;

public class PacManSprite extends BaseSprite {

  protected List<BufferedImage> yPacList;
  protected List<BufferedImage> xPacList;

  protected int currDir = Constants.DIR_RG;
  protected int wantDir = Constants.DIR_RG;

  protected int destroy;

  public int yellowKey; // TODO, Private

  // --------------------------------------------------------------------------------

  public PacManSprite() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void loadImgs() throws IOException {
    yPacList = new ArrayList<BufferedImage>();
    yPacList.add(ImageCache.getInstance().getImage("ypac1.png"));
    yPacList.add(ImageCache.getInstance().getImage("ypac2.png"));
    yPacList.add(ImageCache.getInstance().getImage("ypac3.png"));
    yPacList.add(ImageCache.getInstance().getImage("ypac4.png"));
    yPacList.add(ImageCache.getInstance().getImage("ypac5.png"));
    yPacList.add(ImageCache.getInstance().getImage("ypac6.png"));
    yPacList.add(ImageCache.getInstance().getImage("ypac5.png"));
    yPacList.add(ImageCache.getInstance().getImage("ypac4.png"));
    yPacList.add(ImageCache.getInstance().getImage("ypac3.png"));
    yPacList.add(ImageCache.getInstance().getImage("ypac2.png"));

    xPacList = new ArrayList<BufferedImage>();
    xPacList.add(ImageCache.getInstance().getImage("xpac1.png"));
    xPacList.add(ImageCache.getInstance().getImage("xpac2.png"));
    xPacList.add(ImageCache.getInstance().getImage("xpac3.png"));
    xPacList.add(ImageCache.getInstance().getImage("xpac4.png"));
    xPacList.add(ImageCache.getInstance().getImage("xpac5.png"));
    xPacList.add(ImageCache.getInstance().getImage("xpac6.png"));
    xPacList.add(ImageCache.getInstance().getImage("xpac5.png"));
    xPacList.add(ImageCache.getInstance().getImage("xpac4.png"));
    xPacList.add(ImageCache.getInstance().getImage("xpac3.png"));
    xPacList.add(ImageCache.getInstance().getImage("xpac2.png"));

    bimgList = xPacList;
  }

  // --------------------------------------------------------------------------------

  protected void calcNext() {
    char[][] data = levelExec.getData();

    //    BaseSprite[][] dyna = levelExec.getDyna();
    SpriteMatrix layerArray = levelExec.getSpriteMatrix();

    grdCurr.x = grdNext.x;
    grdCurr.y = grdNext.y;

    // --------------------------------------------------------------------------------

    switch (wantDir) {
      case Constants.DIR_LF :
        if (data[grdCurr.x - 1][grdCurr.y] != 'X') {
          if (layerArray.testStepOn(grdCurr.x - 1, grdCurr.y, this)) {
            currDir = wantDir;
          }
        }
        break;
      case Constants.DIR_RG :
        if (data[grdCurr.x + 1][grdCurr.y] != 'X') {
          if (layerArray.testStepOn(grdCurr.x + 1, grdCurr.y, this)) {
            currDir = wantDir;
          }
        }
        break;
      case Constants.DIR_UP :
        if (data[grdCurr.x][grdCurr.y - 1] != 'X') {
          if (layerArray.testStepOn(grdCurr.x, grdCurr.y - 1, this)) {
            currDir = wantDir;
          }
        }
        break;
      case Constants.DIR_DW :
        if (data[grdCurr.x][grdCurr.y + 1] != 'X') {
          if (layerArray.testStepOn(grdCurr.x, grdCurr.y + 1, this)) {
            currDir = wantDir;
          }
        }
        break;
    }

    // --------------------------------------------------------------------------------

    switch (currDir) {
      case Constants.DIR_LF :
        if (data[grdCurr.x - 1][grdCurr.y] != 'X') {
          if (layerArray.testStepOn(grdCurr.x - 1, grdCurr.y, this)) {
            grdNext.x = grdCurr.x - 1;
          }
        }
        break;
      case Constants.DIR_RG :
        if (data[grdCurr.x + 1][grdCurr.y] != 'X') {
          if (layerArray.testStepOn(grdCurr.x + 1, grdCurr.y, this)) {
            grdNext.x = grdCurr.x + 1;
          }
        }
        break;
      case Constants.DIR_UP :
        if (data[grdCurr.x][grdCurr.y - 1] != 'X') {
          if (layerArray.testStepOn(grdCurr.x, grdCurr.y - 1, this)) {
            grdNext.y = grdCurr.y - 1;
          }
        }
        break;
      case Constants.DIR_DW :
        if (data[grdCurr.x][grdCurr.y + 1] != 'X') {
          if (layerArray.testStepOn(grdCurr.x, grdCurr.y + 1, this)) {
            grdNext.y = grdCurr.y + 1;
          }
        }
        break;
    }
  }

  // --------------------------------------------------------------------------------

  protected void calcLook() {
    switch (currDir) {
      case Constants.DIR_LF :
        scrLook.x = (grdCurr.x - 1) * Constants.TILE_W;
        scrLook.y = (grdCurr.y)/**/* Constants.TILE_H;
        break;
      case Constants.DIR_RG :
        scrLook.x = (grdCurr.x + 1) * Constants.TILE_W;
        scrLook.y = (grdCurr.y)/**/* Constants.TILE_H;
        break;
      case Constants.DIR_UP :
        scrLook.x = (grdCurr.x)/**/* Constants.TILE_W;
        scrLook.y = (grdCurr.y - 1) * Constants.TILE_H;
        break;
      case Constants.DIR_DW :
        scrLook.x = (grdCurr.x)/**/* Constants.TILE_W;
        scrLook.y = (grdCurr.y + 1) * Constants.TILE_H;
        break;
    }
  }

  // --------------------------------------------------------------------------------

  public void updateBimgList() {
    if (destroy > 0) {
      bimgList = yPacList;
    } else {
      bimgList = xPacList;
    }
  }

  // --------------------------------------------------------------------------------

  public int getWantDir() {
    return wantDir;
  }

  public void setWantDir(int wantDir) {
    this.wantDir = wantDir;
  }

  // --------------------------------------------------------------------------------

  public int getDestroy() {
    return destroy;
  }

  public void incDestroy() {
    destroy++;
    updateBimgList();
  }

  public void decDestroy() {
    destroy--;
    updateBimgList();
  }

  @Override
  public void init(LevelExec levelExec, BaseBean baseBean, String[] parmArray) {
    speed = Integer.parseInt(parmArray[0]);
    init(baseBean.getX(), baseBean.getY(), speed, levelExec);
  }

  // --------------------------------------------------------------------------------

  public boolean isPlayer() {
    return true;
  }
}
