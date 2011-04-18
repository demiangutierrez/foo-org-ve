package org.cyrano.pacman2;

import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class GhostSprite extends BaseSprite {

  protected BaseSprite tgtSprite;

  // --------------------------------------------------------------------------------

  public GhostSprite(int grdX, int grdY, int speed, TextMap textMap, BaseSprite tgtSprite) {
    super(grdX, grdY, speed, textMap);

    scrLook = new DblPoint();
    scrLook.x = grdX * Constants.TILE_W;
    scrLook.y = grdY * Constants.TILE_H;

    this.tgtSprite = tgtSprite;
  }

  // --------------------------------------------------------------------------------

  protected void loadImgs() throws IOException {
    bimgList.add(ImageIO.read(getClass().getResource("ypac1.png")));
    bimgList.add(ImageIO.read(getClass().getResource("ypac2.png")));
    bimgList.add(ImageIO.read(getClass().getResource("ypac3.png")));
    bimgList.add(ImageIO.read(getClass().getResource("ypac4.png")));
    bimgList.add(ImageIO.read(getClass().getResource("ypac5.png")));
    bimgList.add(ImageIO.read(getClass().getResource("ypac6.png")));
    bimgList.add(ImageIO.read(getClass().getResource("ypac5.png")));
    bimgList.add(ImageIO.read(getClass().getResource("ypac4.png")));
    bimgList.add(ImageIO.read(getClass().getResource("ypac3.png")));
    bimgList.add(ImageIO.read(getClass().getResource("ypac2.png")));
  }

  // --------------------------------------------------------------------------------

  protected void calcLook() {
    scrLook.x = grdNext.x * Constants.TILE_W;
    scrLook.y = grdNext.y * Constants.TILE_H;
  }
}
