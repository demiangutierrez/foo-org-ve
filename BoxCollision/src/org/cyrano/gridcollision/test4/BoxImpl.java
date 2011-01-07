package org.cyrano.gridcollision.test4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.boxcollision.base.Box;
import org.cyrano.gridcollision.base.GridCollisionDetector;
import org.cyrano.gridcollision.base.GridCollisionDetector.GridCollisionInfo;

public class BoxImpl implements Box {

  public boolean/**/mv;

  public double/* */cx;
  public double/* */cy;

  public int/* */bw;
  public int/* */bh;

  public double/* */vx;
  public double/* */vy;

  public double/* */sx;
  public double/* */sy;

  public Color color;

  public String id;

  public List<PointBean> pointList = new ArrayList<PointBean>();

  public double minTime;

  public GridCollisionInfo minGci;

  public PointBean minPb;

  // --------------------------------------------------------------------------------

  public BoxImpl() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void calcV() {
    vx = sx - cx;
    vy = sy - cy;
  }

  public void calcP() {
    pointList.clear();

    PointBean pointBean;
    Point pt;

    int tw1 = 1 * getW() / 3;
    int th1 = 1 * getH() / 3;
    int tw2 = 2 * getW() / 3;
    int th2 = 2 * getH() / 3;

    pt = new Point(minX() + tw1, minY());
    pointBean = new PointBean();
    pointBean.beg = pt;
    pointBean.axis = Axis.VER;
    pointList.add(pointBean);

    pt = new Point(minX() + tw2, minY());
    pointBean = new PointBean();
    pointBean.beg = pt;
    pointBean.axis = Axis.VER;
    pointList.add(pointBean);

    pt = new Point(minX() + tw1, maxY());
    pointBean = new PointBean();
    pointBean.beg = pt;
    pointBean.axis = Axis.VER;
    pointList.add(pointBean);

    pt = new Point(minX() + tw2, maxY());
    pointBean = new PointBean();
    pointBean.beg = pt;
    pointBean.axis = Axis.VER;
    pointList.add(pointBean);

    pt = new Point(minX(), minY() + th1);
    pointBean = new PointBean();
    pointBean.beg = pt;
    pointBean.axis = Axis.HOR;
    pointList.add(pointBean);

    pt = new Point(minX(), minY() + th2);
    pointBean = new PointBean();
    pointBean.beg = pt;
    pointBean.axis = Axis.HOR;
    pointList.add(pointBean);

    pt = new Point(maxX(), minY() + th1);
    pointBean = new PointBean();
    pointBean.beg = pt;
    pointBean.axis = Axis.HOR;
    pointList.add(pointBean);

    pt = new Point(maxX(), minY() + th2);
    pointBean = new PointBean();
    pointBean.beg = pt;
    pointBean.axis = Axis.HOR;
    pointList.add(pointBean);
  }

  // --------------------------------------------------------------------------------

  public void updatePos(double dt) {
    if (!mv) {
      return;
    }

    cx = cx + (int) (vx * dt);
    cy = cy + (int) (vy * dt);

    sx = sx + (int) (vx * dt);
    sy = sy + (int) (vy * dt);
  }

  // --------------------------------------------------------------------------------

  @Override
  public int getW() {
    return bw;
  }

  @Override
  public int getH() {
    return bh;
  }

  @Override
  public int minX() {
    return (int) cx;
  }

  @Override
  public int minY() {
    return (int) cy;
  }

  // --------------------------------------------------------------------------------

  @Override
  public int midX() {
    return (int) (cx + bw / 2);
  }

  @Override
  public int midY() {
    return (int) (cy + bh / 2);
  }

  // --------------------------------------------------------------------------------

  @Override
  public int maxX() {
    return (int) (cx + bw);
  }

  @Override
  public int maxY() {
    return (int) (cy + bh);
  }

  // --------------------------------------------------------------------------------

  @Override
  public int velX() {
    return (int) vx;
  }

  @Override
  public int velY() {
    return (int) vy;
  }

  @Override
  public boolean collides(Box box) {
    return true;
  }

  @Override
  public String toString() {
    StringBuffer ret = new StringBuffer();

    ret.append("->mv: ");
    ret.append(mv);

    ret.append(", cx: ");
    ret.append(cx);

    ret.append(", cy: ");
    ret.append(cy);

    ret.append(", bw: ");
    ret.append(bw);

    ret.append(", bh: ");
    ret.append(bh);

    ret.append(", vx: ");
    ret.append(vx);

    ret.append(", vy: ");
    ret.append(vy);

    ret.append(", sx: ");
    ret.append(sx);

    ret.append(", sy: ");
    ret.append(sy);

    return ret.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void draw(Graphics2D g2d) {
    drawBox(g2d, this, color);

    List<GridCollisionInfo> firstGCList = new ArrayList<GridCollisionInfo>();
    List<PointBean> firstPBList = new ArrayList<PointBean>();

    for (PointBean pointBean : pointList) {
      Point beg = pointBean.beg;
      Point end = new Point((int) (beg.x + sx - cx), (int) (beg.y + sy - cy));

      g2d.setColor(Color.GRAY);
      g2d.drawLine(beg.x, beg.y, end.x, end.y);

      //      List<GridCollisionInfo> gciList;
      GridCollisionInfo gciList;

      if (pointBean.axis == Axis.HOR) {
        gciList = GridCollisionDetector.calcHorGridCollisionInfo(beg.x, beg.y, vx, vy, GamePanel.GRID_SIZE);
        g2d.setColor(Color.YELLOW);
      } else {
        gciList = GridCollisionDetector.calcVerGridCollisionInfo(beg.x, beg.y, vx, vy, GamePanel.GRID_SIZE);
        g2d.setColor(Color.RED);
      }

      g2d.drawOval(beg.x - GamePanel.POINT_RADIUS_BIG, beg.y - GamePanel.POINT_RADIUS_BIG,
          2 * GamePanel.POINT_RADIUS_BIG, 2 * GamePanel.POINT_RADIUS_BIG);
      g2d.setColor(Color.WHITE);
      g2d.drawOval(beg.x - GamePanel.POINT_RADIUS_SML, beg.y - GamePanel.POINT_RADIUS_SML,
          2 * GamePanel.POINT_RADIUS_SML, 2 * GamePanel.POINT_RADIUS_SML);

      if (pointBean.axis == Axis.HOR) {
        g2d.setColor(Color.YELLOW);
      } else {
        g2d.setColor(Color.RED);
      }

      g2d.drawOval(end.x - GamePanel.POINT_RADIUS_BIG, end.y - GamePanel.POINT_RADIUS_BIG,
          2 * GamePanel.POINT_RADIUS_BIG, 2 * GamePanel.POINT_RADIUS_BIG);
      g2d.setColor(Color.WHITE);
      g2d.drawOval(end.x - GamePanel.POINT_RADIUS_SML, end.y - GamePanel.POINT_RADIUS_SML,
          2 * GamePanel.POINT_RADIUS_SML, 2 * GamePanel.POINT_RADIUS_SML);

      g2d.setColor(Color.WHITE);
      g2d.drawOval(gciList.px - GamePanel.POINT_RADIUS_SML, gciList.py - GamePanel.POINT_RADIUS_SML,
          2 * GamePanel.POINT_RADIUS_SML, 2 * GamePanel.POINT_RADIUS_SML);
      //      if (pointBean.axis == Axis.HOR) {
      //        drawHorPointList(g2d, gciList);
      //      } else {
      //        drawVerPointList(g2d, gciList);
      //      }

      //      if (!gciList.isEmpty()) {
      firstGCList.add(gciList);
      firstPBList.add(pointBean);
      //      }
    }

    minTime = Double.MAX_VALUE;
    for (int i = 0; i < firstGCList.size(); i++) {
      GridCollisionInfo gci = firstGCList.get(i);
      PointBean pb = firstPBList.get(i);

      int dst;
      double curTime;

      if (pb.axis == Axis.HOR) {
        dst = Math.abs(pb.beg.x - gci.px);
        curTime = Math.abs(dst / (double) vx);
      } else {
        dst = Math.abs(pb.beg.y - gci.py);
        curTime = Math.abs(dst / (double) vy);
      }

      if (curTime > 0 && curTime < minTime) {
        minTime = curTime;
        minGci = gci;
        minPb = pb;
      }
    }
  }

  // --------------------------------------------------------------------------------

  private void drawBox(Graphics2D g2d, BoxImpl box, Color color) {

    g2d.setColor(Color.CYAN);
    g2d.drawRect((int) box.sx, (int) box.sy, (int) box.bw, (int) box.bh);

    g2d.setColor(color);
    g2d.drawRect((int) box.cx, (int) box.cy, (int) box.bw, (int) box.bh);

    Point beg1 = new Point();
    Point end1 = new Point();
    Point beg2 = new Point();
    Point end2 = new Point();

    if (box.cx < box.sx) {
      if (box.cy < box.sy) {
        beg1.setLocation(box.cx, box.cy + box.bh);
        end1.setLocation(box.sx, box.sy + box.bh);

        beg2.setLocation(box.cx + box.bw, box.cy);
        end2.setLocation(box.sx + box.bw, box.sy);
      } else if (box.cy == box.sy) {
        beg1.setLocation(box.cx + box.bw, box.cy + box.bh);
        end1.setLocation(box.sx, box.sy + box.bh);

        beg2.setLocation(box.cx + box.bw, box.cy);
        end2.setLocation(box.sx, box.sy);
      } else {
        beg1.setLocation(box.cx + box.bw, box.cy + box.bh);
        end1.setLocation(box.sx + box.bw, box.sy + box.bh);

        beg2.setLocation(box.cx, box.cy);
        end2.setLocation(box.sx, box.sy);
      }
      // -------------------------------------------
    } else if (box.cx == box.sx) {
      if (box.cy < box.sy) {
        beg1.setLocation(box.cx, box.cy + box.bh);
        end1.setLocation(box.sx, box.sy);

        beg2.setLocation(box.cx + box.bw, box.cy + box.bh);
        end2.setLocation(box.sx + box.bw, box.sy);
      } else if (box.cy == box.sy) {
        // NONE
      } else {
        beg1.setLocation(box.sx, box.sy + box.bh);
        end1.setLocation(box.cx, box.cy);

        beg2.setLocation(box.sx + box.bw, box.sy + box.bh);
        end2.setLocation(box.cx + box.bw, box.cy);
      }
      // -------------------------------------------
    } else {
      if (box.cy < box.sy) {
        beg1.setLocation(box.sx, box.sy);
        end1.setLocation(box.cx, box.cy);

        beg2.setLocation(box.sx + box.bw, box.sy + box.bh);
        end2.setLocation(box.cx + box.bw, box.cy + box.bh);
      } else if (box.cy == box.sy) {
        beg1.setLocation(box.sx + box.bw, box.sy);
        end1.setLocation(box.cx, box.cy);

        beg2.setLocation(box.sx + box.bw, box.sy + box.bh);
        end2.setLocation(box.cx, box.cy + box.bh);
      } else {
        beg1.setLocation(box.cx + box.bw, box.cy);
        end1.setLocation(box.sx + box.bw, box.sy);

        beg2.setLocation(box.cx, box.cy + box.bh);
        end2.setLocation(box.sx, box.sy + box.bh);
      }
    }

    if (beg1 != null && end1 != null) {
      g2d.setColor(Color.WHITE);
      g2d.drawLine(beg1.x, beg1.y, end1.x, end1.y);
    }
    if (beg2 != null && end2 != null) {
      g2d.setColor(Color.WHITE);
      g2d.drawLine(beg2.x, beg2.y, end2.x, end2.y);
    }
  }

  private void drawVerPointList(Graphics2D g2d, List<GridCollisionInfo> calculateVerPointList) {
    for (GridCollisionInfo gci : calculateVerPointList) {
      g2d.setColor(Color.WHITE);
      g2d.drawOval(gci.px - GamePanel.POINT_RADIUS_SML, gci.py - GamePanel.POINT_RADIUS_SML,
          2 * GamePanel.POINT_RADIUS_SML, 2 * GamePanel.POINT_RADIUS_SML);

      int gx = gci.gx * GamePanel.GRID_SIZE + GamePanel.GRID_SIZE / 2;
      int gy = gci.gy * GamePanel.GRID_SIZE + GamePanel.GRID_SIZE / 2;

      //      g2d.setColor(Color.BLUE);
      //      g2d.drawLine(//
      //          gx - GamePanel.POINT_RADIUS_SML, //
      //          gy, //
      //          gx + GamePanel.POINT_RADIUS_SML, //
      //          gy);
      //
      //      g2d.drawLine(//
      //          gx, //
      //          gy - GamePanel.POINT_RADIUS_SML, //
      //          gx, //
      //          gy + GamePanel.POINT_RADIUS_SML);
      //
      //      g2d.drawLine(gx, gy, gci.px, gci.py);
    }
  }

  private void drawHorPointList(Graphics2D g2d, List<GridCollisionInfo> calculateHorPointList) {
    for (GridCollisionInfo gci : calculateHorPointList) {
      g2d.setColor(Color.WHITE);
      g2d.drawOval(gci.px - GamePanel.POINT_RADIUS_SML, gci.py - GamePanel.POINT_RADIUS_SML,
          2 * GamePanel.POINT_RADIUS_SML, 2 * GamePanel.POINT_RADIUS_SML);

      int gx = gci.gx * GamePanel.GRID_SIZE + GamePanel.GRID_SIZE / 2;
      int gy = gci.gy * GamePanel.GRID_SIZE + GamePanel.GRID_SIZE / 2;

      //      g2d.setColor(Color.YELLOW);
      //      g2d.drawLine(//
      //          gx - GamePanel.POINT_RADIUS_SML, //
      //          gy - GamePanel.POINT_RADIUS_SML, //
      //          gx + GamePanel.POINT_RADIUS_SML, //
      //          gy + GamePanel.POINT_RADIUS_SML);
      //
      //      g2d.drawLine(//
      //          gx - GamePanel.POINT_RADIUS_SML, //
      //          gy + GamePanel.POINT_RADIUS_SML, //
      //          gx + GamePanel.POINT_RADIUS_SML, //
      //          gy - GamePanel.POINT_RADIUS_SML);
      //
      //      g2d.drawLine(gx, gy, gci.px, gci.py);
    }
  }

  // --------------------------------------------------------------------------------

  public enum Axis {
    VER, HOR
  }

  public static class PointBean {
    public Point beg;

    public Axis axis;
  }
}
