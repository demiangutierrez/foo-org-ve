package org.cyrano.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.cyrano.graph.Edge;
import org.cyrano.graph.Graph;
import org.cyrano.graph.Node;

public class GameMap {

  private Graph graph = new Graph();

  public Player player0;

  private Node src;
  private Node tgt;

  //  private List<Node> path;

  public BufferedImage map;

  private boolean drawGraph = true;

  // ----------------------------------------

  public GameMap() {
    try {
      map = ImageIO.read(new File("map.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    player0 = new Player();
    Animator.getInstance().addSprite(player0);
  }

  // ----------------------------------------

  public Node getNearest(Point p) {
    Node ret = null;

    double maxD = Double.MAX_VALUE;
    double curD = 0;

    for (Node node : graph.getNodeMap().values()) {
      curD = p.distance(node.getCoords());

      if (curD < maxD) {
        maxD = curD;
        ret = node;
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics2D g2dPanel, int w, int h, Image img) {
    //    BufferedImage back = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = (Graphics2D) img.getGraphics();

    int midX = w / 2;
    int midY = h / 2;

    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, w, h);

    AffineTransform curr = AffineTransform.getTranslateInstance( //
        midX - player0.getCoords().x, midY - player0.getCoords().y);
    AffineTransform prev = g2d.getTransform();

    g2d.setTransform(curr);

    // ----------------------------------------

    if (map != null) {
      g2d.drawImage(map, +20, +20, null);
    }

    // ----------------------------------------

    List<Node> path = player0.getWalkPath();

    Set<Edge> paintedEdgeSet = new HashSet<Edge>();

    if (drawGraph) {
      for (Node node : graph.getNodeMap().values()) {
        for (Edge edge : node.getEdgeSet()) {
          if (!paintedEdgeSet.contains(edge)) { // XXX: Fixme, this is ugly
            edge.paint(g2d, //
                path != null ? //
                    path.contains(edge.getNodeLftRef()) && //
                        path.contains(edge.getNodeRghRef())
                    : false);
            paintedEdgeSet.add(edge);
          }
        }
        node.paint(g2d, path != null //
            ? path.contains(node)
            : false);
      }
    }

    // ----------------------------------------

    player0.paint(g2d);
    //    player1.paint(g2d);
    //    player2.paint(g2d);

    g2d.setTransform(prev);

    g2dPanel.drawImage(img, 0, 0, null);
  }

  public Graph getGraph() {
    return graph;
  }

  public void setGraph(Graph graph) {
    this.graph = graph;
  }

  public Node getSrc() {
    return src;
  }

  public void setSrc(Node src) {
    this.src = src;

    //    player.coords.x = src.getCoords().x;
    //    player.coords.y = src.getCoords().y;
  }

  public Node getTgt() {
    return tgt;
  }

  public void setTgt(Node tgt) {
    this.tgt = tgt;
  }

  public void setPath(List<Node> path) {
    player0.setWalkPath(path);
  }
}
