package org.cyrano.jogl.quadtree;

import java.awt.Color;
import java.awt.Graphics2D;

import org.cyrano.util.MathUtil;
import org.cyrano.util.PointDbl;

public class QuadNode {

  private double minX;
  private double minY;
  private double maxX;
  private double maxY;

  private PointDbl curPointDbl;

  private QuadNode nw;
  private QuadNode ne;
  private QuadNode sw;
  private QuadNode se;
  
  private boolean hasChild;

  // --------------------------------------------------------------------------------

  public QuadNode(double minX, double minY, double maxX, double maxY) {
    this.minX = minX;
    this.minY = minY;
    this.maxX = maxX;
    this.maxY = maxY;
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

  public void insert(PointDbl newPointDbl) {
    if (newPointDbl == null) {
      return;
    }
    
    if (!contains(newPointDbl)) {
      return;
    }

    if (curPointDbl == null && !hasChild) {
      curPointDbl = newPointDbl;
      return;
    }
    


    if (!hasChild) {
      nw = new QuadNode(minX(), minY(), midX(), midY());
      ne = new QuadNode(midX(), minY(), maxX(), midY());

      sw = new QuadNode(minX(), midY(), midX(), maxY());
      se = new QuadNode(midX(), midY(), maxX(), maxY());

      nw.insert(curPointDbl);
      ne.insert(curPointDbl);
      sw.insert(curPointDbl);
      se.insert(curPointDbl);
      
      // This has child variable (and it's children by the way) sucks
      hasChild = true;
    }

    nw.insert(newPointDbl);
    ne.insert(newPointDbl);
    sw.insert(newPointDbl);
    se.insert(newPointDbl);

    curPointDbl = null;
  }

  // --------------------------------------------------------------------------------

  public void draw(Graphics2D g2d) {
    g2d.setColor(Color.RED);

    g2d.drawRect((int) minX(), (int) minY(), (int) getW(), (int) getH());

    if (nw != null) {
      nw.draw(g2d);
      ne.draw(g2d);
      sw.draw(g2d);
      se.draw(g2d);
    }
  }
}
