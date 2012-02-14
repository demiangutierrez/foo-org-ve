package org.cyrano.space.quadtree2;

import java.util.ArrayList;
import java.util.List;

import org.cyrano.util.geometry.PointDbl;
import org.cyrano.util.misc.MathUtil;

public class QuadNode {

  private static final int MIN = 30;

  // XXX: Should be int and 2 powers initially
  // that has to be checked somewhere!!!
  private double minX;
  private double minY;
  private double maxX;
  private double maxY;

  private List<Line> lineList = new ArrayList<Line>();

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

  private boolean rectContains(Line newLine) {
    return QuadNode.rectContains(newLine, minX(), minY(), maxX(), maxY());
  }

  // --------------------------------------------------------------------------------

  private static boolean rectContains(Line newLine, //
      double minX, double minY, double maxX, double maxY) {

    return /**/rectContains(newLine.p1, minX, minY, maxX, maxY) && //
        /*   */rectContains(newLine.p2, minX, minY, maxX, maxY);
  }

  // --------------------------------------------------------------------------------

  private static boolean rectContains(PointDbl newPointDbl, //
      double minX, double minY, double maxX, double maxY) {

    return /**/MathUtil.betweenC(minX, newPointDbl.getDX(), maxX) && //
        /*   */MathUtil.betweenC(minY, newPointDbl.getDY(), maxY);
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

  public boolean hasData() {
    boolean children = false;
    
    
    if (hasChildren()) {
      children |= nw.hasChildren();
      children |= ne.hasChildren();
      children |= sw.hasChildren();
      children |= se.hasChildren();
    }
    
    return !lineList.isEmpty() || children;
  }
  
  public boolean insert(Line newLine) {
    if (newLine == null) {
      throw new IllegalArgumentException("newLine == null");
    }

    if (!rectContains(newLine)) {
      return false;
    }

    if (!hasChildren()) {
      nw = new QuadNode(this, minX(), minY(), midX(), midY());
      ne = new QuadNode(this, midX(), minY(), maxX(), midY());
      sw = new QuadNode(this, minX(), midY(), midX(), maxY());
      se = new QuadNode(this, midX(), midY(), maxX(), maxY());
    }

    boolean inserted = false;

    inserted |= nw.insert(newLine);
    inserted |= ne.insert(newLine);
    inserted |= sw.insert(newLine);
    inserted |= se.insert(newLine);

    if (!inserted) {
      lineList.add(newLine);
    }

    if (hasData()) {
      nw = null;
      ne = null;
      sw = null;
      se = null;
    }

    return true;
  }

  //  public boolean insert(Line newLine) {
  //    if (newLine == null) {
  //      throw new IllegalArgumentException("newLine == null");
  //    }
  //
  //    if (!rectContains(newLine)) {
  //      return false;
  //    }
  //
  //    boolean inserted = false;
  //
  //    if (hasChildren()) {
  //      inserted |= nw.insert(newLine);
  //      inserted |= ne.insert(newLine);
  //      inserted |= sw.insert(newLine);
  //      inserted |= se.insert(newLine);
  //
  //      if (!inserted) {
  //        lineList.add(newLine);
  //      }
  //      return true;
  //    }
  //
  //    if (lineList.isEmpty()) {
  //      lineList.add(newLine);
  //      return true;
  //    }
  //
  //    if (getW() < MIN || getH() < MIN) {
  //      System.err.println("NEVER");
  //      lineList.add(newLine);
  //      return true;
  //    }
  //
  //    boolean insertInChildren = false;
  //
  //    insertInChildren |= QuadNode.rectContains(newLine, minX(), minY(), midX(), midY());
  //    insertInChildren |= QuadNode.rectContains(newLine, midX(), minY(), maxX(), midY());
  //    insertInChildren |= QuadNode.rectContains(newLine, minX(), midY(), midX(), maxY());
  //    insertInChildren |= QuadNode.rectContains(newLine, midX(), midY(), maxX(), maxY());
  //
  //    if (insertInChildren) {
  //      nw = new QuadNode(this, minX(), minY(), midX(), midY());
  //      ne = new QuadNode(this, midX(), minY(), maxX(), midY());
  //      sw = new QuadNode(this, minX(), midY(), midX(), maxY());
  //      se = new QuadNode(this, midX(), midY(), maxX(), maxY());
  //
  //      Line curLine = lineList.remove(0);
  //
  //      insert(curLine);
  //      return insert(newLine);
  //    }
  //
  //    return false;
  //  }

  // --------------------------------------------------------------------------------

  //  public QuadNode getQuadFor(PointDbl pt) {
  //    if (!rectContains(pt)) {
  //      return null;
  //    }
  //
  //    if (!hasChildren()) {
  //      return this;
  //    }
  //
  //    QuadNode ret;
  //
  //    ret = nw.getQuadFor(pt);
  //
  //    if (ret != null) {
  //      return ret;
  //    }
  //
  //    ret = sw.getQuadFor(pt);
  //
  //    if (ret != null) {
  //      return ret;
  //    }
  //
  //    ret = ne.getQuadFor(pt);
  //
  //    if (ret != null) {
  //      return ret;
  //    }
  //
  //    ret = se.getQuadFor(pt);
  //
  //    if (ret != null) {
  //      return ret;
  //    }
  //
  //    throw new IllegalStateException("not found");
  //  }
}
