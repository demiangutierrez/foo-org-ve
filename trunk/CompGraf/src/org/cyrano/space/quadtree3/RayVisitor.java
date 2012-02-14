package org.cyrano.space.quadtree3;

import java.util.ArrayList;
import java.util.List;

import org.cyrano.util.geometry.PointDbl;

public class RayVisitor extends Visitor {

  public List<QuadNode> inTheRay = new ArrayList<QuadNode>();

  public Ray ray;

  private double distanceOf(Ray ray, PointDbl p1, PointDbl p2) {
    // x = p0x + s * ux
    // y = p0y + s * uy

    double m = (p2.getDY() - p1.getDY()) / (p2.getDX() - p1.getDX());
    double n = p1.getDY() - m * p1.getDX();

    double s;

    if ((p2.getDX() - p1.getDX()) != 0) {
      s = (m * ray.p0.getX() + n - ray.p0.getY()) / (ray.uy() - m * ray.ux());
    } else {
      s = (p1.getDX() - ray.p0.getX()) / ray.ux();
    }

    double x = ray.calcX(s);
    double y = ray.calcY(s);

    double minX = Math.min(p1.getDX(), p2.getDX());
    double maxX = Math.max(p1.getDX(), p2.getDX());
    double minY = Math.min(p1.getDY(), p2.getDY());
    double maxY = Math.max(p1.getDY(), p2.getDY());

    if (minX != maxX && (x < minX || x > maxX)) {
      s = Double.NaN;
    }

    if (minY != maxY && (y < minY || y > maxY)) {
      s = Double.NaN;
    }

    return s;
  }

  @Override
  public boolean internalVisit(QuadNode qn) {

    PointDbl p1 = new PointDbl();
    PointDbl p2 = new PointDbl();

    double[] vertexArray = { //
    /**/qn.minX(), qn.minY(), //
        qn.maxX(), qn.minY(), //
        qn.maxX(), qn.maxY(), //
        qn.minX(), qn.maxY()};

    for (int i = 0; i < 4; i++) {
      p1.x = vertexArray[2 * i + 0];
      p1.y = vertexArray[2 * i + 1];

      p2.x = vertexArray[2 * ((i + 1) % 4) + 0];
      p2.y = vertexArray[2 * ((i + 1) % 4) + 1];

      double dist = distanceOf(ray, p1, p2);

      if (!Double.isNaN(dist) && dist >= 0) {
        if (!qn.hasChildren()) {
          inTheRay.add(qn);
        }

        return true;
      }
    }

    return false;
  }
}
