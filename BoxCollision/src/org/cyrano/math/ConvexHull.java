package org.cyrano.math;

import java.util.ArrayList;
import java.util.List;

import org.cyrano.util.geometry.PointInt;

public class ConvexHull {

  public static List<PointInt> convexHull(PointInt[] pointArray) {

    // ----------------------------------------
    // Get left-top-most point
    // ----------------------------------------

    int prev = 0;

    for (int i = 1; i < pointArray.length; i++) {
      if (pointArray[i].x < pointArray[prev].x) {
        prev = i;
      }

      if (pointArray[i].x == pointArray[prev].x) {
        if (pointArray[i].y < pointArray[prev].y) {
          prev = i;
        }
      }
    }

    // ----------------------------------------
    // Iterate and calculate the hull
    // ----------------------------------------

    int init = prev;

    List<PointInt> ret = new ArrayList<PointInt>();

    ret.add(pointArray[prev]);

    do {
      int next = -1;

      for (int curr = 0; curr < pointArray.length; curr++) {

        if (curr == prev/**/) {
          continue;
        }

        if (next == -1/*  */) {
          next = curr;
          continue;
        }

        // ----------------------------------------
        // Calculates (prev-next) x (prev-curr)
        // ----------------------------------------

        int x1 = pointArray[curr].x - pointArray[prev].x;
        int x2 = pointArray[next].x - pointArray[prev].x;
        int y1 = pointArray[curr].y - pointArray[prev].y;
        int y2 = pointArray[next].y - pointArray[prev].y;

        int cross = (x1 * y2) - (y1 * x2);

        // ----------------------------------------
        // curr is at the lft, rgh or is the same?
        // ----------------------------------------

        if (cross < 0) {
          next = curr;
        }
      }

      prev = next;

      ret.add(pointArray[prev]);
    } while (init != prev);

    return ret;
  }
}
