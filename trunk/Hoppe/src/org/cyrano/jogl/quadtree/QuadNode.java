package org.cyrano.jogl.quadtree;

import java.awt.Color;
import java.awt.Graphics2D;

import org.cyrano.util.MathUtil;
import org.cyrano.util.PointDbl;

public class QuadNode {

  private static final int MIN_W = 10;
  private static final int MIN_H = 10;

  private double minX;
  private double minY;
  private double maxX;
  private double maxY;

  private PointDbl curPointDbl;

  private QuadNode parent;

  private QuadNode nw;
  private QuadNode ne;
  private QuadNode sw;
  private QuadNode se;

  private Color color = Color.RED; // Just for testing purposes

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

  private boolean contains(PointDbl newPointDbl) {
    return /**/MathUtil.betweenC(minX(), newPointDbl.getDX(), maxX()) && //
        /*   */MathUtil.betweenC(minY(), newPointDbl.getDY(), maxY());
  }

  // --------------------------------------------------------------------------------

  private boolean hasChildren() {
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

    if (!contains(newPointDbl)) {
      return;
    }

    if (curPointDbl == null && !hasChildren()) {
      curPointDbl = newPointDbl;
      return;
    }

    if (curPointDbl != null && !hasChildren()) {
      nw = new QuadNode(this, minX(), minY(), midX(), midY());
      ne = new QuadNode(this, midX(), minY(), maxX(), midY());

      sw = new QuadNode(this, minX(), midY(), midX(), maxY());
      se = new QuadNode(this, midX(), midY(), maxX(), maxY());

      nw.insert(curPointDbl);
      ne.insert(curPointDbl);
      sw.insert(curPointDbl);
      se.insert(curPointDbl);
    }

    nw.insert(newPointDbl);
    ne.insert(newPointDbl);
    sw.insert(newPointDbl);
    se.insert(newPointDbl);

    curPointDbl = null;
  }

  // --------------------------------------------------------------------------------

  public QuadNode getQuadFor(PointDbl pt) {
    if (!contains(pt)) {
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

  public void calculateJuJuXD() {
    color = Color.GREEN;

    if (parent != null) {
      calculateJuJuNorth(this, null, getW());
    }
  }

  public void draw(Graphics2D g2d) {
    g2d.setColor(color);

    g2d.drawRect((int) minX(), (int) minY(), (int) getW(), (int) getH());

    if (nw != null) {
      nw.draw(g2d);
      ne.draw(g2d);
      sw.draw(g2d);
      se.draw(g2d);
    }
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
