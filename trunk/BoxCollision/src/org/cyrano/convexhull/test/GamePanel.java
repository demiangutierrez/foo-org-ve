package org.cyrano.convexhull.test;

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

import org.cyrano.math.ConvexHull;
import org.cyrano.util.geometry.PointInt;
import org.cyrano.util.misc.Hwh;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel {

  private List<PointInt> pointIntList = new ArrayList<PointInt>();

  private PointInt dragPoint;

  private int dx;
  private int dy;

  // --------------------------------------------------------------------------------

  public GamePanel() {
    pointIntList.add(new PointInt(/**/200, /**/100));
    pointIntList.add(new PointInt(/**/100, /* */50));
    pointIntList.add(new PointInt(/**/200, /* */10));
    pointIntList.add(new PointInt(/*  */5, /**/250));
    pointIntList.add(new PointInt(/* */50, /**/400));
    pointIntList.add(new PointInt(/**/500, /**/400));
    pointIntList.add(new PointInt(/**/100, /**/111));
    pointIntList.add(new PointInt(/**/150, /**/150));

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

    for (PointInt pointInt : pointIntList) {
      g2d.setColor(Color.YELLOW);
      g2d.drawOval(pointInt.x - 5, pointInt.y - 5, 10, 10);
    }

    List<PointInt> convexHull = //
    ConvexHull.convexHull(pointIntList.toArray(new PointInt[0]));

    drawPol(g2d, convexHull, Color.CYAN);
  }

  // --------------------------------------------------------------------------------

  private void drawPol(Graphics2D g2d, List<PointInt> ch, Color color) {
    g2d.setColor(color);

    PointInt frstPoint = null;
    PointInt prevPoint = null;

    for (PointInt currPoint : ch) {
      if (prevPoint != null) {
        g2d.drawLine(prevPoint.x, prevPoint.y, currPoint.x, currPoint.y);
      }

      if (frstPoint == null) {
        frstPoint = currPoint;
      }

      prevPoint = currPoint;
    }

    g2d.drawLine(prevPoint.x, prevPoint.y, frstPoint.x, frstPoint.y);
  }

  // --------------------------------------------------------------------------------
  // Mouse handling
  // --------------------------------------------------------------------------------

  private void mousePressed(MouseEvent evt) {
    for (PointInt pointInt : pointIntList) {
      Rectangle r = new Rectangle( //
          pointInt.x - 5, pointInt.y - 5, 10, 10);

      if (r.contains(evt.getPoint())) {
        dx = evt.getPoint().x - pointInt.x;
        dy = evt.getPoint().y - pointInt.y;
        dragPoint = pointInt;
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
