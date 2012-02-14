package org.cyrano.space.quadtree2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.util.geometry.PointDbl;
import org.cyrano.util.misc.Hwh;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel {

  private List<Line> lineList = new ArrayList<Line>();

  private PointDbl dragPoint;

  private int dx;
  private int dy;

  private QuadNode quadNode;

  // --------------------------------------------------------------------------------

  public GamePanel() {
//    lineList.add(new Line( //
//        new PointDbl(/**/200, /**/100), //
//        new PointDbl(/**/100, /* */50)));
//    lineList.add(new Line( //
//        new PointDbl(/**/200, /* */10), //
//        new PointDbl(/*  */5, /**/250)));
//    lineList.add(new Line( //
//        new PointDbl(/* */50, /**/400), //
//        new PointDbl(/**/500, /**/400)));
//    lineList.add(new Line( //
//        new PointDbl(/**/100, /**/111), //
//        new PointDbl(/**/150, /**/150)));

    lineList.add(new Line( //
        new PointDbl(/* */50, /**/50), //
        new PointDbl(/**/100, /**/100)));
    lineList.add(new Line( //
        new PointDbl(/**/700, /**/600), //
        new PointDbl(/**/800, /**/700)));
    
    
    // ----------------------------------------

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed/* */(MouseEvent evt) {
        GamePanel.this.mousePressed/* */(evt);
      }

      @Override
      public void mouseReleased/**/(MouseEvent evt) {
        GamePanel.this.mouseReleased/**/(evt);
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged/* */(MouseEvent evt) {
        GamePanel.this.mouseDragged/* */(evt);
      }
    });

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent evt) {
        quadNode = null;
      }
    });
  }

  // --------------------------------------------------------------------------------
  // JPanel
  // --------------------------------------------------------------------------------

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    if (quadNode == null) {
      initQuadNode();
    }

    for (Line line : lineList) {
      g2d.setColor(Color.YELLOW);
      g2d.drawOval(line.p1.getIX() - 5, line.p1.getIY() - 5, 10, 10);
      g2d.drawOval(line.p2.getIX() - 5, line.p2.getIY() - 5, 10, 10);

      g2d.setColor(Color.GREEN);
      g2d.drawLine( //
          line.p1.getIX(), line.p1.getIY(), //
          line.p2.getIX(), line.p2.getIY());
    }

    drawQuadNode(quadNode, g2d, Color.RED, new BasicStroke(3));
  }

  // --------------------------------------------------------------------------------

  public void drawQuadNode(QuadNode quadNode, //
      Graphics2D g2d, Color color, BasicStroke stroke) {

    g2d.setColor(color);
    g2d.setStroke(stroke);

    g2d.drawRect( //
        (int) quadNode.minX(), (int) quadNode.minY(), //
        (int) quadNode.getW(), (int) quadNode.getH());

    if (quadNode.hasChildren()) {
      drawQuadNode(quadNode.getNw(), g2d, color, stroke);
      drawQuadNode(quadNode.getNe(), g2d, color, stroke);
      drawQuadNode(quadNode.getSw(), g2d, color, stroke);
      drawQuadNode(quadNode.getSe(), g2d, color, stroke);
    }
  }

  // --------------------------------------------------------------------------------

  private void initQuadNode() {
    quadNode = new QuadNode(null, 0, 0, Hwh.getW(this), Hwh.getH(this));

    for (Line line : lineList) {
      quadNode.insert(line);
    }
  }

  // --------------------------------------------------------------------------------
  // Mouse handling
  // --------------------------------------------------------------------------------

  private void mousePressed(MouseEvent evt) {
    for (Line line : lineList) {
      for (PointDbl pointDbl : line.getPointDblList()) {
        Rectangle r = new Rectangle( //
            pointDbl.getIX() - 5, pointDbl.getIY() - 5, 10, 10);

        if (r.contains(evt.getPoint())) {
          dx = evt.getPoint().x - pointDbl.getIX();
          dy = evt.getPoint().y - pointDbl.getIY();
          dragPoint = pointDbl;
          return;
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  private void mouseReleased(MouseEvent e) {
    dragPoint = null;
    initQuadNode();
repaint();
  }

  // --------------------------------------------------------------------------------

  private void mouseDragged(MouseEvent evt) {
    if (dragPoint == null) {
      return;
    }

    dragPoint.x = evt.getPoint().x - dx;
    dragPoint.y = evt.getPoint().y - dy;

    repaint();
  }
}
