package org.cyrano.boxcollision.test5;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Demián Gutierrez
 */
public class CollisionDetector {

  private enum Mark {
    BEG, END
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

    // -----------------------------------------------------------------

    TimeMarkBean[] overlay = calcOverlayArray(crashList);

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
  //  }

  private static Point2D calcTimesToPotImpactAxis(AxisProblem axisProblem) {

    Point2D bp1 = new Point2D.Double( //
        Math.min(axisProblem.pBegPol1OX.x, axisProblem.pEndPol1OX.x), //
        Math.max(axisProblem.pBegPol1OX.x, axisProblem.pEndPol1OX.x));

    Point2D bp2 = new Point2D.Double( //
        Math.min(axisProblem.pBegPol2OX.x, axisProblem.pEndPol2OX.x), //
        Math.max(axisProblem.pBegPol2OX.x, axisProblem.pEndPol2OX.x));

    return calcTimesToPotImpact(bp1, bp2, axisProblem.pol1VOX, axisProblem.pol2VOX);
  }

  // --------------------------------------------------------------------------------

  private static Point2D calcTimesToPotImpact( //
      Point2D bp1, Point2D bp2, double ve1, double ve2) {

    double vRel;

    AxisProyP boxYPos = calcBoxPos(bp1, bp2);

    switch (boxYPos) {
      case B1_OO_B2 :
      case B1_B2_OO :
        vRel = ve1 - ve2;
        break;
      case B2_OO_B1 :
      case B2_B1_OO :
        vRel = ve2 - ve1;
        break;
      case B1_B2_B1 :
      case B2_B1_B2 :
        vRel = ve1 - ve2;
        vRel = -Math.abs(vRel);
        break;
      default :
        throw new IllegalStateException();
    }

    double distCrash1 = Math.abs(bp1.getX() - bp2.getY()) - 1;
    double distCrash2 = Math.abs(bp1.getY() - bp2.getX()) - 1;

    double distCrashMin = Math.min(distCrash1, distCrash2);
    double distCrashMax = Math.max(distCrash1, distCrash2);

    if (boxYPos == AxisProyP.B1_B2_B1 || boxYPos == AxisProyP.B2_B1_B2 || //
        boxYPos == AxisProyP.B1_B2_OO || boxYPos == AxisProyP.B2_B1_OO) {
      if (vRel < 0) {
        return new Point2D.Double( //
            0, Math.abs(distCrashMin / vRel));
      }

      if (vRel == 0) {
        return new Point2D.Double( //
            0, Double.MAX_VALUE);
      }

      if (vRel > 0) {
        return new Point2D.Double( //
            0, Math.abs(distCrashMax / vRel));
      }
    }

    if (vRel <= 0) {
      return null;
    }

    return new Point2D.Double( //
        (distCrashMin / vRel), distCrashMax / vRel);
  }

  // --------------------------------------------------------------------------------

  private static TimeMarkBean[] calcOverlayArray(List<Point2D> crashList) {
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

  public static AxisProyP calcBoxPos(Point2D p1, Point2D p2) {
    if (p1.getX() <= p2.getX() && p2.getY() <= p1.getY()) {
      return AxisProyP.B1_B2_B1;
    }

    if (p2.getX() <= p1.getX() && p1.getY() <= p2.getY()) {
      return AxisProyP.B2_B1_B2;
    }

    if (p1.getX() < p2.getX() && p2.getX() < p1.getY()) {
      return AxisProyP.B1_B2_OO;
    }

    if (p2.getX() < p1.getX() && p1.getX() < p2.getY()) {
      return AxisProyP.B2_B1_OO;
    }

    if (p1.getX() < p2.getX()) {
      return AxisProyP.B1_OO_B2;
    }

    if (p1.getX() > p2.getX()) {
      return AxisProyP.B2_OO_B1;
    }

    throw new IllegalStateException();
  }

  // --------------------------------------------------------------------------------

  public static class CollisionInfo {

    public List<AxisProblem> axisList1;
    public Polygon/*       */pol1;

    public List<AxisProblem> axisList2;
    public Polygon/*       */pol2;

    // ----------------------------------------

    public double time = Double.MAX_VALUE;

    // ----------------------------------------

    public CollisionInfo( //
        Polygon pol1, List<AxisProblem> axisList1, //
        Polygon pol2, List<AxisProblem> axisList2) {

      this.pol1 = pol1;
      this.axisList1 = axisList1;

      this.pol2 = pol2;
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

      ret.append("\n\tbox2 : ");
      ret.append(pol2);

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
