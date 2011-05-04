package org.cyrano.linemath;

import java.util.ArrayList;
import java.util.List;

import org.cyrano.util.PointInt;

public class LineMath {

  public static List<PointInt> convexHull(PointInt[] X) {
    int N = X.length;
    int p = 0;

    //First find the leftmost point
    for (int i = 1; i < N; i++) {
      if (X[i].x < X[p].x) {
        p = i;
      }
      if (X[i].x == X[p].x) {
        if (X[i].y < X[p].y) {
          p = i;
        }
      }
    }

    int start = p;

    List<PointInt> ret = new ArrayList<PointInt>();

    ret.add(X[p]);

    do {
      int n = -1;
      for (int i = 0; i < N; i++) {

        //Don't go back to the same point you came from
        if (i == p)
          continue;

        //If there is no N yet, set it to i
        if (n == -1)
          n = i;

        int x1 = X[i].x - X[p].x;
        int x2 = X[n].x - X[p].x;
        int y1 = X[i].y - X[p].y;
        int y2 = X[n].y - X[p].y;

        // XXX: I'm not sure of this
        int cross = (x1 * y2) - (y1 * x2);

        if (cross < 0) {
          //As described above, set N=X
          n = i;
        }
      }
      p = n;
      ret.add(X[p]);

    } while (start != p);

    return ret;
  }

  // Based on http://paulbourke.net/geometry/lineline2d/
  // It's not that I can't do the math my self ;-)
  // TODO: Shoud take numeric error in consideration
  public static PointInt intersectionPoint( //
      PointInt p1, PointInt p2, PointInt p3, PointInt p4) {

    int nu1 = (p4.x - p3.x) * (p1.y - p3.y) - (p4.y - p3.y) * (p1.x - p3.x);
    int nu2 = (p2.x - p1.x) * (p1.y - p3.y) - (p2.y - p1.y) * (p1.x - p3.x);

    int den = (p4.y - p3.y) * (p2.x - p1.x) - (p4.x - p3.x) * (p2.y - p1.y);

    // Same
    if (den == 0 && nu1 == 0 && nu2 == 0) {
      return null;
    }

    // Paralel
    if (den == 0) {
      return null;
    }

    PointInt ret = new PointInt();

    int ua = nu1 / den;

    ret.x = p1.x + ua * (p2.x - p1.x);
    ret.y = p1.y + ua * (p2.y - p1.y);

    return ret;
  }
}
