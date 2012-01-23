package bfs_old;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Edge  {

  private Node nodeLftRef;
  private Node nodeRghRef;

  private double distance;

  private static final int STROKE = 4;

  // ----------------------------------------

  public Edge(Node nodeLftRef, Node nodeRghRef) {
    this.nodeLftRef = nodeLftRef;
    this.nodeRghRef = nodeRghRef;

    nodeLftRef.getEdgeSet().add(this);
    nodeRghRef.getEdgeSet().add(this);

    double x1 = nodeLftRef.getCoords().getX();
    double y1 = nodeLftRef.getCoords().getY();

    double x2 = nodeRghRef.getCoords().getX();
    double y2 = nodeRghRef.getCoords().getY();

    distance = Math.sqrt( //
        /**/(x1 - x2) * (x1 - x2) + //
            (y1 - y2) * (y1 - y2));
  }

  // ----------------------------------------

  public Node getNodeLftRef() {
    return nodeLftRef;
  }

  public void setNodeLftRef(Node nodeLftRef) {
    this.nodeLftRef = nodeLftRef;
  }

  // ----------------------------------------

  public Node getNodeRghRef() {
    return nodeRghRef;
  }

  public void setNodeRghRef(Node nodeLftRef) {
    this.nodeRghRef = nodeLftRef;
  }

  // ----------------------------------------

  public double getDistance() {
    return distance;
  }

  // ----------------------------------------

  public Node getOtherSide(Node node) {
    if (node != nodeLftRef && node != nodeRghRef) {
      throw new IllegalArgumentException(node.toString());
    }

    return nodeLftRef == node ? //
        nodeRghRef
        : //
        nodeLftRef;
  }

  // ----------------------------------------

  public boolean isSide(Node node) {
    return node == nodeLftRef || node == nodeRghRef;
  }

  // ----------------------------------------

  public void paint(Graphics2D g2d, boolean inPath) {
    if (inPath) {
      g2d.setColor(Color.GREEN.darker());
    } else {
      g2d.setColor(Color.LIGHT_GRAY);
    }
    g2d.setStroke(new BasicStroke(STROKE));

    g2d.drawLine( //
        (int) nodeLftRef.getCoords().getX(), //
        (int) nodeLftRef.getCoords().getY(), //
        (int) nodeRghRef.getCoords().getX(), //
        (int) nodeRghRef.getCoords().getY());
  }

  public void paint(Graphics2D g2d) {
    paint(g2d, false);
  }

  // ----------------------------------------

  public Rectangle getBounds() {
    int x = (int) Math.min( //
        nodeLftRef.getCoords().getX(), //
        nodeRghRef.getCoords().getX());

    int y = (int) Math.min( //
        nodeLftRef.getCoords().getY(), //
        nodeRghRef.getCoords().getY());

    int w = (int) Math.abs( //
        /**/nodeLftRef.getCoords().getX() - //
            nodeRghRef.getCoords().getX());

    int h = (int) Math.abs( //
        /**/nodeLftRef.getCoords().getY() - //
            nodeRghRef.getCoords().getY());

    Rectangle ret = new Rectangle( //
        x - STROKE, //
        y - STROKE, //
        w + 2 * STROKE, //
        h + 2 * STROKE);

    return ret;
  }

  // ----------------------------------------

  public boolean isStatic() {
    return true;
  }
}
