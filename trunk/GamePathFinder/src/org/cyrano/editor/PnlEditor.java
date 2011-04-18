package org.cyrano.editor;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JScrollPane;

import org.cyrano.common.GridCreator;
import org.cyrano.graph.Edge;
import org.cyrano.graph.Graph;
import org.cyrano.graph.Node;
import org.cyrano.index.Boundable;
import org.cyrano.index.Index;

public class PnlEditor extends JScrollPane {

  private static final int X_DIST_BETWEEN_NODES = 70;
  private static final int Y_DIST_BETWEEN_NODES = 70;

  // --------------------------------------------------------------------------------

  private ASwingPanel swingPanel;

  // --------------------------------------------------------------------------------

  private Index index;

  private Graph graph;

  // --------------------------------------------------------------------------------

  public PnlEditor() {
    ACanvas canvas = new ACanvas(new ACanvasFactory());
    canvas.setMinX(0);
    canvas.setMinY(0);
    canvas.setMaxX(0);
    canvas.setMaxY(0);

    setViewportView(swingPanel = //
    new ASwingPanel(canvas));

    canvas.addPaintListener(new APaintAdapter() {
      public void posPaint(APaintEvent evt) {
        paintGraph(evt);
      }
    });

    swingPanel.addPanelMouseInteractor(new ForeInteractor());
  }

  // --------------------------------------------------------------------------------

  public Node getNearest(Point p) {
    Node ret = null;

    double maxD = Double.MAX_VALUE;
    double curD = 0;

    List<Boundable> boundableSet = index.getArea( //
        p.x, p.y, false);

    for (Boundable boundable : boundableSet) {
      if (boundable instanceof Node) {
        Node node = (Node) boundable;

        curD = p.distance(node.getCoords());

        if (curD < maxD) {
          maxD = curD;
          ret = node;
        }
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void paintGraph(APaintEvent evt) {
    Graphics2D g2d = evt.getGraphics2D();

    ARect viewRect = evt.getViewRect();

    System.err.println(viewRect);

    // g2d.setColor(Color.WHITE);
    // g2d.fillRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    List<Boundable> paintList = index.getBoundables(viewRect);

    Set<Edge> edgeSet = new HashSet<Edge>();

    for (Boundable boundable : paintList) {
      if (boundable instanceof Node) {
        Node node = (Node) boundable;

        for (Edge edge : node.getEdgeSet()) {
          if (!edgeSet.contains(edge)) {
            edge.paint(g2d);
            edgeSet.add(edge);
          }
        }
      }

      boundable.paint(g2d);
    }
  }

  // --------------------------------------------------------------------------------

  public Graph getGraph() {
    return graph;
  }

  private void setGraph(Graph graph) {
    setPreferredSize/**/(new Dimension(graph.getMw(), graph.getMh()));
    setMinimumSize/*  */(new Dimension(graph.getMw(), graph.getMh()));
    setMaximumSize/*  */(new Dimension(graph.getMw(), graph.getMh()));
    setSize/*         */(new Dimension(graph.getMw(), graph.getMh()));

    this.graph = graph;

    swingPanel.getCanvas().repaintRequest(null);
  }

  // --------------------------------------------------------------------------------

  public void setGraphSize(int x, int y) {
    GridCreator gridCreator = new GridCreator();
    gridCreator.createQua(x, y, //
        X_DIST_BETWEEN_NODES, Y_DIST_BETWEEN_NODES);

    getHorizontalScrollBar().setBlockIncrement( //
        X_DIST_BETWEEN_NODES);
    getVerticalScrollBar().setBlockIncrement( //
        Y_DIST_BETWEEN_NODES);

    graph = gridCreator.getGraph();
    index = gridCreator.getIndex();

    swingPanel.getCanvas().setMaxX(graph.getMw());
    swingPanel.getCanvas().setMaxY(graph.getMh());
    swingPanel.updatePanelSize();

    setGraph(graph);
  }

  // --------------------------------------------------------------------------------

  public int getGraphW() {
    return graph.getNw();
  }

  public int getGraphH() {
    return graph.getNh();
  }
}
