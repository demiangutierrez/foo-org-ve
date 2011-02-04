package boxcollision3_1;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Box {

  public enum Side {
    LFT, RGH, TOP, BOT
  }

  public enum Mark {
    BEGX, ENDX, BEGY, ENDY
  }

  public boolean/**/mv;

  public int/* */cx;
  public int/* */cy;

  public int/* */bw;
  public int/* */bh;

  public int/* */vx;
  public int/* */vy;

  //  public double/* */ax;
  //  public double/* */ay;

  public int/* */sx;
  public int/* */sy;

  public Color color;

  public String id;

  // --------------------------------------------------------------------------------

  public void calcV() {
    vx = sx - cx;
    vy = sy - cy;
  }

  public Box() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void updatePos(double dt) {
    if (!mv) {
      return;
    }

    cx = cx + (int) (vx * dt);
    cy = cy + (int) (vy * dt);
  }

  // --------------------------------------------------------------------------------

  public static void timeToCollide(CollisionInfo collisionInfo) {
    Box box1 = collisionInfo.box1;
    Box box2 = collisionInfo.box2;

    Point2D crashX = calcTimesToPotImpactX(box1, box2);
    Point2D crashY = calcTimesToPotImpactY(box1, box2);

    MarkTimeBean[] overlay;

    if (crashX == null || crashY == null) {
      collisionInfo.time = Double.MAX_VALUE;
      return;
    }

    overlay = calcOverlayArray(crashX, crashY);

    if (overlay == null) {
      collisionInfo.time = Double.MAX_VALUE;
      return;
    }

    collisionInfo.time = overlay[1].time;

    switch (overlay[1].mark) {
      case BEGX :
        if (box1.cx < box2.cx) {
          collisionInfo.box1Side = Side.RGH;
          collisionInfo.box2Side = Side.LFT;
        } else {
          collisionInfo.box1Side = Side.LFT;
          collisionInfo.box2Side = Side.RGH;
        }
        break;
      case BEGY :
        if (box1.cy < box2.cy) {
          collisionInfo.box1Side = Side.BOT;
          collisionInfo.box2Side = Side.TOP;
        } else {
          collisionInfo.box1Side = Side.TOP;
          collisionInfo.box2Side = Side.BOT;
        }
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private static Point getBoxOverX(Box box) {
    return new Point((int) box.cx, (int) (box.cx + box.bw));
  }

  // --------------------------------------------------------------------------------

  private static Point getBoxOverY(Box box) {
    return new Point((int) box.cy, (int) (box.cy + box.bh));
  }

  // --------------------------------------------------------------------------------

  private enum Pos {
    B1_OO_B2, //
    B1_B2_OO, //
    B2_OO_B1, //
    B2_B1_OO, //
    B1_B2_B1, //
    B2_B1_B2
  }

  private static Pos calcBoxXPos(Box box1, Box box2) {
    if (box1.minX() <= box2.minX() && box2.maxX() <= box1.maxX()) {
      return Pos.B1_B2_B1;
    }

    if (box2.minX() <= box1.minX() && box1.maxX() <= box2.maxX()) {
      return Pos.B2_B1_B2;
    }

    if (box1.minX() < box2.minX() && box2.minX() < box1.maxX()) {
      return Pos.B1_B2_OO;
    }

    if (box2.minX() < box1.minX() && box1.minX() < box2.maxX()) {
      return Pos.B2_B1_OO;
    }

    if (box1.minX() < box2.minX()) {
      return Pos.B1_OO_B2;
    }

    if (box1.minX() > box2.minX()) {
      return Pos.B2_OO_B1;
    }

    throw new IllegalStateException();
  }

  private static Pos calcBoxYPos(Box box1, Box box2) {
    if (box1.minY() <= box2.minY() && box2.maxY() <= box1.maxY()) {
      return Pos.B1_B2_B1;
    }

    if (box2.minY() <= box1.minY() && box1.maxY() <= box2.maxY()) {
      return Pos.B2_B1_B2;
    }

    if (box1.minY() < box2.minY() && box2.minY() < box1.maxY()) {
      return Pos.B1_B2_OO;
    }

    if (box2.minY() < box1.minY() && box1.minY() < box2.maxY()) {
      return Pos.B2_B1_OO;
    }

    if (box1.minY() < box2.minY()) {
      return Pos.B1_OO_B2;
    }

    if (box1.minY() > box2.minY()) {
      return Pos.B2_OO_B1;
    }

    throw new IllegalStateException();
  }

  private static Point2D calcTimesToPotImpactX(Box box1, Box box2) {
    Point bp1 = getBoxOverX(box1);
    Point bp2 = getBoxOverX(box2);

    int vRel;

    Pos boxXPos = calcBoxXPos(box1, box2);

    switch (boxXPos) {
      case B1_OO_B2 :
      case B1_B2_OO :
        vRel = (int) (box1.vx - box2.vx);
        break;
      case B2_OO_B1 :
      case B2_B1_OO :
        vRel = (int) (box2.vx - box1.vx);
        break;
      case B1_B2_B1 :
      case B2_B1_B2 :
        vRel = (int) (box1.vx - box2.vx);
        vRel = -Math.abs(vRel);
        break;
      default :
        throw new IllegalStateException();
    }

    int distCrash1 = Math.abs(bp1.x - bp2.y) - 1;
    int distCrash2 = Math.abs(bp1.y - bp2.x) - 1;

    int distCrashMin = Math.min(distCrash1, distCrash2);
    int distCrashMax = Math.max(distCrash1, distCrash2);

    if (boxXPos == Pos.B1_B2_B1 || boxXPos == Pos.B2_B1_B2 || //
        boxXPos == Pos.B1_B2_OO || boxXPos == Pos.B2_B1_OO) {
      if (vRel < 0) {
        return new Point2D.Double( //
            0, Math.abs(distCrashMin / (double) vRel));
      }

      if (vRel == 0) {
        return new Point2D.Double( //
            0, Double.MAX_VALUE);
      }

      if (vRel > 0) {
        return new Point2D.Double( //
            0, Math.abs(distCrashMax / (double) vRel));
      }
    }

    if (vRel < 0) {
      return null;
    }

    return new Point2D.Double( //
        (distCrashMin / (double) vRel), distCrashMax / (double) vRel);
  }

  // --------------------------------------------------------------------------------

  private static Point2D calcTimesToPotImpactY(Box box1, Box box2) {
    Point bp1 = getBoxOverY(box1);
    Point bp2 = getBoxOverY(box2);

    int vRel;

    Pos boxYPos = calcBoxYPos(box1, box2);

    switch (boxYPos) {
      case B1_OO_B2 :
      case B1_B2_OO :
        vRel = (int) (box1.vy - box2.vy);
        break;
      case B2_OO_B1 :
      case B2_B1_OO :
        vRel = (int) (box2.vy - box1.vy);
        break;
      case B1_B2_B1 :
      case B2_B1_B2 :
        vRel = (int) (box1.vy - box2.vy);
        vRel = -Math.abs(vRel);
        break;
      default :
        throw new IllegalStateException();
    }

    int distCrash1 = Math.abs(bp1.x - bp2.y) - 1;
    int distCrash2 = Math.abs(bp1.y - bp2.x) - 1;

    int distCrashMin = Math.min(distCrash1, distCrash2);
    int distCrashMax = Math.max(distCrash1, distCrash2);

    if (boxYPos == Pos.B1_B2_B1 || boxYPos == Pos.B2_B1_B2 || //
        boxYPos == Pos.B1_B2_OO || boxYPos == Pos.B2_B1_OO) {
      if (vRel < 0) {
        return new Point2D.Double( //
            0, Math.abs(distCrashMin / (double) vRel));
      }

      if (vRel == 0) {
        return new Point2D.Double( //
            0, Double.MAX_VALUE);
      }

      if (vRel > 0) {
        return new Point2D.Double( //
            0, Math.abs(distCrashMax / (double) vRel));
      }
    }

    if (vRel < 0) {
      return null;
    }

    return new Point2D.Double( //
        (distCrashMin / (double) vRel), distCrashMax / (double) vRel);
  }

  // --------------------------------------------------------------------------------

  private static MarkTimeBean[] calcOverlayArray(Point2D crashX, Point2D crashY) {
    if ((crashX.getX() > crashY.getY() || crashX.getY() < crashY.getX())) {
      //        && // 
      //        (crashX.getY() < crashY.getX() || crashX.getY() > crashY.getY())) {
      return null;
    }

    MarkTimeBean[] ret = new MarkTimeBean[]{ //
    new MarkTimeBean(crashX.getX(), Mark.BEGX), new MarkTimeBean(crashX.getY(), Mark.ENDX), //
        new MarkTimeBean(crashY.getX(), Mark.BEGY), new MarkTimeBean(crashY.getY(), Mark.ENDY)};

    // ----------------------------------------
    // A primitive sort...
    // ----------------------------------------

    boolean sorted = true;

    while (sorted) {
      sorted = false;

      for (int i = 0; i < ret.length - 1; i++) {
        if (ret[i].time > ret[i + 1].time) {
          MarkTimeBean aux;

          aux/*    */= ret[i + 1];
          ret[i + 1] = ret[i/**/];
          ret[i/**/] = aux;

          sorted = true;
        }
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static class CollisionInfo {

    // ----------------------------------------

    public Side/*  */box1Side;
    public Box/*   */box1;

    public Side/*  */box2Side;
    public Box/*   */box2;

    // ----------------------------------------

    public double/**/time;
  }

  // --------------------------------------------------------------------------------

  private static class MarkTimeBean {

    public double/**/time;
    public Mark/*  */mark;

    public MarkTimeBean(double time, Mark mark) {
      this.time = time;
      this.mark = mark;
    }

    @Override
    public String toString() {
      return time + ";" + mark;
    }
  }

  public int minX() {
    return cx;
  }

  public int minY() {
    return cy;
  }

  public int maxX() {
    return cx + bw;
  }

  public int maxY() {
    return cy + bh;
  }

}
