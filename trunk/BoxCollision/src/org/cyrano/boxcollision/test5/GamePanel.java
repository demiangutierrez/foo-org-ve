package org.cyrano.boxcollision.test5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.boxcollision.base.CollisionDetector.CollisionInfo;
import org.cyrano.boxcollision.base.CollisionDetector.Side;
import org.cyrano.util.Hwh;
import org.cyrano.util.PointInt;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel {

  public static final Color BOX1_COLOR = new Color(255, 0, 0);
  public static final Color BOX2_COLOR = new Color(0, 255, 0);

  private List<Polygon> polList = new ArrayList<Polygon>();

  private Polygon pol1;
  private Polygon pol2;

  private boolean start;

  private Polygon dragPol;

  private int dx;
  private int dy;

  private double grayTimeFactor = 1;

  // --------------------------------------------------------------------------------

  public GamePanel() {
    pol1 = new Polygon();

    pol1.setColor(Color.GREEN);
    pol1.getSrcPointList().add(new PointInt(/* */20,/* */50));
    pol1.getSrcPointList().add(new PointInt(/**/100,/**/150));
    pol1.getSrcPointList().add(new PointInt(/**/150,/* */60));
    pol1.getSrcPointList().add(new PointInt(/**/140,/* */20));
    pol1.getSrcPointList().add(new PointInt(/* */60,/* */20));

    pol1.setSrcX(+200);
    pol1.setSrcY(+200);

    pol1.initTgt();
    //pol1.tgtMove(+200, +200);

    polList.add(pol1);

    pol2 = new Polygon();

    pol2.setColor(Color.RED);
    pol2.getSrcPointList().add(new PointInt(/**/300,/**/150));
    pol2.getSrcPointList().add(new PointInt(/**/380,/**/220));
    pol2.getSrcPointList().add(new PointInt(/**/400,/* */60));
    pol2.getSrcPointList().add(new PointInt(/**/340,/* */60));

    pol2.setSrcX(-200);
    pol2.setSrcY(+200);

    pol2.initTgt();

    //pol2.tgtMove(-200, +200);

    polList.add(pol2);

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

  public void setGrayTimeFactor(double grayTimeFactor) {
    this.grayTimeFactor = grayTimeFactor;

    repaint();
  }

  // --------------------------------------------------------------------------------

  private void drawPol(Graphics2D g2d, Polygon pol, Color color) {

    g2d.setColor(pol.getColor());

    PointInt frstPoint = null;
    PointInt prevPoint = null;

    for (PointInt currPoint : pol.getSrcPointList()) {
      if (prevPoint != null) {
        g2d.drawLine(prevPoint.x, prevPoint.y, currPoint.x, currPoint.y);
      }

      if (frstPoint == null) {
        frstPoint = currPoint;
      }

      prevPoint = currPoint;
    }

    g2d.drawLine(prevPoint.x, prevPoint.y, frstPoint.x, frstPoint.y);

    // Second

    g2d.setColor(Color.CYAN);

    frstPoint = null;
    prevPoint = null;

    for (PointInt currPoint : pol.getTgtPointList()) {
      if (prevPoint != null) {
        g2d.drawLine(prevPoint.x, prevPoint.y, currPoint.x, currPoint.y);
      }

      if (frstPoint == null) {
        frstPoint = currPoint;
      }

      prevPoint = currPoint;
    }

    g2d.drawLine(prevPoint.x, prevPoint.y, frstPoint.x, frstPoint.y);

    //    g2d.setColor(Color.CYAN);
    //    g2d.drawRect((int) box.sx, (int) box.sy, (int) box.bw, (int) box.bh);
    //
    //    g2d.setColor(color);
    //    g2d.drawRect((int) box.cx, (int) box.cy, (int) box.bw, (int) box.bh);
    //
    //    Point beg1 = new Point();
    //    Point end1 = new Point();
    //    Point beg2 = new Point();
    //    Point end2 = new Point();
    //
    //    if (box.cx < box.sx) {
    //      if (box.cy < box.sy) {
    //        beg1.setLocation(box.cx, box.cy + box.bh);
    //        end1.setLocation(box.sx, box.sy + box.bh);
    //
    //        beg2.setLocation(box.cx + box.bw, box.cy);
    //        end2.setLocation(box.sx + box.bw, box.sy);
    //      } else if (box.cy == box.sy) {
    //        beg1.setLocation(box.cx + box.bw, box.cy + box.bh);
    //        end1.setLocation(box.sx, box.sy + box.bh);
    //
    //        beg2.setLocation(box.cx + box.bw, box.cy);
    //        end2.setLocation(box.sx, box.sy);
    //      } else {
    //        beg1.setLocation(box.cx + box.bw, box.cy + box.bh);
    //        end1.setLocation(box.sx + box.bw, box.sy + box.bh);
    //
    //        beg2.setLocation(box.cx, box.cy);
    //        end2.setLocation(box.sx, box.sy);
    //      }
    //      // -------------------------------------------
    //    } else if (box.cx == box.sx) {
    //      if (box.cy < box.sy) {
    //        beg1.setLocation(box.cx, box.cy + box.bh);
    //        end1.setLocation(box.sx, box.sy);
    //
    //        beg2.setLocation(box.cx + box.bw, box.cy + box.bh);
    //        end2.setLocation(box.sx + box.bw, box.sy);
    //      } else if (box.cy == box.sy) {
    //        // NONE
    //      } else {
    //        beg1.setLocation(box.sx, box.sy + box.bh);
    //        end1.setLocation(box.cx, box.cy);
    //
    //        beg2.setLocation(box.sx + box.bw, box.sy + box.bh);
    //        end2.setLocation(box.cx + box.bw, box.cy);
    //      }
    //      // -------------------------------------------
    //    } else {
    //      if (box.cy < box.sy) {
    //        beg1.setLocation(box.sx, box.sy);
    //        end1.setLocation(box.cx, box.cy);
    //
    //        beg2.setLocation(box.sx + box.bw, box.sy + box.bh);
    //        end2.setLocation(box.cx + box.bw, box.cy + box.bh);
    //      } else if (box.cy == box.sy) {
    //        beg1.setLocation(box.sx + box.bw, box.sy);
    //        end1.setLocation(box.cx, box.cy);
    //
    //        beg2.setLocation(box.sx + box.bw, box.sy + box.bh);
    //        end2.setLocation(box.cx, box.cy + box.bh);
    //      } else {
    //        beg1.setLocation(box.cx + box.bw, box.cy);
    //        end1.setLocation(box.sx + box.bw, box.sy);
    //
    //        beg2.setLocation(box.cx, box.cy + box.bh);
    //        end2.setLocation(box.sx, box.sy + box.bh);
    //      }
    //    }
    //
    //    if (beg1 != null && end1 != null) {
    //      g2d.setColor(Color.WHITE);
    //      g2d.drawLine(beg1.x, beg1.y, end1.x, end1.y);
    //    }
    //    if (beg2 != null && end2 != null) {
    //      g2d.setColor(Color.WHITE);
    //      g2d.drawLine(beg2.x, beg2.y, end2.x, end2.y);
    //    }
  }

  // --------------------------------------------------------------------------------

  private void drawBox(Graphics2D g2d, BoxImpl box, Color color, CollisionInfo ci) {
    int x, y, w, h;

    if (ci.time != Double.MAX_VALUE) {
      x = (int) (box.cx + box.vx * ci.time * grayTimeFactor);
      y = (int) (box.cy + box.vy * ci.time * grayTimeFactor);
    } else {
      x = (int) (box.cx + box.vx * 1 * grayTimeFactor);
      y = (int) (box.cy + box.vy * 1 * grayTimeFactor);
    }

    w = box.bw;
    h = box.bh;

    g2d.setColor(Color.GRAY);
    g2d.drawRect(x, y, w, h);

    x = (int) (box.cx + box.vx * ci.time);
    y = (int) (box.cy + box.vy * ci.time);
    w = box.bw;
    h = box.bh;

    g2d.setColor(color);
    g2d.drawRect(x, y, w, h);

    Side side = null;

    if (box == ci.box1) {
      side = ci.box1Side;
    }

    if (box == ci.box2) {
      side = ci.box2Side;
    }

    if (side != null) {
      g2d.setColor(Color.WHITE);

      switch (side) {
        case LFT :
          g2d.drawLine(x,/* */y,/* */x,/* */y + h);
          break;

        case RGH :
          g2d.drawLine(x + w, y,/* */x + w, y + h);
          break;

        case TOP :
          g2d.drawLine(x,/* */y,/* */x + w, y/**/);
          break;

        case BOT :
          g2d.drawLine(x,/* */y + h, x + w, y + h);
          break;
      }
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

    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    //    CollisionInfo ci = new CollisionInfo(pol1, box2);
    //    CollisionDetector.calcTimeToCollide(ci);

    //    System.err.println(ci);

    drawPol(g2d, pol1, BOX1_COLOR);
    drawPol(g2d, pol2, BOX2_COLOR);

    //    drawBox(g2d, pol1, BOX1_COLOR, ci);
    //    drawBox(g2d, box2, BOX2_COLOR, ci);
  }

  // --------------------------------------------------------------------------------
  // Mouse handling
  // --------------------------------------------------------------------------------

  private void mousePressed(MouseEvent evt) {
    for (Polygon pol : polList) {
      if (pol.srcContains(evt.getPoint())) {
        System.err.println("INSIDE SRC: " + pol);
        dx = evt.getPoint().x - pol.getSrcPointList().get(0).x;
        dy = evt.getPoint().y - pol.getSrcPointList().get(0).y;
        dragPol = pol;
        start = true;
        return;
      }

      if (pol.tgtContains(evt.getPoint())) {
        System.err.println("INSIDE TGT: " + pol);
        dx = evt.getPoint().x - pol.getTgtPointList().get(0).x;
        dy = evt.getPoint().y - pol.getTgtPointList().get(0).y;
        dragPol = pol;
        start = false;
        return;
      }
    }
  }

  // --------------------------------------------------------------------------------

  private void mouseReleased(MouseEvent e) {
    dragPol = null;
  }

  // --------------------------------------------------------------------------------

  private void mouseDragged(MouseEvent evt) {
    if (dragPol == null) {
      return;
    }

    if (start) {
      int nx = evt.getPoint().x - dx;
      int ny = evt.getPoint().y - dy;

      int ndx = nx - dragPol.getSrcPointList().get(0).x;
      int ndy = ny - dragPol.getSrcPointList().get(0).y;

      dragPol.srcMove(ndx, ndy);

      //      dragPol.cx = evt.getPoint().x - dx;
      //      dragPol.cy = evt.getPoint().y - dy;
    } else {
      int nx = evt.getPoint().x - dx;
      int ny = evt.getPoint().y - dy;

      int ndx = nx - dragPol.getTgtPointList().get(0).x;
      int ndy = ny - dragPol.getTgtPointList().get(0).y;

      dragPol.tgtMove(ndx, ndy);

      //      dragPol.sx = evt.getPoint().x - dx;
      //      dragPol.sy = evt.getPoint().y - dy;
    }

    //    dragPol.calcV();

    repaint();
  }
}
