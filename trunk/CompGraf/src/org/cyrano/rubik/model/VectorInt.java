package org.cyrano.rubik.model;

public class VectorInt {

  public int x;
  public int y;
  public int z;

  // --------------------------------------------------------------------------------

  public VectorInt() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public VectorInt(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  // --------------------------------------------------------------------------------

  public void transform(int[][] t) {
    int xp = t[0][0] * x + t[0][1] * y + t[0][2] * z;
    int yp = t[1][0] * x + t[1][1] * y + t[1][2] * z;
    int zp = t[2][0] * x + t[2][1] * y + t[2][2] * z;

    x = xp;
    y = yp;
    z = zp;
  }
}
