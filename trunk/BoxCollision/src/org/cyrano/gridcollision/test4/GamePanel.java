package org.cyrano.gridcollision.test4;

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

import org.cyrano.util.Hwh;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel {

  public static final int GRID_SIZE = 40;

  public static final int POINT_RADIUS_BIG = 5;
  public static final int POINT_RADIUS_SML = 2;

  //  private Point ptBeg;
  //  private Point ptEnd;

  private List<BoxImpl> boxList = new ArrayList<BoxImpl>();

  private BoxImpl box1;

  //  public static final Color BOX1_COLOR = new Color(255, 0, 0);
  //  public static final Color BOX2_COLOR = new Color(0, 255, 0);
  //
  //  private List<BoxImpl> boxList = new ArrayList<BoxImpl>();
  //
  //  private BoxImpl box1;
  //  private BoxImpl box2;
  //
  //  private boolean start;
  //
  private boolean start;

  private BoxImpl dragBox;

  private int dx;
  private int dy;

  //  private double grayTimeFactor = 1;

  // --------------------------------------------------------------------------------

  public GamePanel() {
    box1 = new BoxImpl();

    box1.mv = true;
    
    box1.cx = 600;
    box1.cy = 100;
    box1.bw = 200;
    box1.bh = 120;
    box1.sx = 300;
    box1.sy = 100;

    box1.color = Color.GREEN;

    box1.calcV();
    box1.calcP();

    boxList.add(box1);

    // ----------------------------------------

    //    ptBeg = new Point(100, 100);
    //    ptEnd = new Point(400, 200);

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

  private void drawGrid(Graphics2D g2d, Color color) {
    int xc = Hwh.getW(this) / GRID_SIZE + 1;
    int yc = Hwh.getH(this) / GRID_SIZE + 1;

    g2d.setColor(color);

    for (int i = 0; i < xc; i++) {
      g2d.drawLine(i * GRID_SIZE, 0, i * GRID_SIZE, Hwh.getH(this));
    }

    for (int i = 0; i < yc; i++) {
      g2d.drawLine(0, i * GRID_SIZE, Hwh.getW(this), i * GRID_SIZE);
    }
  }

  //  private void drawPointSet(Graphics2D g2d, Point beg, Point end, Color color) {
  //    // y = m*x+n
  //    // x = (y - n) / m
  //    double m = ((double) (end.y - beg.y)) / (end.x - beg.x);
  //    double n = beg.y - m * beg.x;
  //
  //    Point p1 = null;
  //    Point p2 = null;
  //
  //    double y0 = m * 0/*          */+ n;
  //    double yw = m * Hwh.getW(this) + n;
  //
  //    double x0 = (0/*          */- n) / m;
  //    double xh = (Hwh.getH(this) - n) / m;
  //
  //    if (y0 >= 0 && y0 <= Hwh.getH(this)) {
  //      if (p1 == null) {
  //        p1 = new Point(0, (int) y0);
  //      } else {
  //        p2 = new Point(0, (int) y0);
  //      }
  //    }
  //
  //    if (x0 >= 0 && x0 <= Hwh.getW(this)) {
  //      if (p1 == null) {
  //        p1 = new Point((int) x0, 0);
  //      } else {
  //        p2 = new Point((int) x0, 0);
  //      }
  //    }
  //
  //    if (yw >= 0 && yw <= Hwh.getH(this)) {
  //      if (p1 == null) {
  //        p1 = new Point(Hwh.getW(this), (int) yw);
  //      } else {
  //        p2 = new Point(Hwh.getW(this), (int) yw);
  //      }
  //    }
  //
  //    if (xh >= 0 && xh <= Hwh.getW(this)) {
  //      if (p1 == null) {
  //        p1 = new Point((int) xh, Hwh.getH(this));
  //      } else {
  //        p2 = new Point((int) xh, Hwh.getH(this));
  //      }
  //    }
  //
  //    // All was NaN
  //    if (p1 == null || p2 == null) {
  //      p1 = new Point(beg.x, 0);
  //      p2 = new Point(end.x, Hwh.getH(this));
  //    }
  //
  //    g2d.setColor(Color.GRAY);
  //    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
  //
  //    g2d.setColor(Color.GREEN);
  //    g2d.drawLine(beg.x, beg.y, end.x, end.y);
  //
  //    g2d.setColor(color);
  //    g2d.drawOval(beg.x - POINT_RADIUS_BIG, beg.y - POINT_RADIUS_BIG, 2 * POINT_RADIUS_BIG, 2 * POINT_RADIUS_BIG);
  //    g2d.setColor(Color.WHITE);
  //    g2d.drawOval(beg.x - POINT_RADIUS_SML, beg.y - POINT_RADIUS_SML, 2 * POINT_RADIUS_SML, 2 * POINT_RADIUS_SML);
  //
  //    g2d.setColor(Color.CYAN);
  //    g2d.drawOval(end.x - POINT_RADIUS_BIG, end.y - POINT_RADIUS_BIG, 2 * POINT_RADIUS_BIG, 2 * POINT_RADIUS_BIG);
  //    g2d.setColor(Color.WHITE);
  //    g2d.drawOval(end.x - POINT_RADIUS_SML, end.y - POINT_RADIUS_SML, 2 * POINT_RADIUS_SML, 2 * POINT_RADIUS_SML);
  //  }

  // --------------------------------------------------------------------------------
  // JPanel
  // --------------------------------------------------------------------------------

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

    drawGrid(g2d, Color.GRAY);

    //    drawBox(g2d, box1, Color.GREEN);
    box1.draw(g2d);

    //    drawPointSet(g2d, ptBeg, ptEnd, Color.RED);
    //
    //    drawVerPointList(g2d, calculateVerPointList(ptBeg, ptEnd, GRID_SIZE),
    //        calculateHorPointList(ptBeg, ptEnd, GRID_SIZE));
  }

  // --------------------------------------------------------------------------------
  // Mouse handling
  // --------------------------------------------------------------------------------

  //  private void drawVerPointList(Graphics2D g2d, List<GridColInfo> calculateVerPointList,
  //      List<GridColInfo> calculateHorPointList) {
  //
  //    for (GridColInfo gci : calculateVerPointList) {
  //      g2d.setColor(Color.WHITE);
  //      g2d.drawOval(gci.px - POINT_RADIUS_SML, gci.py - POINT_RADIUS_SML, 2 * POINT_RADIUS_SML, 2 * POINT_RADIUS_SML);
  //
  //      int gx = gci.gx * GRID_SIZE + GRID_SIZE / 2;
  //      int gy = gci.gy * GRID_SIZE + GRID_SIZE / 2;
  //
  //      g2d.setColor(Color.BLUE);
  //      g2d.drawLine(gx - POINT_RADIUS_SML, gy, gx + POINT_RADIUS_SML, gy);
  //      g2d.drawLine(gx, gy - POINT_RADIUS_SML, gx, gy + POINT_RADIUS_SML);
  //
  //      g2d.drawLine(gx, gy, gci.px, gci.py);
  //    }
  //
  //    for (GridColInfo gci : calculateHorPointList) {
  //      g2d.setColor(Color.WHITE);
  //      g2d.drawOval(gci.px - POINT_RADIUS_SML, gci.py - POINT_RADIUS_SML, 2 * POINT_RADIUS_SML, 2 * POINT_RADIUS_SML);
  //
  //      int gx = gci.gx * GRID_SIZE + GRID_SIZE / 2;
  //      int gy = gci.gy * GRID_SIZE + GRID_SIZE / 2;
  //
  //      g2d.setColor(Color.YELLOW);
  //      g2d.drawLine(gx - POINT_RADIUS_SML, gy - POINT_RADIUS_SML, gx + POINT_RADIUS_SML, gy + POINT_RADIUS_SML);
  //      g2d.drawLine(gx - POINT_RADIUS_SML, gy + POINT_RADIUS_SML, gx + POINT_RADIUS_SML, gy - POINT_RADIUS_SML);
  //
  //      g2d.drawLine(gx, gy, gci.px, gci.py);
  //    }
  //  }

  //  private void mousePressed(MouseEvent evt) {
  //    Rectangle rectBeg = new Rectangle( //
  //        ptBeg.x - POINT_RADIUS_BIG, ptBeg.y - POINT_RADIUS_BIG, //
  //        2 * POINT_RADIUS_BIG, 2 * POINT_RADIUS_BIG);
  //
  //    Rectangle rectEnd = new Rectangle( //
  //        ptEnd.x - POINT_RADIUS_BIG, ptEnd.y - POINT_RADIUS_BIG, //
  //        2 * POINT_RADIUS_BIG, 2 * POINT_RADIUS_BIG);
  //
  //    if (rectBeg.contains(evt.getPoint())) {
  //      dx = evt.getPoint().x - ptBeg.x;
  //      dy = evt.getPoint().y - ptBeg.y;
  //      ptDrg = ptBeg;
  //      return;
  //    }
  //
  //    if (rectEnd.contains(evt.getPoint())) {
  //      dx = evt.getPoint().x - ptEnd.x;
  //      dy = evt.getPoint().y - ptEnd.y;
  //      ptDrg = ptEnd;
  //      return;
  //    }
  //  }
  //
  //  // --------------------------------------------------------------------------------
  //
  //  private void mouseReleased(MouseEvent e) {
  //    ptDrg = null;
  //  }
  //
  //  // --------------------------------------------------------------------------------
  //
  //  private void mouseDragged(MouseEvent evt) {
  //    if (ptDrg == null) {
  //      return;
  //    }
  //
  //    ptDrg.x = evt.getPoint().x - dx;
  //    ptDrg.y = evt.getPoint().y - dy;
  //
  //    repaint();
  //  }

  // --------------------------------------------------------------------------------
  // Mouse handling
  // --------------------------------------------------------------------------------

  private void mousePressed(MouseEvent evt) {
    for (BoxImpl box : boxList) {
      Rectangle r;

      r = new Rectangle(box.minX(), box.minY(), box.getW(), box.getH());

      if (r.contains(evt.getPoint())) {
        dx = (int) (evt.getPoint().x - box.cx);
        dy = (int) (evt.getPoint().y - box.cy);
        dragBox = box;
        start = true;
        return;
      }

      r = new Rectangle((int)box.sx, (int)box.sy, box.bw, box.bh);

      if (r.contains(evt.getPoint())) {
        dx = (int) (evt.getPoint().x - box.sx);
        dy = (int) (evt.getPoint().y - box.sy);
        dragBox = box;
        start = false;
        return;
      }
    }
  }

  // --------------------------------------------------------------------------------

  private void mouseReleased(MouseEvent e) {
    dragBox = null;
  }

  // --------------------------------------------------------------------------------

  private void mouseDragged(MouseEvent evt) {
    if (dragBox == null) {
      return;
    }

    if (start) {
      dragBox.cx = evt.getPoint().x - dx;
      dragBox.cy = evt.getPoint().y - dy;
      dragBox.calcP();
    } else {
      dragBox.sx = evt.getPoint().x - dx;
      dragBox.sy = evt.getPoint().y - dy;
    }

    dragBox.calcV();

    repaint();
  }

  public void next() {
    System.err.println(box1.minTime);
    box1.updatePos(box1.minTime);
    box1.calcP();
    repaint();
  }
}
