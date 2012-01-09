package org.cyrano.rubik.model;

import java.awt.Color;

public enum FaceletColor {

  Y, G, W, B, R, O;

  // --------------------------------------------------------------------------------

  public Color translateColor() {
    switch (this) {
      case Y :
        return new Color(0xA0, 0xA0, 0x00);
      case G :
        return new Color(0x00, 0xFF, 0x00);
      case W :
        return new Color(0x00, 0xA0, 0xA0);
      case B :
        return new Color(0x00, 0x00, 0xFF);
      case R :
        return new Color(0xFF, 0x00, 0x00);
      case O :
        return new Color(0xFF, 0x80, 0x80);
      default :
        throw new IllegalStateException( //
            this.toString());
    }
  }

  // --------------------------------------------------------------------------------

  public static FaceletColor fromNormal(int x, int y, int z) {
    if (x == +0 && y == +0 && z == +1) {
      return Y;
    }

    if (x == +0 && y == +1 && z == +0) {
      return G;
    }

    if (x == +0 && y == +0 && z == -1) {
      return W;
    }

    if (x == +0 && y == -1 && z == +0) {
      return B;
    }

    if (x == +1 && y == +0 && z == +0) {
      return R;
    }

    if (x == -1 && y == +0 && z == +0) {
      return O;
    }

    throw new IllegalArgumentException( //
        "x = " + x + "; y = " + y + "; z = " + z);
  }
}
