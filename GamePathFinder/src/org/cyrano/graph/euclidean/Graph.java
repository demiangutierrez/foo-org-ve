package org.cyrano.graph.euclidean;

import java.io.PrintWriter;
import java.util.Map;
import java.util.regex.Pattern;

import org.cyrano.graph.euclidean.Dijkstra.Data;

public class Graph {

  public char[][] data;

  public Pair min;
  public Pair max;

  // --------------------------------------------------------------------------------

  //  public Graph(int w, int h) {
  //    data = new char[w][h];
  //  }

  public void setMinMax(Pair min, Pair max) {
    if (min.x >= max.x || min.y >= max.y) {
      throw new IllegalArgumentException();
    }

    this.min = min;
    this.max = max;
  }

  // --------------------------------------------------------------------------------

  public Node getNodeById(String id) {
    Pattern p = Pattern.compile(":");

    String[] coords = p.split(id);

    if (coords.length != 2) {
      throw new IllegalArgumentException(id);
    }

    int x;
    int y;

    try {
      x = Integer.parseInt(coords[0]);
      y = Integer.parseInt(coords[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(id, e);
    }

    if (x < min.x && x > max.x) {
      return null;
    }

    if (y < min.y && y > max.y) {
      return null;
    }

    return new Node(this, x, y);
  }

  // --------------------------------------------------------------------------------

  public void print(PrintWriter pw) {
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        pw.print(data[i][j]);
      }

      pw.println();
    }
  }

  public Map<Node, Data> getNodeMap() {
    Map<Strint, V>
    return null;
  }
}
