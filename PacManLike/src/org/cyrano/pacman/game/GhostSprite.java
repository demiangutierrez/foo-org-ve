package org.cyrano.pacman.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Constants;
import org.cyrano.pacman.base.LevelLoad;
import org.cyrano.util.base.ImageCache;
import org.cyrano.util.base.PointDbl;

public abstract class GhostSprite extends BaseSprite {

  protected List<BufferedImage> yPacList;
  protected List<BufferedImage> xPacList;
  protected List<BufferedImage> dPacList;

  protected int destroy = 1;
  protected boolean dead;

  protected BaseSprite tgtSprite;

  // --------------------------------------------------------------------------------

  public GhostSprite(int grdX, int grdY, int speed, LevelLoad textMap, BaseSprite tgtSprite) {
    super(grdX, grdY, speed, textMap);

    scrLook = new PointDbl();
    scrLook.x = grdX * Constants.TILE_W;
    scrLook.y = grdY * Constants.TILE_H;

    this.tgtSprite = tgtSprite;
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
      if (destroy > 0) {
        bimgList = yPacList;
      } else {
        bimgList = xPacList;
      }
    }
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

  // --------------------------------------------------------------------------------

  public boolean getDead() {
    return dead;
  }

  public void setDead(boolean dead) {
    this.dead = dead;
    updateBimgList();
  }
}
