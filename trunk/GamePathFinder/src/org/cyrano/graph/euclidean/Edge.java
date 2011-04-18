package org.cyrano.graph.euclidean;

public class Edge {

  private double distance;
  private final Graph graph;
  private final Node nodeLftRef;
  private final Node nodeRghRef;

  // ----------------------------------------

  public Edge(Graph graph, Node nodeLftRef, Node nodeRghRef) {
    this.graph = graph;

    this.nodeLftRef = nodeLftRef;
    this.nodeRghRef = nodeRghRef;

    double x1 = nodeLftRef.getCoords().x;
    double y1 = nodeLftRef.getCoords().y;

    double x2 = nodeRghRef.getCoords().x;
    double y2 = nodeRghRef.getCoords().y;

    distance = Math.sqrt( //
        /**/(x1 - x2) * (x1 - x2) + //
            (y1 - y2) * (y1 - y2));
  }

  // ----------------------------------------

  public Node getNodeLftRef() {
    return nodeLftRef;
  }

  public void setNodeLftRef(Node nodeLftRef) {
    throw new UnsupportedOperationException();
  }

  // ----------------------------------------

  public Node getNodeRghRef() {
    return nodeRghRef;
  }

  public void setNodeRghRef(Node nodeLftRef) {
    throw new UnsupportedOperationException();
  }

  // ----------------------------------------

  public double getDistance() {
    return distance;
  }

  // ----------------------------------------

  //  public Node getOtherSide(Node node) {
  //    if (node != nodeLftRef && node != nodeRghRef) {
  //      throw new IllegalArgumentException(node.toString());
  //    }
  //
  //    return nodeLftRef == node ? //
  //        nodeRghRef
  //        : //
  //        nodeLftRef;
  //  }

  // ----------------------------------------

  //  public boolean isSide(Node node) {
  //    return node == nodeLftRef || node == nodeRghRef;
  //  }
}
