package astar;

public class Node implements Comparable<Node> {

  public final int x;
  public final int y;

  public double g;
  public double h;

  public Node prvNd;

  // --------------------------------------------------------------------------------

  public Node(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // --------------------------------------------------------------------------------

  public static String getId(int x, int y) {
    return x + "-" + y;
  }

  public String getId() {
    return getId(x, y);
  }

  // --------------------------------------------------------------------------------

  @Override
  public int compareTo(Node node) {

    double fthis = this.g + this.h;
    double fnode = node.g + node.h;

    if (fthis == fnode) {
      return 0;
    }

    if (fthis < fnode) {
      return -1;
    }

    return 1;

    // won't work because of the int cast
    //return (int) ((this.g + this.h) - (node.g + node.h));
  }

  // --------------------------------------------------------------------------------
  // hashCode / equals
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

    Node oth = (Node) obj;

    if (x != oth.x) {
      return false;
    }

    if (y != oth.y) {
      return false;
    }

    return true;
  }

  // --------------------------------------------------------------------------------

  @Override
  public String toString() {
    return super.toString() + " : " + (g + h);
  }
}
