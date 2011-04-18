package org.cyrano.pacman3;

import java.io.IOException;

import javax.imageio.ImageIO;

public class KeyItemSprite extends BaseSprite {


  // --------------------------------------------------------------------------------

  public KeyItemSprite(int grdX, int grdY) {
    super(grdX, grdY, 0, null);
  }

  // --------------------------------------------------------------------------------

  protected void loadImgs() throws IOException {
    bimgList.add(ImageIO.read(getClass().getResource("key.png")));
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void calcLook() {
  }

  @Override
  protected void calcNext() {
  }
}
