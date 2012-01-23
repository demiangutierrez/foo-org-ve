package bfs_old;

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

    // DMI: Wtf... DMI has gone crazy!!!
    for (Node curr : graph.getNodeMap().values()) {
      nodeDataByNodeMap.put(curr, new Data());
    }

    Data nd = nodeDataByNodeMap.get(node);
    nd.dist = 0;

    Node curr = node;

    while (true) {
      Data ndCurr = nodeDataByNodeMap.get(curr);

      ndCurr.vist = true;

      for (Edge edge : curr.getEdgeSet()) {
        Node next = edge.getOtherSide(curr);

        Data ndNext = nodeDataByNodeMap.get(next);

        if (ndNext.vist) {
          continue;
        }

        if (ndCurr.dist + edge.getDistance() < ndNext.dist) {
          ndNext.dist = ndCurr.dist + edge.getDistance();

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
          entry.getValue().dist < minData.dist)
          && //
          !entry.getValue().vist) {

        minNode = entry.getKey();
        minData = entry.getValue();
      }
    }

    return minNode;
  }

  // --------------------------------------------------------------------------------

  public List<Node> pathTo(Node curr) {

    List<Node> ret = new ArrayList<Node>();

    while (curr != node) {
      Data nd = nodeDataByNodeMap.get(curr);
      ret.add(0, curr);
      curr = nd.sourceNode;

      if (curr == null) {
        return null; // no path
      }
    }

    ret.add(0, curr);

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void dumpTable() {
    for (Map.Entry<Node, Data> entry : nodeDataByNodeMap.entrySet()) {
      String sourceName = entry.getValue().sourceNode != null //
          ? entry.getValue().sourceNode.getName() //
          : "null";

      System.err.println( //
          entry.getKey().getName() + ";" + //
              entry.getValue().dist + ";" + //
              entry.getValue().vist + ";" + //
              sourceName);
    }
  }

  // --------------------------------------------------------------------------------

  private static class Data {
    public double/* */dist = Double.MAX_VALUE;
    public boolean/**/vist;

    public Node sourceNode;
  }
}
