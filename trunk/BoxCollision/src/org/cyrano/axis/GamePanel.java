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

import org.cyrano.util.geometry.PointAbs;
import org.cyrano.util.geometry.PointDbl;
import org.cyrano.util.geometry.PointInt;
import org.cyrano.util.misc.Hwh;
import org.cyrano.util.misc.MathUtil;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel {

  private List<PointDib> pointList = new ArrayList<PointDib>();

  private PointInt pAxis1;
  private PointInt pAxis2;

  private PointInt pProj1;
  private PointInt pProj2;

  private PointInt pVel1;
  private PointInt pVel2;

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

    pVel1 = new PointInt();

    pVel1.x = 0;
    pVel1.y = 0;

    //pointList.add(new PointDib(pVel1, Color.MAGENTA));

    pVel2 = new PointInt();

    pVel2.x = 150;
    pVel2.y = 150;

    pointList.add(new PointDib(pVel2, Color.MAGENTA));

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

    new PointDib(pVel1, Color.MAGENTA).paint(g2d);

    g2d.setColor(Color.MAGENTA);
    g2d.setStroke(new BasicStroke(3));
    g2d.drawLine(pVel1.getIX(), pVel1.getIY(), pVel2.getIX(), pVel2.getIY());

    g2d.setColor(Color.YELLOW);
    g2d.drawLine(pAxis1.x, pAxis1.y, pAxis2.x, pAxis2.y);

    g2d.setColor(Color.RED);
    g2d.setStroke(new BasicStroke(3));
    g2d.drawLine(pProj1.x, pProj1.y, pProj2.x, pProj2.y);

    axis = new Axis();
    axis.initFromSideNor(pAxis1, pAxis2);
    axis.paint(g2d, axis.pSrc, 50, Color.YELLOW);

    PointDbl pVelResXY2 = new PointDbl();
    axis.calcVectorProjectionOX(pVel2, pVelResXY2);
    new PointDib(pVelResXY2, Color.GREEN).paint(g2d);

    //    PointDbl pVelResXY1 = new PointDbl();
    //    axis.calcProjectionXY(pProj1, pVelResXY1);
    //    new PointDib(pVelResXY1, Color.GREEN).paint(g2d);

    PointDbl pProjResXY1 = new PointDbl();
    axis.calcPointProjectionXY(pProj1, pProjResXY1);
    new PointDib(pProjResXY1, Color.GREEN).paint(g2d);

    PointDbl pProjResXY2 = new PointDbl();
    axis.calcPointProjectionXY(pProj2, pProjResXY2);
    new PointDib(pProjResXY2, Color.GREEN).paint(g2d);

    PointDbl pProjResOX1 = new PointDbl();
    axis.calcPointProjectionOX(pProj1, pProjResOX1);
    new PointDib(pProjResOX1, Color.CYAN).paint(g2d);

    PointDbl pProjResOX2 = new PointDbl();
    axis.calcPointProjectionOX(pProj2, pProjResOX2);
    new PointDib(pProjResOX2, Color.CYAN).paint(g2d);

    g2d.setColor(Color.CYAN);
    g2d.setStroke(new BasicStroke(3));
    g2d.drawLine(pProjResOX1.getIX(), pProjResOX1.getIY(), pProjResOX2.getIX(), pProjResOX2.getIY());

    double d1 = PointAbs.dist(pProjResXY1, pProjResXY2);
    double d2 = PointAbs.dist(pProjResOX1, pProjResOX2);

    if (!MathUtil.betweenO(d1 - 0.00001, d2, d1 + 0.00001)) {
      System.err.println("Opps***********************");
      System.err.println(d1);
      System.err.println(d2);
    }

    g2d.setColor(Color.GREEN);
    g2d.setStroke(new BasicStroke(3));
    g2d.drawLine(pProjResXY1.getIX(), pProjResXY1.getIY(), pProjResXY2.getIX(), pProjResXY2.getIY());

    g2d.setColor(Color.LIGHT_GRAY);

    g2d.setStroke(new BasicStroke(1, //
        BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, //
        1, new float[]{4, 4}, 0));

    g2d.drawLine(pProjResXY1.getIX(), pProjResXY1.getIY(), pProj1.x, pProj1.y);
    g2d.drawLine(pProjResXY2.getIX(), pProjResXY2.getIY(), pProj2.x, pProj2.y);

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
