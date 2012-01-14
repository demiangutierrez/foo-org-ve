package org.cyrano.swing.spline.B;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.util.draw.CtrlPoint;
import org.cyrano.util.draw.CtrlPoint.Axis;
import org.cyrano.util.misc.Hwh;

public class Canvas extends JPanel //
    implements
      MouseListener,
      MouseMotionListener {

  private List<CtrlPoint> ctrlPointList = new ArrayList<CtrlPoint>();

  //  private Mode mode = Mode.CLS;

  // --------------------------------------------------------------------------------

  private CtrlPoint curr;

  private double dx;
  private double dy;

  // --------------------------------------------------------------------------------

  public Canvas() {
    addMouseListener(this);
    addMouseMotionListener(this);

    CtrlPoint ctrlPoint;

    // p0 = -60, +50
    ctrlPoint = new CtrlPoint();
    ctrlPoint.setX(-60);
    ctrlPoint.setY(+50);
    ctrlPointList.add(ctrlPoint);

    // p1 = -75, -3
    ctrlPoint = new CtrlPoint();
    ctrlPoint.setX(-75);
    ctrlPoint.setY(-3);
    ctrlPointList.add(ctrlPoint);

    // p2 = -50, -10
    ctrlPoint = new CtrlPoint();
    ctrlPoint.setX(-50);
    ctrlPoint.setY(-10);
    ctrlPointList.add(ctrlPoint);

    // p3 = 0, 0
    ctrlPoint = new CtrlPoint();
    ctrlPoint.setX(0);
    ctrlPoint.setY(0);
    ctrlPointList.add(ctrlPoint);

    // p4 = +50, -10
    ctrlPoint = new CtrlPoint();
    ctrlPoint.setX(+50);
    ctrlPoint.setY(-10);
    ctrlPointList.add(ctrlPoint);

    // p5 = +75, -10
    ctrlPoint = new CtrlPoint();
    ctrlPoint.setX(+75);
    ctrlPoint.setY(-10);
    ctrlPointList.add(ctrlPoint);

    // p6 = +60, +50
    ctrlPoint = new CtrlPoint();
    ctrlPoint.setX(+60);
    ctrlPoint.setY(+50);
    ctrlPointList.add(ctrlPoint);
  }

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

    AffineTransform prev = g2d.getTransform();

    g2d.translate(Hwh.getW(this) / 2, Hwh.getH(this) / 2);
    g2d.scale(1, -1);

    // ----------------------------------------

    CtrlPoint prevCtrlPoint = null;

    for (CtrlPoint currCtrlPoint : ctrlPointList) {
      g2d.setColor(Color.GREEN);
      currCtrlPoint.draw(g2d);

      if (prevCtrlPoint != null) {

        g2d.setColor(Color.RED);
        g2d.drawLine( //
            (int) prevCtrlPoint.getX(), (int) prevCtrlPoint.getY(), //
            (int) currCtrlPoint.getX(), (int) currCtrlPoint.getY());
      }

      prevCtrlPoint = currCtrlPoint;
    }

    // ----------------------------------------

    //    int segments = mode == Mode.OPN //
    //        ? ctrlPointList.size() - 1 //
    //        : ctrlPointList.size();
    //    int segments = ctrlPointList.size() - 1 //
    //
    //    for (int i = 0; i < segments; i++) {
    //      drawPoly(g2d, 20, //
    //          splineX[i][0], splineX[i][1], splineX[i][2], splineX[i][3], //
    //          splineY[i][0], splineY[i][1], splineY[i][2], splineY[i][3]);
    //    }
    drawPoly(g2d, 200);

    g2d.setTransform(prev);
  }

  // --------------------------------------------------------------------------------

  private void drawPoly(Graphics2D g2d, int steps) {

    double prevX = Double.NaN;
    double prevY = Double.NaN;

    g2d.setColor(Color.WHITE);

    for (double u = 0; u <= 1; u += 1f / steps) {
      double currX = BSpline.solve(ctrlPointList, Axis.X, u);
      double currY = BSpline.solve(ctrlPointList, Axis.Y, u);

      if (!Double.isNaN(prevX) && !Double.isNaN(prevY)) {
        g2d.drawLine( //
            (int) prevX, (int) prevY, //
            (int) currX, (int) currY);
      }

      prevX = currX;
      prevY = currY;
    }
  }

  // --------------------------------------------------------------------------------
  // MouseListener
  // --------------------------------------------------------------------------------

  @Override
  public void mouseClicked(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mousePressed(MouseEvent evt) {
    int x = +evt.getPoint().x - Hwh.getW(this) / 2;
    int y = -evt.getPoint().y + Hwh.getH(this) / 2;

    for (CtrlPoint ctrlPoint : ctrlPointList) {
      curr = ctrlPoint.contains(x, y);

      if (curr != null) {
        dx = curr.getX() - x;
        dy = curr.getY() - y;

        return;
      }
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseReleased(MouseEvent evt) {
    curr = null;
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseEntered(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseExited(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // MouseMotionListener
  // --------------------------------------------------------------------------------

  @Override
  public void mouseDragged(MouseEvent evt) {
    if (curr == null) {
      return;
    }

    int x = +evt.getPoint().x - Hwh.getW(this) / 2;
    int y = -evt.getPoint().y + Hwh.getH(this) / 2;

    curr.setX(x + dx);
    curr.setY(y + dy);

    //    Spline.solve(ctrlPointList, splineX, mode, Axis.X);
    //    Spline.solve(ctrlPointList, splineY, mode, Axis.Y);

    repaint();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseMoved(MouseEvent evt) {
    // Empty
  }
}
