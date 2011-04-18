package org.cyrano.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dijkstra {

  private Map<Node, Data> nodeDataByNodeMap = new HashMap<Node, Data>();
  private Node node;

  // --------------------------------------------------------------------------------

  public Dijkstra(Graph graph, Node node) {
    this.node = node;

    for (Node curr : graph.getNodeMap().values()) {
      nodeDataByNodeMap.put(curr, new Data());
    }

    Data nd = nodeDataByNodeMap.get(node);
    nd.distance = 0;

    Node curr = node;

    while (true) {
      Data ndCurr = nodeDataByNodeMap.get(curr);

      ndCurr.visited = true;

      for (Edge edge : curr.getEdgeSet()) {
        Node next = edge.getOtherSide(curr);

        Data ndNext = nodeDataByNodeMap.get(next);

        if (ndNext.visited) {
          continue;
        }

        if (ndCurr.distance + edge.getDistance() < ndNext.distance) {
          ndNext.distance = ndCurr.distance + edge.getDistance();

          ndNext.sourceNode = curr;
        }

      }

      // dumpTable();

      curr = getMinUnknown();

      if (curr == null) {
        break;
      }
    }
  }

  // --------------------------------------------------------------------------------

  private Node getMinUnknown() {
    Node minNode = null;
    Data minData = null;

    for (Map.Entry<Node, Data> entry : nodeDataByNodeMap.entrySet()) {
      if ((minData == null || //
          entry.getValue().distance < minData.distance)
          && //
          !entry.getValue().visited) {
        minNode = entry.getKey();
        minData = entry.getValue();
      }
    }

    return minNode;
  }

  public List<Node> pathTo(Node curr) {

    //    if (!nd.visited) {
    //      return null;
    //    }

    List<Node> ret = new ArrayList<Node>();

    while (curr != node) {
      Data nd = nodeDataByNodeMap.get(curr);
      ret.add(0, curr);
      curr = nd.sourceNode;

      if (curr == null) {
        return null; // No path
      }
    }
    ret.add(0, curr);

    return ret;
  }

  public void dumpTable() {
    for (Map.Entry<Node, Data> entry : nodeDataByNodeMap.entrySet()) {
      System.err.println(entry.getKey().getName() + ";" + entry.getValue().distance + ";" + entry.getValue().visited
          + ";" + (entry.getValue().sourceNode != null ? entry.getValue().sourceNode.getName() : "null"));

    }
  }

  private static class Data {
    public boolean visited;

    public double distance = Double.MAX_VALUE;

    public Node sourceNode;
  }
}
