package org.cyrano.pacman2;

import java.io.IOException;

import javax.imageio.ImageIO;

public class PacManSprite extends BaseSprite {

  private int currDir = Constants.DIR_RG;
  private int wantDir = Constants.DIR_RG;

  // --------------------------------------------------------------------------------

  public PacManSprite(int grdX, int grdY, int speed, TextMap textMap) {
    super(grdX, grdY, speed, textMap);
  }

  // --------------------------------------------------------------------------------

  protected void loadImgs() throws IOException {
    bimgList.add(ImageIO.read(getClass().getResource("xpac1.png")));
    bimgList.add(ImageIO.read(getClass().getResource("xpac2.png")));
    bimgList.add(ImageIO.read(getClass().getResource("xpac3.png")));
    bimgList.add(ImageIO.read(getClass().getResource("xpac4.png")));
    bimgList.add(ImageIO.read(getClass().getResource("xpac5.png")));
    bimgList.add(ImageIO.read(getClass().getResource("xpac6.png")));
    bimgList.add(ImageIO.read(getClass().getResource("xpac5.png")));
    bimgList.add(ImageIO.read(getClass().getResource("xpac4.png")));
    bimgList.add(ImageIO.read(getClass().getResource("xpac3.png")));
    bimgList.add(ImageIO.read(getClass().getResource("xpac2.png")));
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

  public int getWantDir() {
    return wantDir;
  }

  public void setWantDir(int wantDir) {
    this.wantDir = wantDir;
  }
}
