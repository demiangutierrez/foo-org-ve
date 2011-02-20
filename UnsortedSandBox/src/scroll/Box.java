package scroll;

public class Box {

  public int x;
  public int y;

  public int w;
  public int h;

  //  // ----------------------------------------
  //
  //  public int getW() {
  //    return w;
  //  }
  //
  //  public int getH() {
  //    return h;
  //  }

  // ----------------------------------------

  public int minX() {
    return x;
  }

  public int minY() {
    return y;

  }

  // ----------------------------------------

  public int maxX() {
    return x + w;
  }

  public int maxY() {
    return y + h;
  }
}
