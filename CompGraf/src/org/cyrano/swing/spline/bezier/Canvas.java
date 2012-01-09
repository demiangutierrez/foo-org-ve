package org.cyrano.swing.spline.bezier;

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

import org.cyrano.swing.spline.bezier.Bezier.Axis;
import org.cyrano.swing.spline.bezier.TriCtrlPoint.PointMode;
import org.cyrano.util.draw.CtrlPoint;
import org.cyrano.util.misc.Hwh;

public class Canvas extends JPanel //
    implements
      MouseListener,
      MouseMotionListener {

  public enum CurveMode {
    OPN, CLS
  }

  // --------------------------------------------------------------------------------

  private List<TriCtrlPoint> ctrlPointList = new ArrayList<TriCtrlPoint>();

  private CurveMode curveMode = CurveMode.CLS;

  // --------------------------------------------------------------------------------

  private CtrlPoint curr;

  private double dx;
  private double dy;

  // --------------------------------------------------------------------------------

  public Canvas() {
    addMouseListener(this);
    addMouseMotionListener(this);

    TriCtrlPoint triCtrlPoint;

    //@begnf
    triCtrlPoint= initPoint(-100,   0, curveMode == CurveMode.CLS, true);
    ctrlPointList.add(triCtrlPoint);

    triCtrlPoint= initPoint( -50, +50, true, true);
    ctrlPointList.add(triCtrlPoint);

    triCtrlPoint= initPoint(   0, -50, true, true);
    ctrlPointList.add(triCtrlPoint);

    triCtrlPoint= initPoint( +50, +50, true, true);
    ctrlPointList.add(triCtrlPoint);

    triCtrlPoint= initPoint(+100,   0, true, curveMode == CurveMode.CLS);
    ctrlPointList.add(triCtrlPoint);
    //@endnf
  }

  // --------------------------------------------------------------------------------

  private TriCtrlPoint initPoint(double x, double y, boolean lft, boolean rgh) {
    TriCtrlPoint triCtrlPoint = new TriCtrlPoint();
    triCtrlPoint.setMode(PointMode.SYN);
    triCtrlPoint.setX(x);
    triCtrlPoint.setY(y);

    if (lft) {
      triCtrlPoint.setLftCtrlPoint(new CtrlPoint());
      triCtrlPoint.getLftCtrlPoint().setColor(Color.YELLOW);
      triCtrlPoint.getLftCtrlPoint().setX(triCtrlPoint.getX() - 10);
      triCtrlPoint.getLftCtrlPoint().setY(triCtrlPoint.getY() - 30);
    }

    if (rgh) {
      triCtrlPoint.setRghCtrlPoint(new CtrlPoint());
      triCtrlPoint.getRghCtrlPoint().setColor(Color.YELLOW);
      triCtrlPoint.getRghCtrlPoint().setX(triCtrlPoint.getX() + 10);
      triCtrlPoint.getRghCtrlPoint().setY(triCtrlPoint.getY() + 30);
    }

    return triCtrlPoint;
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

    TriCtrlPoint prevCtrlPoint = null;

    int count = curveMode == CurveMode.CLS //
        ? ctrlPointList.size() + 1
        : ctrlPointList.size();

    for (int i = 0; i < count; i++) {
      TriCtrlPoint currCtrlPoint = ctrlPointList.get( //
          i % ctrlPointList.size());

      g2d.setColor(Color.GREEN);
      currCtrlPoint.draw(g2d);

      if (prevCtrlPoint != null) {

        g2d.setColor(Color.RED);
        g2d.drawLine( //
            (int) prevCtrlPoint.getX(), (int) prevCtrlPoint.getY(), //
            (int) currCtrlPoint.getX(), (int) currCtrlPoint.getY());

        List<CtrlPoint> bezier = new ArrayList<CtrlPoint>();
        bezier.add(prevCtrlPoint);
        bezier.add(prevCtrlPoint.getRghCtrlPoint());
        bezier.add(currCtrlPoint.getLftCtrlPoint());
        bezier.add(currCtrlPoint);

        drawPoly(g2d, 20, bezier);
      }

      prevCtrlPoint = currCtrlPoint;
    }

    g2d.setTransform(prev);
  }

  // --------------------------------------------------------------------------------

  private void drawPoly(Graphics2D g2d, int steps, List<CtrlPoint> bezier) {
    double prevX = Double.NaN;
    double prevY = Double.NaN;

    g2d.setColor(Color.WHITE);

    for (double u = 0; u <= 1; u += 1f / steps) {
      double currX = Bezier.solve(bezier, Axis.X, u);
      double currY = Bezier.solve(bezier, Axis.Y, u);

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
    if (evt.getClickCount() != 2) {
      return;
    }

    int x = +evt.getPoint().x - Hwh.getW(this) / 2;
    int y = -evt.getPoint().y + Hwh.getH(this) / 2;

    CtrlPoint clicked = null;

    for (CtrlPoint ctrlPoint : ctrlPointList) {
      clicked = ctrlPoint.contains(x, y);

      if (clicked != null) {
        break;
      }
    }

    if (clicked == null || !(clicked instanceof TriCtrlPoint)) {
      return;
    }

    TriCtrlPoint triCtrlPoint = (TriCtrlPoint) clicked;

    switch (triCtrlPoint.getMode()) {
      case IND :
        triCtrlPoint.setMode(PointMode.LCK);
        break;
      case LCK :
        triCtrlPoint.setMode(PointMode.SYN);
        break;
      case SYN :
        triCtrlPoint.setMode(PointMode.IND);
        break;
    }
    
    repaint();
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

    repaint();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseMoved(MouseEvent evt) {
    // Empty
  }
}
