package org.cyrano.swing.convexhull;

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
import org.cyrano.util.math.ConvexHull;
import org.cyrano.util.misc.Hwh;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel {

  private List<PointDbl> pointDblList = new ArrayList<PointDbl>();

  private PointDbl dragPoint;

  private int dx;
  private int dy;

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

    for (PointDbl pointDbl : pointDblList) {
      g2d.setColor(Color.YELLOW);
      g2d.drawOval((int) (pointDbl.x - 5), (int) (pointDbl.y - 5), 10, 10);
    }

    List<PointDbl> convexHull = //
    ConvexHull.convexHull(pointDblList.toArray(new PointDbl[0]));

    drawPol(g2d, convexHull, Color.CYAN);
  }

  // --------------------------------------------------------------------------------

  private void drawPol(Graphics2D g2d, List<PointDbl> ch, Color color) {
    g2d.setColor(color);

    PointDbl frstPoint = null;
    PointDbl prevPoint = null;

    for (PointDbl currPoint : ch) {
      if (prevPoint != null) {
        g2d.drawLine( //
            (int) prevPoint.x, (int) prevPoint.y, //
            (int) currPoint.x, (int) currPoint.y);
      }

      if (frstPoint == null) {
        frstPoint = currPoint;
      }

      prevPoint = currPoint;
    }

    g2d.drawLine( //
        (int) prevPoint.x, (int) prevPoint.y, //
        (int) frstPoint.x, (int) frstPoint.y);
  }

  // --------------------------------------------------------------------------------
  // Mouse handling
  // --------------------------------------------------------------------------------

  private void mousePressed(MouseEvent evt) {
    for (PointDbl pointDbl : pointDblList) {
      Rectangle r = new Rectangle( //
          (int) (pointDbl.x - 5), (int) (pointDbl.y - 5), 10, 10);

      if (r.contains(evt.getPoint())) {
        dx = (int) (evt.getPoint().x - pointDbl.x);
        dy = (int) (evt.getPoint().y - pointDbl.y);
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

    repaint();
  }
}
