package org.cyrano.tools.normal;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.cyrano.common.ImageCache;
import org.cyrano.common.PaintableBase;

public class NormalImageFace extends PaintableBase {

  private BufferedImage bufferedImage;

  // --------------------------------------------------------------------------------

  public NormalImageFace(int x1, int y1, int x2, int y2) {
    super(x1, y1, x2, y2);

    bufferedImage = ImageCache.getInstance().getImage("smile1.png");
  }

  // --------------------------------------------------------------------------------

  @Override
  public void draw(Graphics2D g2d) {
    g2d.drawImage(bufferedImage, x1, y1, x2 - x1, y2 - y1, null);
  }
}
