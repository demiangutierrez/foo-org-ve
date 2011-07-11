package org.cyrano.pacman.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.pacman.base.BaseBean;
import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Constants;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.util.misc.ImageCache;

public abstract class GhostSprite extends BaseSprite {

  protected List<BufferedImage> yPacList;
  protected List<BufferedImage> xPacList;
  protected List<BufferedImage> zPacList;

  protected List<BufferedImage> dPacList;

  protected int destroy = 0;
  protected boolean dead;

  // --------------------------------------------------------------------------------

  public GhostSprite() {
    lookRotate = false;
    stepsPerSec = 8;
  }

  protected void loadImgs() throws IOException {
    yPacList = new ArrayList<BufferedImage>();
    yPacList.add(ImageCache.getInstance().getImage("yghs1.png"));
    yPacList.add(ImageCache.getInstance().getImage("yghs2.png"));

    zPacList = new ArrayList<BufferedImage>();
    zPacList.add(ImageCache.getInstance().getImage("yghs1.png"));
    zPacList.add(ImageCache.getInstance().getImage("zghs1.png"));
    zPacList.add(ImageCache.getInstance().getImage("yghs2.png"));
    zPacList.add(ImageCache.getInstance().getImage("zghs2.png"));

    dPacList = new ArrayList<BufferedImage>();
    dPacList.add(ImageCache.getInstance().getImage("grave.png"));

    bimgList = yPacList;
  }

  // --------------------------------------------------------------------------------

  protected void calcLook() {
    if (dead) {
      scrLook.x = (grdCurr.x) * Constants.TILE_W;
      scrLook.y = (grdCurr.y + 1) * Constants.TILE_H;
    } else {
      scrLook.x = grdNext.x * Constants.TILE_W;
      scrLook.y = grdNext.y * Constants.TILE_H;
    }
  }

  // --------------------------------------------------------------------------------

  public void updateBimgList() {
    if (dead) {
      bimgList = dPacList;
    } else {
      switch (destroy) {
        case 0 :
          bimgList = xPacList;
          break;
        case 1 :
          bimgList = zPacList;
          break;
        default :
          bimgList = yPacList;
          break;
      }
    }
  }

  // --------------------------------------------------------------------------------

  public int getDestroy() {
    return destroy;
  }

  public void incDestroy() {
    destroy += 2;
    System.err.println("destroy inc: " + destroy);
    updateBimgList();
  }

  public void decDestroy() {
    destroy--;
    System.err.println("destroy dec: " + destroy);
    updateBimgList();
  }

  // --------------------------------------------------------------------------------

  public boolean getDead() {
    return dead;
  }

  public void setDead(boolean dead) {
    this.dead = dead;
    updateBimgList();
  }

  @Override
  public void init(LevelExec levelExec, BaseBean baseBean, String[] parmArray) {
    speed = Integer.parseInt(parmArray[0]);
    init(baseBean.getX(), baseBean.getY(), speed, levelExec);
  }
}
