package org.cyrano.common;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import org.cyrano.graph.Edge;
import org.cyrano.graph.Graph;
import org.cyrano.graph.Node;
import org.cyrano.index.Index;

public class GridCreator {

  private Graph graph;
  private Index index;

  // --------------------------------------------------------------------------------

  public GridCreator() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void cleanMap(BufferedImage mapImg, Graph graph, int mapOffsetX, int mapOffsetY) {
    if (mapImg == null) {
      return;
    }

    Iterator<Node> itt = graph.getNodeMap().values().iterator();

    while (itt.hasNext()) {
      Node node = (Node) itt.next();

      if ((node.getCoords().getX() + mapOffsetX) > mapImg.getWidth() || //
          (node.getCoords().getY() + mapOffsetY) > mapImg.getHeight()) {
        continue;
      }

      if (mapImg.getRGB( //
          (int) node.getCoords().getX() + mapOffsetX, //
          (int) node.getCoords().getY() + mapOffsetY) != -1) {
        itt.remove();

        for (Edge edge : node.getEdgeSet()) {
          Node other = edge.getOtherSide(node);
          other.getEdgeSet().remove(edge);
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void createQua(int netW, int netH, int stepX, int stepY) {
    graph = new Graph();

    graph.setNw(netW);
    graph.setNh(netH);
    graph.setMw(stepX * netW);
    graph.setMh(stepY * netH);
    graph.setDx(stepX);
    graph.setDy(stepY);

    index = new Index( //
        graph.getMw() + 2 * graph.getDx(), //
        graph.getMh() + 2 * graph.getDy(), //
        graph.getDx() * 5, //
        graph.getDy() * 5);

    Node curr;

    Node[] currRow = new Node[netW];
    Node[] prevRow = new Node[netW];
    Node[] swapRow;

    int nodes = 0;
    int edges = 0;

    ColorCalculator colorCalculator = new ColorCalculator();
    colorCalculator.init(netW, netH);

    for (int i = 0; i < netH; i++) {
      for (int j = 0; j < netW; j++) {
        curr = new Node( //
            i * netW + j, //
            new Point(stepX / 2 + stepX * j, stepX / 2 + stepY * i), //
            colorCalculator.calculate(j, i));
        curr.setName(j + ":" + i);

        currRow[j] = curr;

        nodes++;
        graph.getNodeMap().put(curr.getName(), curr);
        index.create(curr);

        // ----------------------------------------
        // Horizontal ones
        // ----------------------------------------

        if (j != 0) {
          new Edge( //
              currRow[j - 1], //
              curr);
          edges++;
        }

        // ----------------------------------------
        // Vertical ones
        // ----------------------------------------

        if (i != 0) {
          new Edge( //
              prevRow[j], //
              curr);
          edges++;

          if (j != 0) {
            new Edge( //
                prevRow[j - 1], //
                curr);
            edges++;
          }

          if (j < netW - 1) {
            new Edge( //
                prevRow[j + 1], //
                curr);
            edges++;
          }
        }
      }
      swapRow = prevRow;
      prevRow = currRow;
      currRow = swapRow;
    }
  }

  // --------------------------------------------------------------------------------

  public Graph getGraph() {
    return graph;
  }

  public Index getIndex() {
    return index;
  }
}
