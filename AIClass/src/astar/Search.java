package astar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.cyrano.util.geometry.PointInt;

public class Search {
  
  public static final double D = 3000;

  // --------------------------------------------------------------------------------
  // problem definition
  // --------------------------------------------------------------------------------

  private Graph graph;

  private Node begNd;
  private Node endNd;

  // --------------------------------------------------------------------------------

  private PriorityQueue<Node> pqueue;

  private Set<Node> /*      */closed;
  private Set<Node> /*      */border;

  private int count = 0;

  // --------------------------------------------------------------------------------

  public Search(Graph graph, PointInt begPt, PointInt endPt) {
    this.graph = graph;

    this.begNd = graph.getFromCache(begPt.x, begPt.y);
    this.endNd = graph.getFromCache(endPt.x, endPt.y);

    pqueue = new PriorityQueue<Node>();

    closed = new HashSet<Node>();
    border = new HashSet<Node>();
  }

  // --------------------------------------------------------------------------------

  public void search() {
    if (count > 0) {
      throw new IllegalStateException();
    }

    pqueue.add(begNd);

    while (!pqueue.isEmpty()) {
      Node curNd = pqueue.remove();

      count++;

      if ((count % 1000) == 0) {
        printStats();
      }

      // ----------------------------------------
      // mark as closed
      // ----------------------------------------

      closed.add(curNd);

      // ----------------------------------------
      // solution found
      // ----------------------------------------

      if (curNd.equals(endNd)) {
        return;
      }

      expand(curNd);
    }

    printStats();
  }

  // --------------------------------------------------------------------------------

  public void printStats() {
    System.err.println("********************************************");
    System.err.println("count       : " + count);
    System.err.println("pqueue size : " + pqueue.size());
    System.err.println("closed size : " + closed.size());
    System.err.println("border size : " + border.size());
    System.err.println("total nodes : " + graph.cache.values().size());
  }

  // --------------------------------------------------------------------------------

  private void expand(Node curNd) {
    List<Node> nextNodes = graph.getNextNodes(curNd);

    for (Node nxtNd : nextNodes) {
      if (!closed.contains(nxtNd)) {
        calcGandH(curNd, nxtNd);

        nxtNd.prvNd = curNd;

        if (!border.contains(nxtNd)) {
          pqueue.add(nxtNd);
          border.add(nxtNd);
        }

      } else {
        double g = getG(curNd, nxtNd);
        double h = getH(curNd, nxtNd);
        double f = g + h;

        //        if (f < (nxtNd.g + nxtNd.h)) {
        //          //          System.err.println("Opps!");
        //          calcGandH(curNd, nxtNd);
        //
        //          nxtNd.prvNd = curNd;
        //
        //          border.add(nxtNd);
        //        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  private void calcGandH(Node curNd, Node nxtNd) {
    nxtNd.g = getG(curNd, nxtNd);
    nxtNd.h = getH(curNd, nxtNd);
  }

  // --------------------------------------------------------------------------------

  private double getG(Node curNd, Node nxtNd) {
    double dx = nxtNd.x - curNd.x;
    double dy = nxtNd.y - curNd.y;

    double d = Math.sqrt(dx * dx + dy * dy);

    //d = Math.floor(d * 10) / 10f;

    double h = getH(curNd, nxtNd);

//    if (h > curNd.h) {
//      d *= D * h * h * h * h * h * h * h;
//    }

    // ----------------------------------------
    // use this for A*
    return curNd.g + d;
    // ----------------------------------------

    // ----------------------------------------
    // use this for a breadth-first search
    //    return count;
    // ----------------------------------------

    // ----------------------------------------
    // is this valid? gives better performance
    //    return curNd.g + 1;
    // ----------------------------------------
  }

  // --------------------------------------------------------------------------------

  private double getH(Node curNd, Node nxtNd) {
    double dx = endNd.x - nxtNd.x;
    double dy = endNd.y - nxtNd.y;

    // ----------------------------------------
    // use this (0) for a breadth-first search
    // return 0;
    // ----------------------------------------

    // ----------------------------------------
    // use this for A*
    return Math.sqrt(dx * dx + dy * dy);
    // ----------------------------------------
  }

  // --------------------------------------------------------------------------------

  public List<Node> getPath() {
    List<Node> ret = new ArrayList<Node>();

    Node curNd = endNd;

    while (curNd.prvNd != null) {
      ret.add(curNd);

      curNd = curNd.prvNd;
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public PriorityQueue<Node> getBorder() {
    return pqueue;
  }

  // --------------------------------------------------------------------------------

  public Set<Node> getClosed() {
    return closed;
  }
}
