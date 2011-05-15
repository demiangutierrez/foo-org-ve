package org.cyrano.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.util.Hwh;
import org.cyrano.util.MathUtil;
import org.cyrano.util.PointAbs;
import org.cyrano.util.PointDbl;
import org.cyrano.util.PointInt;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel {

  //  public static final Color BOX1_COLOR = new Color(255, 0, 0);
  //  public static final Color BOX2_COLOR = new Color(0, 255, 0);
  //
  //
  //  private BoxImpl box1;
  //  private BoxImpl box2;
  //
  //  private boolean start;
  //
  //  private BoxImpl dragBox;
  //
  //  private int dx;
  //  private int dy;
  //
  //  private double grayTimeFactor = 1;

  private List<PointDib> pointList = new ArrayList<PointDib>();

  private PointInt pAxis1;
  private PointInt pAxis2;

  private PointInt pProj1;
  private PointInt pProj2;

  private Axis axis;

  private int dx;
  private int dy;

  private PointDib dragPoint;

  // --------------------------------------------------------------------------------

  public GamePanel() {

    pAxis1 = new PointInt();

    pAxis1.x = 100;
    pAxis1.y = 100;

    pointList.add(new PointDib(pAxis1, Color.YELLOW));

    pAxis2 = new PointInt();

    pAxis2.x = 200;
    pAxis2.y = 200;

    pointList.add(new PointDib(pAxis2, Color.YELLOW));

    // ----------------------------------------

    pProj1 = new PointInt();

    pProj1.x = 200;
    pProj1.y = 100;

    pointList.add(new PointDib(pProj1, Color.RED));

    pProj2 = new PointInt();

    pProj2.x = 100;
    pProj2.y = 200;

    pointList.add(new PointDib(pProj2, Color.RED));

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

  private void drawXYAxis(Graphics2D g2d) {
    g2d.setBackground(Color.BLACK);

    g2d.clearRect(-Hwh.getW(this) / 2, -Hwh.getH(this) / 2, //
        Hwh.getW(this), Hwh.getH(this));

    g2d.setColor(Color.WHITE);

    g2d.drawLine(-Hwh.getW(this) / 2, 0, +Hwh.getW(this) / 2, 0);
    g2d.drawLine(0, -Hwh.getH(this) / 2, 0, +Hwh.getH(this) / 2);

    for (int i = 0; i < Hwh.getW(this) / 2; i += 50) {
      g2d.drawLine(+i, -2, +i, 2);
      g2d.drawLine(-i, -2, -i, 2);
    }

    for (int i = 0; i < Hwh.getH(this) / 2; i += 50) {
      g2d.drawLine(-2, +i, 2, +i);
      g2d.drawLine(-2, -i, 2, -i);
    }
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

    AffineTransform prev = g2d.getTransform();

    g2d.translate(Hwh.getW(this) / 2, Hwh.getH(this) / 2);

    drawXYAxis(g2d);

    for (PointDib point : pointList) {
      point.paint(g2d);
    }

    g2d.setColor(Color.YELLOW);
    g2d.drawLine(pAxis1.x, pAxis1.y, pAxis2.x, pAxis2.y);

    g2d.setColor(Color.RED);
    g2d.setStroke(new BasicStroke(3));
    g2d.drawLine(pProj1.x, pProj1.y, pProj2.x, pProj2.y);

    axis = new Axis();
    axis.initFromSideNor(pAxis1, pAxis2);
    axis.paint(g2d, axis.pSrc, 50, Color.YELLOW);

    PointDbl resXY1 = new PointDbl();
    axis.calcProjectionXY(pProj1, resXY1);
    new PointDib(resXY1, Color.GREEN).paint(g2d);

    PointDbl resXY2 = new PointDbl();
    axis.calcProjectionXY(pProj2, resXY2);
    new PointDib(resXY2, Color.GREEN).paint(g2d);

    PointDbl resOX1 = new PointDbl();
    axis.calcProjectionOX(pProj1, resOX1);
    new PointDib(resOX1, Color.CYAN).paint(g2d);

    PointDbl resOX2 = new PointDbl();
    axis.calcProjectionOX(pProj2, resOX2);
    new PointDib(resOX2, Color.CYAN).paint(g2d);

    double d1 = PointAbs.dist(resXY1, resXY2);
    double d2 = PointAbs.dist(resOX1, resOX2);

    if (!MathUtil.betweenO(d1 - 0.00001, d2, d1 + 0.00001)) {
      System.err.println("Opps***********************");
      System.err.println(d1);
      System.err.println(d2);
    }

    g2d.setColor(Color.GREEN);
    g2d.setStroke(new BasicStroke(3));
    g2d.drawLine(resXY1.getIX(), resXY1.getIY(), resXY2.getIX(), resXY2.getIY());

    g2d.setColor(Color.LIGHT_GRAY);

    g2d.setStroke(new BasicStroke(1, //
        BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, //
        1, new float[]{4, 4}, 0));

    g2d.drawLine(resXY1.getIX(), resXY1.getIY(), pProj1.x, pProj1.y);
    g2d.drawLine(resXY2.getIX(), resXY2.getIY(), pProj2.x, pProj2.y);

    g2d.setTransform(prev);
  }

  // --------------------------------------------------------------------------------
  // Mouse handling
  // --------------------------------------------------------------------------------

  private void mousePressed(MouseEvent evt) {
    int x = evt.getPoint().x - Hwh.getW(this) / 2;
    int y = evt.getPoint().y - Hwh.getH(this) / 2;

    for (PointDib point : pointList) {
      if (point.inside(x, y)) {
        dx = x - point.pointAbs.getIX();
        dy = y - point.pointAbs.getIY();
        dragPoint = point;
        break;
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

    int x = evt.getPoint().x - Hwh.getW(this) / 2;
    int y = evt.getPoint().y - Hwh.getH(this) / 2;

    dragPoint.pointAbs.setIX(x - dx);
    dragPoint.pointAbs.setIY(y - dy);

    repaint();
  }
}
