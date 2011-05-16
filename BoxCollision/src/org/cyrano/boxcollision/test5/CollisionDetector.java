package org.cyrano.boxcollision.test5;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class CollisionDetector {

  private enum Mark {
    BEG, END
  }

  public enum Side {
    LFT, RGH, TOP, BOT
  }

  public enum AxisProyP {
    B1_OO_B2, //
    B1_B2_OO, //
    B2_OO_B1, //
    B2_B1_OO, //
    B1_B2_B1, //
    B2_B1_B2
  }

  // --------------------------------------------------------------------------------

  private CollisionDetector() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static void calcTimeToCollide(CollisionInfo collisionInfo) {

    // -----------------------------------------------------------------
    // [time_to_beg_col, time_to_end_col] n-th axis, null if moving away
    // -----------------------------------------------------------------
    List<Point2D> crashList = new ArrayList<Point2D>();

    for (AxisProblem axis : collisionInfo.axisList1) {
      Point2D crash = calcTimesToPotImpactAxis(axis);

      if (crash == null) {
        collisionInfo.time = Double.MAX_VALUE;
        return;
      }

      crashList.add(crash);
    }

    for (AxisProblem axis : collisionInfo.axisList2) {
      Point2D crash = calcTimesToPotImpactAxis(axis);

      if (crash == null) {
        collisionInfo.time = Double.MAX_VALUE;
        return;
      }

      crashList.add(crash);
    }

    TimeMarkBean[] overlay;

    overlay = calcOverlayArray(crashList);

    if (overlay == null) {
      return;
    }

    double time = Double.MAX_VALUE;

    for (int i = 0; i < overlay.length / 2; i++) {
      if (overlay[i].mark == Mark.END) {
        collisionInfo.time = Double.MAX_VALUE;
        return;
      }
      time = overlay[i].time;
    }

    collisionInfo.time = time;

    //calcCollisionSide(collisionInfo, overlay);
  }

  // --------------------------------------------------------------------------------

  //  private static void calcCollisionSide( //
  //      CollisionInfo collisionInfo, TimeMarkBean[] overlay) {
  //
  //    switch (overlay[1].mark) {
  //      case BEGX :
  //        if (collisionInfo.pol1.minX() < collisionInfo.pol2.minX()) {
  //          collisionInfo.box1Side = Side.RGH;
  //          collisionInfo.box2Side = Side.LFT;
  //        } else {
  //          collisionInfo.box1Side = Side.LFT;
  //          collisionInfo.box2Side = Side.RGH;
  //        }
  //        break;
  //
  //      case BEGY :
  //        if (collisionInfo.pol1.minY() < collisionInfo.pol2.minY()) {
  //          collisionInfo.box1Side = Side.BOT;
  //          collisionInfo.box2Side = Side.TOP;
  //        } else {
  //          collisionInfo.box1Side = Side.TOP;
  //          collisionInfo.box2Side = Side.BOT;
  //        }
  //        break;
  //    }
  //  }

  // --------------------------------------------------------------------------------

  //  private static Point getPts1OverAxis(Axis axis) {
  //
  //    //    return new Point(box.minX(), box.maxX());
  //  }

  // --------------------------------------------------------------------------------

  //  private static Point getBoxOverY(Box box) {
  //    return new Point(box.minY(), box.maxY());
  //  }

  // --------------------------------------------------------------------------------

  //private static Point2D calcTimesToPotImpactX(Box box1, Box box2) {
  //Point bp1 = getBoxOverX(box1);
  //Point bp2 = getBoxOverX(box2);
  //
  //return calcTimesToPotImpact(bp1, bp2, box1.velX(), box2.velX());
  //}

  private static Point2D calcTimesToPotImpactAxis(AxisProblem axisProblem) {
    // XXX: Beware with casts to int
    Point bp1 = new Point(
        Math.min((int) axisProblem.pBegPol1OX.x, (int) axisProblem.pEndPol1OX.x),
        Math.max((int) axisProblem.pBegPol1OX.x, (int) axisProblem.pEndPol1OX.x)
        );
    Point bp2 = new Point(
        Math.min((int) axisProblem.pBegPol2OX.x, (int) axisProblem.pEndPol2OX.x),
        Math.max((int) axisProblem.pBegPol2OX.x, (int) axisProblem.pEndPol2OX.x)
            );

    return calcTimesToPotImpact(bp1, bp2, (int) axisProblem.pol1VOX, (int) axisProblem.pol2VOX);
  }

  // --------------------------------------------------------------------------------

  //  private static Point2D calcTimesToPotImpactY(Box box1, Box box2) {
  //    Point bp1 = getBoxOverY(box1);
  //    Point bp2 = getBoxOverY(box2);
  //
  //    return calcTimesToPotImpact(bp1, bp2, box1.velY(), box2.velY());
  //  }

  // --------------------------------------------------------------------------------

  private static Point2D calcTimesToPotImpact( //
      Point bp1, Point bp2, int ve1, int ve2) {

    int vRel;

    AxisProyP boxYPos = calcBoxPos(bp1, bp2);

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

    if (boxYPos == AxisProyP.B1_B2_B1 || boxYPos == AxisProyP.B2_B1_B2 || //
        boxYPos == AxisProyP.B1_B2_OO || boxYPos == AxisProyP.B2_B1_OO) {
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

  private static TimeMarkBean[] calcOverlayArray(List<Point2D> crashList) {

    //    if ((crashX.getX() > crashY.getY() || crashX.getY() < crashY.getX())) {
    //      return null;
    //    }

    List<TimeMarkBean> timeMarkBeanList = new ArrayList<TimeMarkBean>();

    for (Point2D crash : crashList) {
      timeMarkBeanList.add(new TimeMarkBean(crash.getX(), Mark.BEG));
      timeMarkBeanList.add(new TimeMarkBean(crash.getY(), Mark.END));
    }

    TimeMarkBean[] ret = timeMarkBeanList.toArray(new TimeMarkBean[0]);

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

  public static AxisProyP calcBoxPos(Point p1, Point p2) {
    if (p1.x <= p2.x && p2.y <= p1.y) {
      return AxisProyP.B1_B2_B1;
    }

    if (p2.x <= p1.x && p1.y <= p2.y) {
      return AxisProyP.B2_B1_B2;
    }

    if (p1.x < p2.x && p2.x < p1.y) {
      return AxisProyP.B1_B2_OO;
    }

    if (p2.x < p1.x && p1.x < p2.y) {
      return AxisProyP.B2_B1_OO;
    }

    if (p1.x < p2.x) {
      return AxisProyP.B1_OO_B2;
    }

    if (p1.x > p2.x) {
      return AxisProyP.B2_OO_B1;
    }

    throw new IllegalStateException();
  }

  // --------------------------------------------------------------------------------

  //  public static AxisProyP calcBoxXPos(Box box1, Box box2) {
  //    return calcBoxPos( //
  //        new Point(box1.minX(), box1.maxX()), //
  //        new Point(box2.minX(), box2.maxX()));
  //  }

  // --------------------------------------------------------------------------------

  //  public static AxisProyP calcBoxYPos(Box box1, Box box2) {
  //    return calcBoxPos( //
  //        new Point(box1.minY(), box1.maxY()), //
  //        new Point(box2.minY(), box2.maxY()));
  //  }

  // --------------------------------------------------------------------------------

  public static class CollisionInfo {

    // ----------------------------------------

    //    public Side/*  */box1Side;
    public Polygon/*   */pol1;
    public List<AxisProblem> axisList1;

    //    public Side/*  */box2Side;
    public Polygon/*   */pol2;
    public List<AxisProblem> axisList2;

    // ----------------------------------------

    public double/**/time = Double.MAX_VALUE;

    // ----------------------------------------

    public CollisionInfo( //
        Polygon box1, List<AxisProblem> axisList1, //
        Polygon box2, List<AxisProblem> axisList2) {

      this.pol1 = box1;
      this.axisList1 = axisList1;

      this.pol2 = box2;
      this.axisList2 = axisList2;
    }

    // ----------------------------------------

    @Override
    public String toString() {
      StringBuffer ret = new StringBuffer();

      ret.append("[ time: ");
      ret.append(time);

      ret.append("\n\tbox1 : ");
      ret.append(pol1);

      //      ret.append("\n\tbox1Side : ");
      //      ret.append(box1Side);

      ret.append("\n\tbox2 : ");
      ret.append(pol2);

      //      ret.append("\n\tbox2Side : ");
      //      ret.append(box2Side);

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
