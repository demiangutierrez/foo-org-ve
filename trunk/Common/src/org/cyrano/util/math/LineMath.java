package org.cyrano.util.math;


public class LineMath {


  //  // Based on http://paulbourke.net/geometry/lineline2d/
  //  // It's not that I can't do the math my self ;-)
  //  // TODO: Shoud take numeric error in consideration
  //  public static PointInt intersectionPoint( //
  //      PointInt p1, PointInt p2, PointInt p3, PointInt p4) {
  //
  //    int nu1 = (p4.x - p3.x) * (p1.y - p3.y) - (p4.y - p3.y) * (p1.x - p3.x);
  //    int nu2 = (p2.x - p1.x) * (p1.y - p3.y) - (p2.y - p1.y) * (p1.x - p3.x);
  //
  //    int den = (p4.y - p3.y) * (p2.x - p1.x) - (p4.x - p3.x) * (p2.y - p1.y);
  //
  //    // Same
  //    if (den == 0 && nu1 == 0 && nu2 == 0) {
  //      return null;
  //    }
  //
  //    // Paralel
  //    if (den == 0) {
  //      return null;
  //    }
  //
  //    PointInt ret = new PointInt();
  //
  //    int ua = nu1 / den;
  //
  //    ret.x = p1.x + ua * (p2.x - p1.x);
  //    ret.y = p1.y + ua * (p2.y - p1.y);
  //
  //    return ret;
  //  }
}
