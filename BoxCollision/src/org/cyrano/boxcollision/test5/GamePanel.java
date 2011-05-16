package org.cyrano.boxcollision.test5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.axis.Axis;
import org.cyrano.axis.PointDib;
import org.cyrano.boxcollision.test5.CollisionDetector.CollisionInfo;
import org.cyrano.math.ConvexHull;
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
        pol1.calcV();
    
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
        pol2.calcV();
    
        //pol2.tgtMove(-200, +200);
    
        polList.add(pol2);

    // ----------------------------------------

//    pol1 = new Polygon();
//
//    pol1.setColor(Color.GREEN);
//    pol1.getSrcPointList().add(new PointInt(/* */50,/* */60));
//    pol1.getSrcPointList().add(new PointInt(/**/100,/* */60));
//    pol1.getSrcPointList().add(new PointInt(/**/100,/**/110));
//    pol1.getSrcPointList().add(new PointInt(/* */50,/**/110));
//
//    pol1.setSrcX(+200);
//    pol1.setSrcY(+200);
//
//    pol1.initTgt();
//    //pol1.tgtMove(+200, +200);
//    pol1.calcV();
//
//    polList.add(pol1);
//
//    pol2 = new Polygon();
//
//    pol2.setColor(Color.RED);
//
//    pol2.getSrcPointList().add(new PointInt(/**/250,/* */50));
//    pol2.getSrcPointList().add(new PointInt(/**/300,/* */50));
//    pol2.getSrcPointList().add(new PointInt(/**/300,/**/100));
//    pol2.getSrcPointList().add(new PointInt(/**/250,/**/100));
//
//    pol2.setSrcX(-200);
//    pol2.setSrcY(+200);
//
//    pol2.initTgt();
//    //pol2.tgtMove(-200, +200);
//    pol2.calcV();
//
//    polList.add(pol2);

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

  private List<Axis> calculatePolAxis(List<PointInt> pointListList) {
    List<Axis> axisList = new ArrayList<Axis>();

    PointInt frstPoint = null;
    PointInt prevPoint = null;

    for (PointInt currPoint : pointListList) {
      if (prevPoint != null) {
        Axis axis = new Axis();
        axis.initFromSideNor(prevPoint, currPoint);
        axisList.add(axis);
      }

      if (frstPoint == null) {
        frstPoint = currPoint;
      }

      prevPoint = currPoint;
    }

    Axis axis = new Axis();
    axis.initFromSideNor(prevPoint, frstPoint);
    axisList.add(axis);

    return axisList;
  }

  private void drawPol(Graphics2D g2d, Polygon pol, Color color) {
    g2d.setColor(pol.getColor());
    g2d.setStroke(new BasicStroke(1));

    drawPol(g2d, pol.getSrcPointList(), 0, 0, color);
    drawPol(g2d, pol.getTgtPointList(), 0, 0, Color.CYAN);

    int dx = (int) (pol.vx * 1 * grayTimeFactor);
    int dy = (int) (pol.vy * 1 * grayTimeFactor);

    drawPol(g2d, pol.getSrcPointList(), dx, dy, Color.LIGHT_GRAY);

    PointInt[] theFuckingLine = calculateTheFuckingLine(pol);
    g2d.setColor(Color.WHITE);
    g2d.drawLine(theFuckingLine[0].x, theFuckingLine[0].y, theFuckingLine[1].x, theFuckingLine[1].y);
    g2d.drawLine(theFuckingLine[2].x, theFuckingLine[2].y, theFuckingLine[3].x, theFuckingLine[3].y);
  }

  private void drawPol(Graphics2D g2d, Polygon pol, Color color, CollisionInfo ci) {
    if (ci.time == Double.MAX_VALUE) {
      return;
    }

    g2d.setColor(pol.getColor());

    drawPol(g2d, pol.getSrcPointList(), 0, 0, color);
    drawPol(g2d, pol.getTgtPointList(), 0, 0, Color.CYAN);

    int dx = (int) (pol.vx * 1 * ci.time);
    int dy = (int) (pol.vy * 1 * ci.time);

    drawPol(g2d, pol.getSrcPointList(), dx, dy, Color.LIGHT_GRAY);

    //    PointInt[] theFuckingLine = calculateTheFuckingLine(pol);
    //    g2d.setColor(Color.WHITE);
    //    g2d.drawLine(theFuckingLine[0].x, theFuckingLine[0].y, theFuckingLine[1].x, theFuckingLine[1].y);
    //    g2d.drawLine(theFuckingLine[2].x, theFuckingLine[2].y, theFuckingLine[3].x, theFuckingLine[3].y);
  }

  //  private void drawProjectedFor(Graphics2D g2d, Axis axis, List<PointInt> pointListList) {
  //    for (PointInt pointInt : pointListList) {
  //      axis.projectPoint(pointInt);
  //    }
  //
  //    for (PointInt pointInt : axis.projectedPointList) {
  //      g2d.setColor(Color.PINK);
  //      g2d.drawOval(pointInt.x - 5, pointInt.y - 5, 10, 10);
  //    }
  //  }

  private void drawProjectedFor(Graphics2D g2d, AxisProblem axisProblem, Color c1, Color c2) {
    new PointDib(axisProblem.pBegPol1XY, c2).paint(g2d);
    new PointDib(axisProblem.pEndPol1XY, c2).paint(g2d);

    new PointDib(axisProblem.pBegPol2XY, c1).paint(g2d);
    new PointDib(axisProblem.pEndPol2XY, c1).paint(g2d);

    g2d.setStroke(new BasicStroke(3));
    g2d.setColor(c2);
    g2d.drawLine( //
        (int) axisProblem.pBegPol1XY.x, (int) axisProblem.pBegPol1XY.y, //
        (int) axisProblem.pEndPol1XY.x, (int) axisProblem.pEndPol1XY.y);
    g2d.setColor(c1);
    g2d.drawLine( //
        (int) axisProblem.pBegPol2XY.x, (int) axisProblem.pBegPol2XY.y, //
        (int) axisProblem.pEndPol2XY.x, (int) axisProblem.pEndPol2XY.y);
  }

  private void drawAxisList(Graphics2D g2d, List<Axis> axisList) {
    for (Axis axis : axisList) {
      axis.paint(g2d, axis.pSrc, 50, Color.YELLOW);
    }
  }

  private void drawPol(Graphics2D g2d, List<PointInt> ch, int dx, int dy, Color color) {
    g2d.setColor(color);

    PointInt frstPoint = null;
    PointInt prevPoint = null;

    for (PointInt currPoint : ch) {
      if (prevPoint != null) {
        g2d.drawLine(//
            prevPoint.x + dx, prevPoint.y + dy, //
            currPoint.x + dx, currPoint.y + dy);
      }

      if (frstPoint == null) {
        frstPoint = currPoint;
      }

      prevPoint = currPoint;
    }

    g2d.drawLine(//
        prevPoint.x + dx, prevPoint.y + dy, //
        frstPoint.x + dx, frstPoint.y + dy);
  }

  private PointInt[] calculateTheFuckingLine(Polygon pol) {
    List<PointInt> ret = new ArrayList<PointInt>();

    List<PointInt> allPointIntList = new ArrayList<PointInt>();
    allPointIntList.addAll(pol.getSrcPointList());
    allPointIntList.addAll(pol.getTgtPointList());

    List<PointInt> convexHull = //
    ConvexHull.convexHull(allPointIntList.toArray(new PointInt[0]));

    PointInt frstPoint = null;
    PointInt prevPoint = null;

    for (PointInt currPoint : convexHull) {
      if (prevPoint != null) {
        if (pol.getSrcPointList().contains(prevPoint) && //
            pol.getTgtPointList().contains(currPoint)) {
          ret.add(prevPoint);
          ret.add(currPoint);
        }

        if (pol.getSrcPointList().contains(currPoint) && //
            pol.getTgtPointList().contains(prevPoint)) {
          ret.add(prevPoint);
          ret.add(currPoint);
        }
      }

      if (frstPoint == null) {
        frstPoint = currPoint;
      }

      prevPoint = currPoint;
    }

    if (pol.getSrcPointList().contains(prevPoint) && //
        pol.getTgtPointList().contains(frstPoint)) {
      ret.add(prevPoint);
      ret.add(frstPoint);
    }

    if (pol.getSrcPointList().contains(frstPoint) && //
        pol.getTgtPointList().contains(prevPoint)) {
      ret.add(prevPoint);
      ret.add(frstPoint);
    }

    if (ret.size() != 4) {
      throw new IllegalStateException("ret.size() != 4");
    }

    return ret.toArray(new PointInt[0]);
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

    List<Axis> axisList1 = calculatePolAxis(pol1.getSrcPointList());
    drawAxisList(g2d, axisList1);
    List<AxisProblem> axisProblemList1 = new ArrayList<AxisProblem>();

    for (Axis axis : axisList1) {
      AxisProblem axisProblem = new AxisProblem();
      axisProblem.axis = axis;
      axisProblem.pol1 = pol1;
      axisProblem.pol2 = pol2;
      axisProblem.calcProjection();

      axisProblemList1.add(axisProblem);
      //drawProjectedFor(g2d, axisProblem, pol1.getColor(), pol2.getColor());
    }

    drawPol(g2d, pol2, BOX2_COLOR);

    List<Axis> axisList2 = calculatePolAxis(pol2.getSrcPointList());
    drawAxisList(g2d, axisList2);
    List<AxisProblem> axisProblemList2 = new ArrayList<AxisProblem>();

    for (Axis axis : axisList2) {
      AxisProblem axisProblem = new AxisProblem();
      axisProblem.axis = axis;
      axisProblem.pol1 = pol1;
      axisProblem.pol2 = pol2;
      axisProblem.calcProjection();

      axisProblemList2.add(axisProblem);
      //drawProjectedFor(g2d, axisProblem, pol1.getColor(), pol2.getColor());
    }

    //    drawBox(g2d, pol1, BOX1_COLOR, ci);
    //    drawBox(g2d, box2, BOX2_COLOR, ci);

    CollisionInfo ci = new CollisionInfo(pol1, axisProblemList1, pol2, axisProblemList2);
    CollisionDetector.calcTimeToCollide(ci);

    System.err.println(ci);

    drawPol(g2d, pol1, BOX1_COLOR, ci);
    drawPol(g2d, pol2, BOX2_COLOR, ci);
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
    } else {
      int nx = evt.getPoint().x - dx;
      int ny = evt.getPoint().y - dy;

      int ndx = nx - dragPol.getTgtPointList().get(0).x;
      int ndy = ny - dragPol.getTgtPointList().get(0).y;

      dragPol.tgtMove(ndx, ndy);
    }

    dragPol.calcV();

    System.err.println(dragPol.vx + ";" + dragPol.vy);

    repaint();
  }
}
