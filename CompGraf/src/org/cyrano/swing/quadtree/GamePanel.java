package org.cyrano.swing.quadtree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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

  private QuadNode quadNode1;
  private QuadNode quadNode2;

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
  }

  // --------------------------------------------------------------------------------
  // JPanel
  // --------------------------------------------------------------------------------

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  @Override
  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    if (quadNode1 == null) {
      initQuadNode(); // Init here or there will be no w/h
    }

    for (PointDbl pointDbl : pointDblList) {
      g2d.setColor(Color.YELLOW);
      g2d.drawOval(pointDbl.getIX() - 5, pointDbl.getIY() - 5, 10, 10);
    }

    drawQuadNode(quadNode1, g2d, Color.WHITE/**/, new BasicStroke(7));
    drawQuadNode(quadNode2, g2d, Color.RED/*  */, new BasicStroke(3));
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

  private static int msboff(int i) {
    i = Math.abs(i);

    int ret = 0;

    while (i != 0) {
      i >>= 1;
      ret++;
    }

    return ret;
  }

  //  public static void main(String[] args) {
  //
  //    System.err.println(msboff(0)); // 0
  //    System.err.println(msboff(1)); // 1
  //    System.err.println(msboff(2)); // 2
  //    System.err.println(msboff(3)); // 2
  //    System.err.println(msboff(4)); // 3
  //    System.err.println(msboff(-4)); // 3
  //  }

  private void initQuadNode() {
    //    int rootSide = Math.min(Hwh.getW(this), Hwh.getH(this));
    //    rootSide = msboff(rootSide);
    //    rootSide = 1 << rootSide;
    //
    //    quadNode1 = new QuadNode(null, 0, 0, rootSide, rootSide);
    //    quadNode2 = new QuadNode(null, -rootSide / 2, -rootSide / 2, 2 * rootSide, 2 * rootSide);

    quadNode1 = new QuadNode(null, 256, 256, 768, 768);
    quadNode2 = new QuadNode(null, 0, 0, 1024, 1024);

    for (PointDbl pointDbl : pointDblList) {
      quadNode1.insert(pointDbl);
      quadNode2.insert(pointDbl);
    }
  }

  // --------------------------------------------------------------------------------
  // Mouse handling
  // --------------------------------------------------------------------------------

  private void mousePressed(MouseEvent evt) {
    for (PointDbl pointDbl : pointDblList) {
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
