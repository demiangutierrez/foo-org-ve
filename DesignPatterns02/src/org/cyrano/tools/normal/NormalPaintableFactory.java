package org.cyrano.tools.normal;

import org.cyrano.common.Paintable;
import org.cyrano.common.PaintableFactory;

// Factory
public class NormalPaintableFactory implements PaintableFactory {

  //  private boolean useImage = true;

  public Paintable create(int x1, int y1, int x2, int y2) {
    //    if (useImage) {
    return new NormalImageFace(x1, y1, x2, y2);
    //    } else {
    //      return new DrawnFace(x1, y1, x2, y2);
    //    }
  }
}
