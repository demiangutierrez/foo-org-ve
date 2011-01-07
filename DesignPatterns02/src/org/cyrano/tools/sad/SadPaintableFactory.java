package org.cyrano.tools.sad;

import org.cyrano.common.Paintable;
import org.cyrano.common.PaintableFactory;

// Factory
public class SadPaintableFactory implements PaintableFactory {

  //  private boolean useImage = true;

  public Paintable create(int x1, int y1, int x2, int y2) {
    //    if (useImage) {
    return new SadImageFace(x1, y1, x2, y2);
    //    } else {
    //      return new DrawnFace(x1, y1, x2, y2);
    //    }
  }
}
