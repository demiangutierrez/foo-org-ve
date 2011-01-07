package org.cyrano.gridcollision.base;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GridCollisionDetector {

  private GridCollisionDetector() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static List<GridCollisionInfo> calculateHorPointList(Point beg, Point end, int gridSize) {
    List<GridCollisionInfo> ret = new ArrayList<GridCollisionInfo>();

    // ----------------------------------------
    // y =  m * x  + n
    // x = (y - n) / m
    // ----------------------------------------

    double m = ((double) (end.y - beg.y)) / (end.x - beg.x);
    double n = beg.y - m * beg.x;

    if (beg.x < end.x) {

      int gridX = beg.x / gridSize * gridSize + gridSize; // Yes, int division

      for (int i = gridX; i < end.x; i += gridSize) {
        GridCollisionInfo gci = new GridCollisionInfo();

        gci.px = i;
        gci.py = (int) (m * i + n);

        gci.gx = i / gridSize;
        gci.gy = gci.py / gridSize;

        ret.add(gci);
      }
    } else {
      int gridX = beg.x / gridSize * gridSize; // Yes, int division

      if (gridX == beg.x) {
        gridX -= gridSize; // This is a hack, I can't find a more elegant way to do it in previous line
      }

      for (int i = gridX; i > end.x; i -= gridSize) {
        GridCollisionInfo gci = new GridCollisionInfo();

        gci.px = i;
        gci.py = (int) (m * i + n);

        gci.gx = i / gridSize - 1;
        gci.gy = gci.py / gridSize;

        ret.add(gci);
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static List<GridCollisionInfo> calculateVerPointList(Point beg, Point end, int gridSize) {
    List<GridCollisionInfo> ret = new ArrayList<GridCollisionInfo>();

    // ----------------------------------------
    // y =  m * x  + n
    // x = (y - n) / m
    // ----------------------------------------

    double m = ((double) (end.y - beg.y)) / (end.x - beg.x);
    double n = beg.y - m * beg.x;

    if (beg.y < end.y) {

      int gridY = beg.y / gridSize * gridSize + gridSize; // Yes, int division

      for (int i = gridY; i < end.y; i += gridSize) {
        GridCollisionInfo gci = new GridCollisionInfo();

        if (beg.x != end.x) {
          gci.px = (int) ((i - n) / m);
        } else {
          gci.px = beg.x;
        }

        gci.py = i;

        gci.gx = gci.px / gridSize;
        gci.gy = i / gridSize;

        ret.add(gci);
      }
    } else {
      int gridY = beg.y / gridSize * gridSize; // Yes, int division

      if (gridY == beg.y) {
        gridY -= gridSize; // This is a hack, I can't find a more elegant way to do it in previous line
      }

      for (int i = gridY; i > end.y; i -= gridSize) {
        GridCollisionInfo gci = new GridCollisionInfo();

        if (beg.x != end.x) {
          gci.px = (int) ((i - n) / m);
        } else {
          gci.px = beg.x;
        }

        gci.py = i;

        gci.gx = gci.px / gridSize;
        gci.gy = i / gridSize - 1;

        ret.add(gci);
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static GridCollisionInfo calcHorGridCollisionInfo( //
      double bx, double by, double vx, double vy, int gs) {

    double ex = bx + vx;
    double ey = by + vy;

    // ----------------------------------------
    // y =  m * x  + n
    // x = (y - n) / m
    // ----------------------------------------

    double m = (ey - by) / (ex - bx);
    double n = by - m * bx;

    int gridX = (int) bx / gs * gs; // Yes, int division

    if (bx < ex) {
      gridX += gs;
    } else {
      if (gridX == bx) {
        gridX -= gs;
      }
    }

    GridCollisionInfo gci = new GridCollisionInfo();

    gci.px = gridX;
    gci.py = (int) (m * gridX + n);

    gci.gx = gridX / gs;
    gci.gy = gci.py / gs;

    return gci;
  }
  // --------------------------------------------------------------------------------

  public static GridCollisionInfo calcVerGridCollisionInfo( //
      double bx, double by, double vx, double vy, int gs) {

    double ex = bx + vx;
    double ey = by + vy;

    // ----------------------------------------
    // y =  m * x  + n
    // x = (y - n) / m
    // ----------------------------------------

    double m = (ey - by) / (ex - bx);
    double n = by - m * bx;

    
    
    int gridY = (int) by / gs * gs; // Yes, int division

    if (by < ey) {
      gridY += gs;
    } else {
      if (gridY == by) {
        gridY -= gs;
      }
    }

    GridCollisionInfo gci = new GridCollisionInfo();

    if (bx != ex) {
      gci.px = (int) ((gridY - n) / m);
    } else {
      gci.px = (int) bx;
    }

    gci.py = gridY;

    gci.gx = gci.px / gs;
    gci.gy = gridY / gs - 1;

    
//    if (beg.x != end.x) {
//      gci.px = (int) ((i - n) / m);
//    } else {
//      gci.px = beg.x;
//    }
//
//    gci.py = i;
//
//    gci.gx = gci.px / gridSize;
//    gci.gy = i / gridSize - 1;
    
    
    return gci;
  }

  // --------------------------------------------------------------------------------

  public static class GridCollisionInfo {

    // ----------------------------------------

    public int gx;
    public int gy;

    // ----------------------------------------

    public int px;
    public int py;
  }
}
