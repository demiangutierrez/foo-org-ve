package org.cyrano.graph.euclidean;

import java.util.HashSet;
import java.util.Set;

public class Node {

  private Graph g;

  private int x;

  private int y;

  // --------------------------------------------------------------------------------

  public Node(Graph g, int x, int y) {
    this.g = g;
    this.x = x;
    this.y = y;
  }

  // --------------------------------------------------------------------------------

  public String getName() {
    return x + ";" + y;
  }

  public void setName(String name) {
    throw new UnsupportedOperationException();
  }

  // --------------------------------------------------------------------------------

  public Pair getCoords() {
    return new Pair(x, y);
  }

  public void setCoords(Pair coords) {
    throw new UnsupportedOperationException();
  }

  // --------------------------------------------------------------------------------

  public Set<Edge> getEdgeSet() {
    Set<Edge> edgeSet = new HashSet<Edge>();

    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {

        int xLft = x;
        int yLft = y;

        int xRgh = x + i;
        int yRgh = y + j;

        Node nodeRghRef = g.getNodeById(xLft + ":" + yLft);
        Node nodeLftRef = g.getNodeById(xRgh + ":" + yRgh);

        edgeSet.add(new Edge(g, nodeLftRef, nodeRghRef));
      }
    }

    return edgeSet;
  }

  // --------------------------------------------------------------------------------

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + x;
    result = prime * result + y;
    return result;
  }

  // --------------------------------------------------------------------------------

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    Node nod = (Node) obj;

    if (x != nod.x) {
      return false;
    }

    if (y != nod.y) {
      return false;
    }

    return true;
  }
}
