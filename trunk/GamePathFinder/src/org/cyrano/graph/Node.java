package org.cyrano.graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import org.cyrano.index.Boundable;
import org.cyrano.util.Hwh;

public class Node implements Boundable {

  private String name;
  private int id;

  private Point coords;

  private Set<Edge> edgeSet = new HashSet<Edge>();

  private Color color;

  private BufferedImage img;

  private static final int NODE_W = 20;
  private static final int NODE_H = 20;

  // ----------------------------------------

  public Node() {
    // Empty
  }

  // ----------------------------------------

  public Node(int id, Point coords, Color color) {
    this.id = id;
    this.coords = coords;
    this.color = color;
  }

  // ----------------------------------------

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  //  public int getId() {
  //    return id;
  //  }
  //
  //  public void setId(int id) {
  //    this.id = id;
  //  }

  // ----------------------------------------

  public Point getCoords() {
    return coords;
  }

  public void setCoords(Point coords) {
    this.coords = coords;
  }

  // ----------------------------------------

  public Set<Edge> getEdgeSet() {
    return edgeSet;
  }

  public void setEdgeSet(Set<Edge> edgeList) {
    this.edgeSet = edgeList;
  }

  // ----------------------------------------

  public void paint(Graphics2D g2d, boolean inPath) {
    if (inPath) {
      g2d.setColor(Color.GREEN.darker());
    } else {
      g2d.setColor(color);
    }

    if (img != null) {
      g2d.drawImage(img, //
          (int) (coords.getX() - Hwh.getW(img) / 2), //
          (int) (coords.getY() - Hwh.getH(img) / 2), //
          null);
    }

    g2d.fillOval( //
        (int) (coords.getX() - NODE_W / 2), //
        (int) (coords.getY() - NODE_H / 2), //
        NODE_W, NODE_H);
  }

  public void paint(Graphics2D g2d) {
    paint(g2d, false);
  }

  // ----------------------------------------

  public Rectangle getBounds() {
    Rectangle ret = new Rectangle( //
        (int) (coords.getX() - NODE_W / 2), //
        (int) (coords.getY() - NODE_H / 2), //
        NODE_W, NODE_H);

    return ret;
  }

  // ----------------------------------------

  public boolean isStatic() {
    return true;
  }

  // ----------------------------------------

  public BufferedImage getImg() {
    return img;
  }

  public void setImg(BufferedImage img) {
    this.img = img;
  }
}
