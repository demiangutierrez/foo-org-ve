package org.cyrano.boxcollision.test5;

import java.util.ArrayList;
import java.util.List;

import org.cyrano.util.PointInt;

public class Axis {

  public PointInt p1 = new PointInt();
  public PointInt p2 = new PointInt();

  // This is the unit vector from the origin
  public double dx;
  public double dy;

  public List<PointInt> projectedPointList = new ArrayList<PointInt>();

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

  public void projectPoint(PointInt pointInt) {
    double mod = 20; // Mod is 20, check calculateFromSide

    // - p1.x and - p1.y brings the point to the origin
    double modPointInt = ((pointInt.x - p1.x) * dx + (pointInt.y - p1.y) * dy) / mod;

    PointInt p = new PointInt();

    // + p1.x and + p1.y moves the proyection away from the origin
    p.x = (int) (dx / 20 * modPointInt) + p1.x;
    p.y = (int) (dy / 20 * modPointInt) + p1.y;

    projectedPointList.add(p);
  }
}
