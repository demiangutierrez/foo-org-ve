package org.cyrano.boxcollision.test5;

import org.cyrano.util.PointInt;

public class AxisXXX {

  public PointInt p1 = new PointInt();
  public PointInt p2 = new PointInt();

  // This is the unit vector from the origin
  public double dx;
  public double dy;

  //  public List<PointInt> projectedPointList = new ArrayList<PointInt>();

  public PointInt pBegPol1;
  public PointInt pEndPol1;

  public PointInt pBegPol2;
  public PointInt pEndPol2;
  
  public PointInt normPol1;
  public PointInt normPol2;
  
//  public void normalizeAxis() {
//    normPol1 = new PointInt();
//    
//    normPol1.x = 0;
//    normPol1
//  }

  public void calculateFromSide(PointInt sp1, PointInt sp2) {
    int midx = (sp2.x - sp1.x) / 2;
    int midy = (sp2.y - sp1.y) / 2;

    p1.x = sp1.x + midx;
    p1.y = sp1.y + midy;

    dx = -(sp2.y - sp1.y);
    dy = +(sp2.x - sp1.x);

    // Normalize to 20 pixels (just for good looking)

    double mod = Math.sqrt(dx * dx + dy * dy);

    dx /= mod;
    dx *= 20;

    dy /= mod;
    dy *= 20;

    p2.x = (int) (dx + p1.x);
    p2.y = (int) (dy + p1.y);
  }

  //  public void projectPoint(PointInt pointInt) {
  //    double mod = 20; // Mod is 20, check calculateFromSide
  //
  //    // - p1.x and - p1.y brings the point to the origin
  //    double modPointInt = ((pointInt.x - p1.x) * dx + (pointInt.y - p1.y) * dy) / mod;
  //
  //    PointInt p = new PointInt();
  //
  //    // + p1.x and + p1.y moves the proyection away from the origin
  //    p.x = (int) (dx / 20 * modPointInt) + p1.x;
  //    p.y = (int) (dy / 20 * modPointInt) + p1.y;
  //
  //    projectedPointList.add(p);
  //  }

  public void projectPoly1(Polygon pol) {
    for (PointInt pointInt : pol.getSrcPointList()) {
      //      projectPoint(pointInt);

      double mod = 20; // Mod is 20, check calculateFromSide

      // - p1.x and - p1.y brings the point to the origin
      double modPointInt = ((pointInt.x - p1.x) * dx + (pointInt.y - p1.y) * dy) / mod;

      PointInt p = new PointInt();

      // + p1.x and + p1.y moves the proyection away from the origin
      p.x = (int) (dx / 20 * modPointInt) + p1.x;
      p.y = (int) (dy / 20 * modPointInt) + p1.y;

      if (pBegPol1 == null) {
        pBegPol1 = p;
        continue;
      }

      if (pEndPol1 == null) {
        pEndPol1 = p;
        continue;
      }

      // XXX: There are 3 sqrt here, may be it can be improved (or cached)
      double d0 = PointInt.dist(pBegPol1, pEndPol1);

      double d1 = PointInt.dist(pBegPol1, p);
      double d2 = PointInt.dist(p, pEndPol1);

      if (d0 < d1 && d2 <= d1) {
        pEndPol1 = p;
        continue;
      }

      if (d0 < d2 && d1 <= d2) {
        pBegPol1 = p;
        continue;
      }
    }
  }

  // XXX: Code is repeating, refactor
  public void projectPoly2(Polygon pol) {
    for (PointInt pointInt : pol.getSrcPointList()) {
      //      projectPoint(pointInt);

      double mod = 20; // Mod is 20, check calculateFromSide

      // - p1.x and - p1.y brings the point to the origin
      double modPointInt = ((pointInt.x - p1.x) * dx + (pointInt.y - p1.y) * dy) / mod;

      PointInt p = new PointInt();

      // + p1.x and + p1.y moves the proyection away from the origin
      p.x = (int) (dx / 20 * modPointInt) + p1.x;
      p.y = (int) (dy / 20 * modPointInt) + p1.y;

      if (pBegPol2 == null) {
        pBegPol2 = p;
        continue;
      }

      if (pEndPol2 == null) {
        pEndPol2 = p;
        continue;
      }

      // XXX: There are 3 sqrt here, may be it can be improved (or cached)
      double d0 = PointInt.dist(pBegPol2, pEndPol2);

      double d1 = PointInt.dist(pBegPol2, p);
      double d2 = PointInt.dist(p, pEndPol2);

      if (d0 < d1 && d2 <= d1) {
        pEndPol2 = p;
        continue;
      }

      if (d0 < d2 && d1 <= d2) {
        pBegPol2 = p;
        continue;
      }
    }
  }
}
