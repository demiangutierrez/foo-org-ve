package org.cyrano.jogl.morton;

import java.util.ArrayList;
import java.util.List;

import org.cyrano.util.PointAbs;
import org.cyrano.util.PointDbl;

public class PointSet {

  private List<PointAbs> pointAbsList = new ArrayList<PointAbs>();

  private PointAbs minPoint;

  // --------------------------------------------------------------------------------

  public void addPoint(PointAbs point) {
    if (minPoint == null) {
      minPoint = new PointDbl(point.getDX(), point.getDY());
    } else {
      if (point.getDX() < minPoint.getDX()) {
        minPoint.setDX(point.getDX());
      }
      if (point.getDY() < minPoint.getDY()) {
        minPoint.setDY(point.getDY());
      }
    }

    getPointAbsList().add(point);
  }

  // --------------------------------------------------------------------------------

  public List<PointAbs> getPointAbsList() {
    return pointAbsList;
  }

  // --------------------------------------------------------------------------------

  public PointAbs getMinPoint() {
    return minPoint;
  }
}
