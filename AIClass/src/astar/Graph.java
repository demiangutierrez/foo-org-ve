package astar;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyrano.util.misc.Hwh;

public class Graph {

  Map<String, Node> cache = //
  new HashMap<String, Node>();

  private BufferedImage bimg;

  // --------------------------------------------------------------------------------

  public Graph(BufferedImage bimg) {
    this.bimg = bimg;
  }

  // --------------------------------------------------------------------------------

  public void clearCache() {
    cache.clear();
  }

  // --------------------------------------------------------------------------------

  public List<Node> getNextNodes(Node curr) {
    List<Node> ret = new ArrayList<Node>();

    for (int j = -1; j <= +1; j++) {
      for (int i = -1; i <= +1; i++) {
        if (i == 0 && j == 0) {
          continue;
        }

        int x = curr.x + i;
        int y = curr.y + j;

        if (!validSelection(x, y)) {
          continue;
        }

        ret.add(getFromCache(x, y));
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public Node getFromCache(int x, int y) {
    Node ret = cache.get(Node.getId(x, y));

    if (ret == null) {
      cache.put( //
          Node.getId(x, y), ret = new Node(x, y));
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public boolean validSelection(int x, int y) {
    x += Hwh.getW(bimg) / 2;
    y += Hwh.getH(bimg) / 2;

    if (x < 0 || x >= Hwh.getW(bimg)) {
      //System.err.println("out x: " + x);
      return false;
    }

    if (y < 0 || y >= Hwh.getH(bimg)) {
      //System.err.println("out y: " + y);
      return false;
    }

    if (!isFreePixel(x, y)) {
      //System.err.println("out not free");
      return false;
    }

    //System.err.println("OK: " + x + ";" + y);

    return true;
  }

  // --------------------------------------------------------------------------------

  private boolean isFreePixel(int x, int y) {
    int rgb = bimg.getRGB(x, y);

    //System.err.println("RGB: " + rgb);

    // FIX THIS!!!
    //return rgb == -1;
    //return rgb == -65540;
    return rgb == -131076;
    //    return rgb == -131076;

  }

  // --------------------------------------------------------------------------------

  public BufferedImage getBimg() {
    return bimg;
  }
}
