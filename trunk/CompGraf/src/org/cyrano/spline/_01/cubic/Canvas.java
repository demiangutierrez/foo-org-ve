package org.cyrano.spline._01.cubic;

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

import org.cyrano.spline._01.cubic.Spline.Mode;
import org.cyrano.util.draw.CtrlPoint;
import org.cyrano.util.draw.CtrlPoint.Axis;
import org.cyrano.util.misc.Hwh;

public class Canvas extends JPanel //
    implements
      MouseListener,
      MouseMotionListener {

  private List<CtrlPoint> ctrlPointList = new ArrayList<CtrlPoint>();

  private Mode mode = Mode.CLS;

  private double[][] splineX;
  private double[][] splineY;

  // --------------------------------------------------------------------------------

  private CtrlPoint curr;

  private double dx;
  private double dy;

  // --------------------------------------------------------------------------------

  public Canvas() {
    addMouseListener(this);
    addMouseMotionListener(this);

    CtrlPoint ctrlPoint;

    ctrlPoint = new CtrlPoint();
    ctrlPoint.setX(-100);
    ctrlPoint.setY(0);
    ctrlPointList.add(ctrlPoint);

    ctrlPoint = new CtrlPoint();
    ctrlPoint.setX(-50);
    ctrlPoint.setY(+50);
    ctrlPointList.add(ctrlPoint);

    ctrlPoint = new CtrlPoint();
    ctrlPoint.setX(0);
    ctrlPoint.setY(-50);
    ctrlPointList.add(ctrlPoint);

    ctrlPoint = new CtrlPoint();
    ctrlPoint.setX(+50);
    ctrlPoint.setY(+50);
    ctrlPointList.add(ctrlPoint);

    ctrlPoint = new CtrlPoint();
    ctrlPoint.setX(+100);
    ctrlPoint.setY(0);
    ctrlPointList.add(ctrlPoint);

    splineX = new double[ctrlPointList.size()][4];
    splineY = new double[ctrlPointList.size()][4];

    Spline.solve(ctrlPointList, splineX, mode, Axis.X);
    Spline.solve(ctrlPointList, splineY, mode, Axis.Y);
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

    int segments = mode == Mode.OPN //
        ? ctrlPointList.size() - 1 //
        : ctrlPointList.size();

    for (int i = 0; i < segments; i++) {
      // FIXME: Bug with odd numbers!
      drawPoly(g2d, 20, //
          splineX[i][0], splineX[i][1], splineX[i][2], splineX[i][3], //
          splineY[i][0], splineY[i][1], splineY[i][2], splineY[i][3]);
    }

    g2d.setTransform(prev);
  }

  // --------------------------------------------------------------------------------

  private void drawPoly(Graphics2D g2d, int steps, //
      double ax, double bx, double cx, double dx, //
      double ay, double by, double cy, double dy) {

    double prevX = Double.NaN;
    double prevY = Double.NaN;

    g2d.setColor(Color.WHITE);

    for (double u = 0; u <= 1; u += 1f / steps) {
      double currX = dx * u * u * u + cx * u * u + bx * u + ax;
      double currY = dy * u * u * u + cy * u * u + by * u + ay;

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

    Spline.solve(ctrlPointList, splineX, mode, Axis.X);
    Spline.solve(ctrlPointList, splineY, mode, Axis.Y);

    repaint();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseMoved(MouseEvent evt) {
    // Empty
  }
}
