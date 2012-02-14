package org.cyrano.space.bsp;

import java.util.ArrayList;
import java.util.List;

import org.cyrano.util.geometry.PointDbl;
import org.cyrano.util.misc.MathUtil;

public class QuadNode {

  private static final int MIN_W = 50;
  private static final int MIN_H = 10;

  // TODO: Should be int and 2 powers initially
  // that has to be checked somewhere!!!
  private double minX;
  private double minY;
  private double maxX;
  private double maxY;

  private List<PointDbl> pointDblList = new ArrayList<PointDbl>();

  private QuadNode parent;

  private QuadNode nw;
  private QuadNode ne;
  private QuadNode sw;
  private QuadNode se;

  // --------------------------------------------------------------------------------

  public QuadNode( //
      QuadNode parent, //
      double minX, double minY, double maxX, double maxY) {

    this.parent = parent;

    this.minX = minX;
    this.minY = minY;
    this.maxX = maxX;
    this.maxY = maxY;
  }

  // --------------------------------------------------------------------------------

  public QuadNode getParent() {
    return parent;
  }

  // --------------------------------------------------------------------------------

  public double getW() {
    return maxX - minX;
  }

  public double getH() {
    return maxY - minY;
  }

  // --------------------------------------------------------------------------------

  public double minX() {
    return minX;
  }

  public double minY() {
    return minY;
  }

  // --------------------------------------------------------------------------------

  public double midX() {
    return (minX + maxX) / 2;
  }

  public double midY() {
    return (minY + maxY) / 2;
  }

  // --------------------------------------------------------------------------------

  public double maxX() {
    return maxX;
  }

  public double maxY() {
    return maxY;
  }

  // --------------------------------------------------------------------------------

  public QuadNode getNw() {
    return nw;
  }

  public QuadNode getNe() {
    return ne;
  }

  public QuadNode getSw() {
    return sw;
  }

  public QuadNode getSe() {
    return se;
  }

  // --------------------------------------------------------------------------------

  private boolean rectContains(PointDbl newPointDbl) {
    return /**/MathUtil.betweenC(minX(), newPointDbl.getDX(), maxX()) && //
        /*   */MathUtil.betweenC(minY(), newPointDbl.getDY(), maxY());
  }

  // --------------------------------------------------------------------------------

  public boolean hasChildren() {
    return //
    /**/nw != null && //
        ne != null && //
        sw != null && //
        se != null;
  }

  // --------------------------------------------------------------------------------

  public void insert(PointDbl newPointDbl) {
    if (newPointDbl == null) {
      throw new IllegalArgumentException("newPointDbl == null");
    }

    if (!rectContains(newPointDbl)) {
      return;
    }

    if (hasChildren()) {
      nw.insert(newPointDbl);
      ne.insert(newPointDbl);
      sw.insert(newPointDbl);
      se.insert(newPointDbl);
      return;
    }

    if (pointDblList.isEmpty()) {
      pointDblList.add(newPointDbl);
      return;
    }

    if (getW() < MIN_W) {
      pointDblList.add(newPointDbl);
      return;
    }

    nw = new QuadNode(this, minX(), minY(), midX(), midY());
    ne = new QuadNode(this, midX(), minY(), maxX(), midY());

    sw = new QuadNode(this, minX(), midY(), midX(), maxY());
    se = new QuadNode(this, midX(), midY(), maxX(), maxY());

    PointDbl curPointDbl = pointDblList.remove(0);

    insert(curPointDbl);
    insert(newPointDbl);
  }

  // --------------------------------------------------------------------------------

  public QuadNode getQuadFor(PointDbl pt) {
    if (!rectContains(pt)) {
      return null;
    }

    if (!hasChildren()) {
      return this;
    }

    QuadNode ret;

    ret = nw.getQuadFor(pt);

    if (ret != null) {
      return ret;
    }

    ret = sw.getQuadFor(pt);

    if (ret != null) {
      return ret;
    }

    ret = ne.getQuadFor(pt);

    if (ret != null) {
      return ret;
    }

    ret = se.getQuadFor(pt);

    if (ret != null) {
      return ret;
    }

    throw new IllegalStateException("not found");
  }

  private enum Dir {
    N, S, E, W, NW, SW, NE, SE
  }

  private void calculateJuJuNorth(QuadNode child, Dir dir, double w) {
    if (dir == null) {
      if (child == nw || child == sw) {
        dir = Dir.W;
      } else if (child == ne || child == se) {
        dir = Dir.E;
      }
    }

    assert (dir != null);

  }

  //  public void calculateJuJuXD() {
  //    color = Color.GREEN;
  //
  //    if (parent != null) {
  //      calculateJuJuNorth(this, null, getW());
  //    }
  //  }

}
