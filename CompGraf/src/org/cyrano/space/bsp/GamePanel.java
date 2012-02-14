package org.cyrano.space.bsp;

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

  private List<PointDbl> pointDblList = new ArrayList<PointDbl>();

  private PointDbl dragPoint;

  private int dx;
  private int dy;

  private QuadNode quadNode;

  private Ray ray;

  // --------------------------------------------------------------------------------

  public GamePanel() {
    pointDblList.add(new PointDbl(/**/200, /**/100));
    pointDblList.add(new PointDbl(/**/100, /* */50));
    pointDblList.add(new PointDbl(/**/200, /* */10));
    pointDblList.add(new PointDbl(/*  */5, /**/250));
    pointDblList.add(new PointDbl(/* */50, /**/400));
    pointDblList.add(new PointDbl(/**/500, /**/400));
    pointDblList.add(new PointDbl(/**/100, /**/111));
    pointDblList.add(new PointDbl(/**/150, /**/150));
    
    pointDblList.add(new PointDbl(/**/220, /**/111));
    pointDblList.add(new PointDbl(/**/170, /**/150));
    pointDblList.add(new PointDbl(/**/220, /**/121));
    pointDblList.add(new PointDbl(/**/170, /**/140));
    pointDblList.add(new PointDbl(/**/220, /**/200));
    pointDblList.add(new PointDbl(/**/170, /**/250));
    pointDblList.add(new PointDbl(/**/420, /**/121));
    pointDblList.add(new PointDbl(/**/470, /**/140));

    ray = new Ray();
    ray.p0.setX(200);
    ray.p0.setY(200);

    ray.pu.setX(300);
    ray.pu.setY(300);

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

    for (PointDbl pointDbl : pointDblList) {
      g2d.setColor(Color.YELLOW);
      g2d.drawOval(pointDbl.getIX() - 5, pointDbl.getIY() - 5, 10, 10);
    }

    ray.draw(g2d);

    drawQuadNode(quadNode, g2d, Color.RED/*  */, new BasicStroke(3));

    RayVisitor rayVisitor = new RayVisitor();
    rayVisitor.ray = ray;
    rayVisitor.visit(quadNode);

    for (QuadNode quadNode : rayVisitor.inTheRay) {
      g2d.setColor(Color.GREEN);
      g2d.setStroke(new BasicStroke(3));

      g2d.drawRect( //
          (int) quadNode.minX(), (int) quadNode.minY(), //
          (int) quadNode.getW(), (int) quadNode.getH());
    }
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

    for (PointDbl pointDbl : pointDblList) {
      quadNode.insert(pointDbl);
    }
  }

  // --------------------------------------------------------------------------------
  // Mouse handling
  // --------------------------------------------------------------------------------

  private void mousePressed(MouseEvent evt) {
    List<PointDbl> allList = new ArrayList<PointDbl>(pointDblList);
    allList.add(ray.p0.getPointDbl());
    allList.add(ray.pu.getPointDbl());

    for (PointDbl pointDbl : allList) {
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

  // --------------------------------------------------------------------------------

  private void mouseReleased(MouseEvent e) {
    dragPoint = null;
  }

  // --------------------------------------------------------------------------------

  private void mouseDragged(MouseEvent evt) {
    if (dragPoint == null) {
      return;
    }

    dragPoint.x = evt.getPoint().x - dx;
    dragPoint.y = evt.getPoint().y - dy;

    initQuadNode();

    repaint();
  }
}
