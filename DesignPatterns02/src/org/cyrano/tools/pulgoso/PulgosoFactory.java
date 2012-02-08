package org.cyrano.tools.pulgoso;

import org.cyrano.common.Paintable;
import org.cyrano.common.PaintableFactory;

public class PulgosoFactory implements PaintableFactory {

  @Override
  public Paintable create(int x1, int y1, int x2, int y2) {
    return new PulgosoImage(x1, y1, x2, y2);
  }
}
