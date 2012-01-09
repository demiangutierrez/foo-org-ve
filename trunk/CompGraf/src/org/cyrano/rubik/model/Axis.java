package org.cyrano.rubik.model;

public enum Axis {

  X_POS, X_NEG, Y_POS, Y_NEG, Z_POS, Z_NEG;

  // --------------------------------------------------------------------------------

  public int[] trans(int i, int j, int l) {
    int[] ret = new int[3];

    switch (this) {
      case X_POS :
        ret[0] = l;
        ret[1] = i;
        ret[2] = j;
        break;
      case X_NEG :
        ret[0] = l;
        ret[1] = i;
        ret[2] = j;
        break;

      case Y_POS :
        ret[0] = i;
        ret[1] = l;
        ret[2] = j;
        break;

      case Y_NEG :
        ret[0] = i;
        ret[1] = l;
        ret[2] = j;
        break;

      case Z_POS :
        ret[0] = i;
        ret[1] = j;
        ret[2] = l;
        break;

      case Z_NEG :
        ret[0] = i;
        ret[1] = j;
        ret[2] = l;
        break;

      default :
        throw new IllegalArgumentException(toString());
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public int[] unitVector() {
    int[] ret = new int[3];

    ret[0] = +0;
    ret[1] = +0;
    ret[2] = +0;

    switch (this) {
      case X_POS :
        ret[0] = +1;
        break;

      case X_NEG :
        ret[0] = -1;
        break;

      case Y_POS :
        ret[1] = +1;
        break;

      case Y_NEG :
        ret[1] = -1;
        break;

      case Z_POS :
        ret[2] = +1;
        break;

      case Z_NEG :
        ret[2] = -1;
        break;

      default :
        throw new IllegalArgumentException(toString());
    }

    return ret;
  }
}
