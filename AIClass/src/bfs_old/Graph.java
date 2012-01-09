package bfs_old;

import java.util.HashMap;
import java.util.Map;

public class Graph {

  private Map<String, Node> nodeMap = new HashMap<String, Node>();

  // ----------------------------------------

  private int nw;
  private int nh;

  private int mw;
  private int mh;

  private int dx;
  private int dy;

  // ----------------------------------------

  public Graph() {
    // Empty
  }

  // ----------------------------------------

  public Map<String, Node> getNodeMap() {
    return nodeMap;
  }

  public void setNodeMap(Map<String, Node> nodeMap) {
    this.nodeMap = nodeMap;
  }

  // ----------------------------------------

  public int getNw() {
    return nw;
  }

  public void setNw(int nw) {
    this.nw = nw;
  }

  // ----------------------------------------

  public int getNh() {
    return nh;
  }

  public void setNh(int nh) {
    this.nh = nh;
  }

  // ----------------------------------------

  public int getMw() {
    return mw;
  }

  public void setMw(int mw) {
    this.mw = mw;
  }

  // ----------------------------------------

  public int getMh() {
    return mh;
  }

  public void setMh(int mh) {
    this.mh = mh;
  }

  // ----------------------------------------

  public int getDx() {
    return dx;
  }

  public void setDx(int dx) {
    this.dx = dx;
  }

  // ----------------------------------------

  public int getDy() {
    return dy;
  }

  public void setDy(int dy) {
    this.dy = dy;
  }
}
