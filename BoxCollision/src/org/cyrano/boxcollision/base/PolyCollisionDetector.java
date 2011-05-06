package org.cyrano.boxcollision.base;

import java.awt.Point;
import java.awt.geom.Point2D;

public class PolyCollisionDetector {

  private enum Mark {
    BEGX, ENDX, BEGY, ENDY
  }

  public enum Side {
    LFT, RGH, TOP, BOT
  }

  public enum BoxP {
    B1_OO_B2, //
    B1_B2_OO, //
    B2_OO_B1, //
    B2_B1_OO, //
    B1_B2_B1, //
    B2_B1_B2
  }

  // --------------------------------------------------------------------------------

  private PolyCollisionDetector() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static void calcTimeToCollide(CollisionInfo collisionInfo) {
    Box box1 = collisionInfo.box1;
    Box box2 = collisionInfo.box2;

    Point2D crashX = calcTimesToPotImpactX(box1, box2);
    Point2D crashY = calcTimesToPotImpactY(box1, box2);

    TimeMarkBean[] overlay;

    if (crashX == null || crashY == null) {
      collisionInfo.time = Double.MAX_VALUE;
      return;
    }

    overlay = calcOverlayArray(crashX, crashY);

    if (overlay == null) {
      return;
    }

    collisionInfo.time = overlay[1].time;

    calcCollisionSide(collisionInfo, overlay);
  }

  // --------------------------------------------------------------------------------

  private static void calcCollisionSide( //
      CollisionInfo collisionInfo, TimeMarkBean[] overlay) {

    switch (overlay[1].mark) {
      case BEGX :
        if (collisionInfo.box1.minX() < collisionInfo.box2.minX()) {
          collisionInfo.box1Side = Side.RGH;
          collisionInfo.box2Side = Side.LFT;
        } else {
          collisionInfo.box1Side = Side.LFT;
          collisionInfo.box2Side = Side.RGH;
        }
        break;

      case BEGY :
        if (collisionInfo.box1.minY() < collisionInfo.box2.minY()) {
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
    return new Point(box.minX(), box.maxX());
  }

  // --------------------------------------------------------------------------------

  private static Point getBoxOverY(Box box) {
    return new Point(box.minY(), box.maxY());
  }

  // --------------------------------------------------------------------------------

  private static Point2D calcTimesToPotImpactX(Box box1, Box box2) {
    Point bp1 = getBoxOverX(box1);
    Point bp2 = getBoxOverX(box2);

    return calcTimesToPotImpact(bp1, bp2, box1.velX(), box2.velX());
  }

  // --------------------------------------------------------------------------------

  private static Point2D calcTimesToPotImpactY(Box box1, Box box2) {
    Point bp1 = getBoxOverY(box1);
    Point bp2 = getBoxOverY(box2);

    return calcTimesToPotImpact(bp1, bp2, box1.velY(), box2.velY());
  }

  // --------------------------------------------------------------------------------

  private static Point2D calcTimesToPotImpact( //
      Point bp1, Point bp2, int ve1, int ve2) {

    int vRel;

    BoxP boxYPos = calcBoxPos(bp1, bp2);

    switch (boxYPos) {
      case B1_OO_B2 :
      case B1_B2_OO :
        vRel = (int) (ve1 - ve2);
        break;
      case B2_OO_B1 :
      case B2_B1_OO :
        vRel = (int) (ve2 - ve1);
        break;
      case B1_B2_B1 :
      case B2_B1_B2 :
        vRel = (int) (ve1 - ve2);
        vRel = -Math.abs(vRel);
        break;
      default :
        throw new IllegalStateException();
    }

    int distCrash1 = Math.abs(bp1.x - bp2.y) - 1;
    int distCrash2 = Math.abs(bp1.y - bp2.x) - 1;

    int distCrashMin = Math.min(distCrash1, distCrash2);
    int distCrashMax = Math.max(distCrash1, distCrash2);

    if (boxYPos == BoxP.B1_B2_B1 || boxYPos == BoxP.B2_B1_B2 || //
        boxYPos == BoxP.B1_B2_OO || boxYPos == BoxP.B2_B1_OO) {
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

    if (vRel <= 0) {
      return null;
    }

    return new Point2D.Double( //
        (distCrashMin / (double) vRel), distCrashMax / (double) vRel);
  }

  // --------------------------------------------------------------------------------

  private static TimeMarkBean[] calcOverlayArray(Point2D crashX, Point2D crashY) {
    if ((crashX.getX() > crashY.getY() || crashX.getY() < crashY.getX())) {
      return null;
    }

    TimeMarkBean[] ret = new TimeMarkBean[]{ //
    /**/new TimeMarkBean(crashX.getX(), Mark.BEGX), //
        new TimeMarkBean(crashX.getY(), Mark.ENDX), //
        new TimeMarkBean(crashY.getX(), Mark.BEGY), //
        new TimeMarkBean(crashY.getY(), Mark.ENDY)};

    // ----------------------------------------
    // A primitive sort...
    // ----------------------------------------

    boolean sorted = true;

    while (sorted) {
      sorted = false;

      for (int i = 0; i < ret.length - 1; i++) {
        if (ret[i].time > ret[i + 1].time) {
          TimeMarkBean aux;

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

  public static BoxP calcBoxPos(Point p1, Point p2) {
    if (p1.x <= p2.x && p2.y <= p1.y) {
      return BoxP.B1_B2_B1;
    }

    if (p2.x <= p1.x && p1.y <= p2.y) {
      return BoxP.B2_B1_B2;
    }

    if (p1.x < p2.x && p2.x < p1.y) {
      return BoxP.B1_B2_OO;
    }

    if (p2.x < p1.x && p1.x < p2.y) {
      return BoxP.B2_B1_OO;
    }

    if (p1.x < p2.x) {
      return BoxP.B1_OO_B2;
    }

    if (p1.x > p2.x) {
      return BoxP.B2_OO_B1;
    }

    throw new IllegalStateException();
  }

  // --------------------------------------------------------------------------------

  public static BoxP calcBoxXPos(Box box1, Box box2) {
    return calcBoxPos( //
        new Point(box1.minX(), box1.maxX()), //
        new Point(box2.minX(), box2.maxX()));
  }

  // --------------------------------------------------------------------------------

  public static BoxP calcBoxYPos(Box box1, Box box2) {
    return calcBoxPos( //
        new Point(box1.minY(), box1.maxY()), //
        new Point(box2.minY(), box2.maxY()));
  }

  // --------------------------------------------------------------------------------

  public static class CollisionInfo {

    // ----------------------------------------

    public Side/*  */box1Side;
    public Box/*   */box1;

    public Side/*  */box2Side;
    public Box/*   */box2;

    // ----------------------------------------

    public double/**/time = Double.MAX_VALUE;

    // ----------------------------------------

    public CollisionInfo(Box box1, Box box2) {
      this.box1 = box1;
      this.box2 = box2;
    }

    // ----------------------------------------

    @Override
    public String toString() {
      StringBuffer ret = new StringBuffer();

      ret.append("[ time: ");
      ret.append(time);

      ret.append("\n\tbox1 : ");
      ret.append(box1);

      ret.append("\n\tbox1Side : ");
      ret.append(box1Side);

      ret.append("\n\tbox2 : ");
      ret.append(box2);

      ret.append("\n\tbox2Side : ");
      ret.append(box2Side);

      ret.append("]");

      return ret.toString();
    }
  }

  // --------------------------------------------------------------------------------

  private static class TimeMarkBean {

    public double/**/time;
    public Mark/*  */mark;

    public TimeMarkBean(double time, Mark mark) {
      this.time = time;
      this.mark = mark;
    }

    @Override
    public String toString() {
      return time + ";" + mark;
    }
  }
}
