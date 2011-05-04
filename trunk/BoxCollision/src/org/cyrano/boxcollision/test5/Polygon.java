package org.cyrano.boxcollision.test5;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.linemath.LineMath;
import org.cyrano.util.PointInt;

// This class sucks, two polygons concepts in the same class
public class Polygon {

  private List<PointInt> srcPointList = new ArrayList<PointInt>();
  private List<PointInt> tgtPointList = new ArrayList<PointInt>();

  private Color color;

  private int srcX;

  public int getSrcX() {
    return srcX;
  }

  public void setSrcX(int srcX) {
    this.srcX = srcX;
  }

  public int getSrcY() {
    return srcY;
  }

  public void setSrcY(int srcY) {
    this.srcY = srcY;
  }

  private int srcY;

  // --------------------------------------------------------------------------------

  public Polygon() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public List<PointInt> getSrcPointList() {
    return srcPointList;
  }

  public void setSrcPointList(List<PointInt> srcPointList) {
    this.srcPointList = srcPointList;
  }

  public List<PointInt> getTgtPointList() {
    return tgtPointList;
  }

  public void setTgtPointList(List<PointInt> tgtPointList) {
    this.tgtPointList = tgtPointList;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  private static void move(List<PointInt> pointList, int dx, int dy) {
    for (PointInt point : pointList) {
      point.x += dx;
      point.y += dy;
    }
  }

  public void srcMove(int dx, int dy) {
    move(srcPointList, dx, dy);
  }

  public void tgtMove(int dx, int dy) {
    move(tgtPointList, dx, dy);
  }

  public static boolean contains(List<PointInt> pointList, Point point) {
    // ---------------------------------------------
    // Ray casting algorithm, check:
    // http://en.wikipedia.org/wiki/Point_in_polygon
    // ---------------------------------------------

    PointInt frstPoint = null;
    PointInt prevPoint = null;

    int count = 0;

    for (PointInt currPoint : pointList) {
      if (prevPoint != null) {
        boolean intersect = Line2D.linesIntersect( //
            /* */prevPoint.x,/* */prevPoint.y,/* */currPoint.x,/* */currPoint.y, //
            /*     */point.x,/*     */point.y,/**/+Double.MAX_VALUE,/**/point.y);

        if (intersect) {
          count++;
        }
      }

      if (frstPoint == null) {
        frstPoint = currPoint;
      }

      prevPoint = currPoint;
    }

    boolean intersect = Line2D.linesIntersect( //
        /* */prevPoint.x,/* */prevPoint.y,/* */frstPoint.x,/* */frstPoint.y, //
        /*     */point.x,/*     */point.y,/**/+Double.MAX_VALUE,/**/point.y);

    if (intersect) {
      count++;
    }

    return count % 2 != 0;
  }

  public boolean srcContains(Point point) {
    return contains(srcPointList, point);
  }

  public boolean tgtContains(Point point) {
    return contains(tgtPointList, point);
  }

  public void initTgt() {
    if (!tgtPointList.isEmpty()) {
      throw new IllegalStateException("!tgtPointList.isEmpty()");
    }

    for (PointInt srcPoint : srcPointList) {
      PointInt tgtPoint = new PointInt(srcPoint.x + srcX, srcPoint.y + srcY);
      tgtPointList.add(tgtPoint);
    }
  }

  //  public PointInt[] calculateOneLine(PointInt src, PointInt tgt) {
  //
  //    PointInt frstPoint = null;
  //    PointInt prevPoint = null;
  //
  //    int count = 0;
  //
  //    for (PointInt currPoint : pointList) {
  //      if (prevPoint != null) {
  //      }
  //
  //      if (frstPoint == null) {
  //        frstPoint = currPoint;
  //      }
  //
  //      prevPoint = currPoint;
  //    }
  //
  //    for (PointInt currPoint : pointList) {
  //      if (prevPoint != null) {
  //      }
  //
  //      if (frstPoint == null) {
  //        frstPoint = currPoint;
  //      }
  //
  //      prevPoint = currPoint;
  //    }
  //
  //    return null;
  //  }
  //
  //  public PointInt[] calculateTheFuckingLines() {
  //    PointInt src1;
  //    PointInt tgt1;
  //
  //    PointInt src2;
  //    PointInt tgt2;
  //
  //    for (PointInt srcPointInt : srcPointList) {
  //      for (PointInt tgtPointInt : tgtPointList) {
  //
  //      }
  //    }
  //
  //    return null;
  //  }
}
