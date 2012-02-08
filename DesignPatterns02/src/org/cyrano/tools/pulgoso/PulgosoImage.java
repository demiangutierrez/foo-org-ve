package org.cyrano.tools.pulgoso;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.cyrano.common.ImageCache;
import org.cyrano.common.PaintableBase;

public class PulgosoImage extends PaintableBase {

  public PulgosoImage(int x1, int y1, int x2, int y2) {
    super(x1, y1, x2, y2);
  }

  @Override
  public void draw(Graphics2D g2d) {
    BufferedImage img = ImageCache.getInstance().getImage("pulgoso.gif");
    g2d.drawImage(img, x1, y1, null);
  }

}
