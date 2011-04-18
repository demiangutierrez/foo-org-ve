package org.cyrano.pacman3;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class PacManSprite extends BaseSprite {

  protected List<BufferedImage> yPacList;
  protected List<BufferedImage> xPacList;

  protected int currDir = Constants.DIR_RG;
  protected int wantDir = Constants.DIR_RG;

  protected int destroy;

  // --------------------------------------------------------------------------------

  public PacManSprite(int grdX, int grdY, int speed, TextMap textMap) {
    super(grdX, grdY, speed, textMap);
  }

  // --------------------------------------------------------------------------------

  protected void loadImgs() throws IOException {
    yPacList = new ArrayList<BufferedImage>();
    yPacList.add(ImageIO.read(getClass().getResource("ypac1.png")));
    yPacList.add(ImageIO.read(getClass().getResource("ypac2.png")));
    yPacList.add(ImageIO.read(getClass().getResource("ypac3.png")));
    yPacList.add(ImageIO.read(getClass().getResource("ypac4.png")));
    yPacList.add(ImageIO.read(getClass().getResource("ypac5.png")));
    yPacList.add(ImageIO.read(getClass().getResource("ypac6.png")));
    yPacList.add(ImageIO.read(getClass().getResource("ypac5.png")));
    yPacList.add(ImageIO.read(getClass().getResource("ypac4.png")));
    yPacList.add(ImageIO.read(getClass().getResource("ypac3.png")));
    yPacList.add(ImageIO.read(getClass().getResource("ypac2.png")));

    xPacList = new ArrayList<BufferedImage>();
    xPacList.add(ImageIO.read(getClass().getResource("xpac1.png")));
    xPacList.add(ImageIO.read(getClass().getResource("xpac2.png")));
    xPacList.add(ImageIO.read(getClass().getResource("xpac3.png")));
    xPacList.add(ImageIO.read(getClass().getResource("xpac4.png")));
    xPacList.add(ImageIO.read(getClass().getResource("xpac5.png")));
    xPacList.add(ImageIO.read(getClass().getResource("xpac6.png")));
    xPacList.add(ImageIO.read(getClass().getResource("xpac5.png")));
    xPacList.add(ImageIO.read(getClass().getResource("xpac4.png")));
    xPacList.add(ImageIO.read(getClass().getResource("xpac3.png")));
    xPacList.add(ImageIO.read(getClass().getResource("xpac2.png")));

    bimgList = xPacList;
  }

  // --------------------------------------------------------------------------------

  protected void calcNext() {
    char[][] data = textMap.getData();

    grdCurr.x = grdNext.x;
    grdCurr.y = grdNext.y;

    // --------------------------------------------------------------------------------

    switch (wantDir) {
      case Constants.DIR_LF :
        if (data[grdCurr.x - 1][grdCurr.y] != 'X') {
          currDir = wantDir;
        }
        break;
      case Constants.DIR_RG :
        if (data[grdCurr.x + 1][grdCurr.y] != 'X') {
          currDir = wantDir;
        }
        break;
      case Constants.DIR_UP :
        if (data[grdCurr.x][grdCurr.y - 1] != 'X') {
          currDir = wantDir;
        }
        break;
      case Constants.DIR_DW :
        if (data[grdCurr.x][grdCurr.y + 1] != 'X') {
          currDir = wantDir;
        }
        break;
    }

    // --------------------------------------------------------------------------------

    switch (currDir) {
      case Constants.DIR_LF :
        if (data[grdCurr.x - 1][grdCurr.y] != 'X') {
          grdNext.x = grdCurr.x - 1;
        }
        break;
      case Constants.DIR_RG :
        if (data[grdCurr.x + 1][grdCurr.y] != 'X') {
          grdNext.x = grdCurr.x + 1;
        }
        break;
      case Constants.DIR_UP :
        if (data[grdCurr.x][grdCurr.y - 1] != 'X') {
          grdNext.y = grdCurr.y - 1;
        }
        break;
      case Constants.DIR_DW :
        if (data[grdCurr.x][grdCurr.y + 1] != 'X') {
          grdNext.y = grdCurr.y + 1;
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
}
