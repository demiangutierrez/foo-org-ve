package ray2D.proy;

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
import org.cyrano.util.geometry.PointDbl;
import org.cyrano.util.math.ConvexHull;
import org.cyrano.util.misc.Hwh;

public class Canvas extends JPanel //
    implements
      MouseListener,
      MouseMotionListener {

  private static final boolean DRAW_RAYS = false;

  private List<Geometry> gList = new ArrayList<Geometry>();

  private List<Line>/* */lList = new ArrayList<Line>();
  //  private List<Ray>/*  */rList = new ArrayList<Ray>();

  private ProyectionBox proyectionBox;

  // --------------------------------------------------------------------------------

  private CtrlPoint curr;

  private double dx;
  private double dy;

  // --------------------------------------------------------------------------------

  public Canvas() {
    addMouseListener(this);
    addMouseMotionListener(this);

    Loader loader = new Loader();

    try {
      loader.load( //
          getClass().getResourceAsStream("map.txt"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    lList.addAll(loader.getLineList());
    gList.addAll(loader.getLineList());

    // ----------------------------------------

    proyectionBox = new ProyectionBox();
    gList.add(proyectionBox);
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

    proyectionBox.setW(Hwh.getW(this));
    proyectionBox.setH(Hwh.getH(this));

    for (Geometry geometry : gList) {
      geometry.draw(g2d);
    }

    drawScreen(g2d);

    g2d.setTransform(prev);
  }

  // --------------------------------------------------------------------------------

  private void drawScreen(Graphics2D g2d) {
    int screensize = 50;
    int pixelSize = 8;

    double step = proyectionBox.sizeNear / screensize;

    PointDbl nearLftPt = proyectionBox.calcNearLft();
    PointDbl nearRghPt = proyectionBox.calcNearRgh();
    PointDbl backLftPt = proyectionBox.calcBackLft();
    PointDbl backRghPt = proyectionBox.calcBackRgh();

    PointDbl orthPt = proyectionBox.calcOrth();

    for (int i = 0; i < screensize; i++) {
      double raypinhole = i * step + step / 2;

      double rayX = orthPt.x * (-raypinhole) + nearLftPt.x;
      double rayY = orthPt.y * (-raypinhole) + nearLftPt.y;

      if (DRAW_RAYS) {
        g2d.drawOval((int) (rayX - 3), (int) (rayY - 3), 6, 6);
      }

      // ----------------------------------------

      Ray ray = new Ray();
      ray.p0.setX(proyectionBox.pPos.getX());
      ray.p0.setY(proyectionBox.pPos.getY());

      ray.pu.setX(rayX);
      ray.pu.setY(rayY);

      ray.trace(lList);

      ray.drawP0 = false;
      ray.drawPU = false;

      int pixPtX = i * pixelSize - screensize * pixelSize / 2;
      int pixPtY = -Hwh.getH(this) / 2 + pixelSize + 2;

      g2d.setColor(Color.WHITE);
      g2d.drawRect(pixPtX, pixPtY, pixelSize, pixelSize);

      boolean voidRay = true;

      for (TraceBean traceBean : ray.traceBeanList) {
        PointDbl testPt = new PointDbl();

        testPt.x = ray.calcX(traceBean.dist);
        testPt.y = ray.calcY(traceBean.dist);

        PointDbl[] pts = new PointDbl[]{ //
        nearLftPt, nearRghPt, backRghPt, backLftPt, testPt};

        List<PointDbl> convexHull = ConvexHull.convexHull(pts);

        if (!convexHull.contains(testPt)) {
          g2d.setColor(((Line) traceBean.geom).color);
          g2d.fillRect(pixPtX + 1, pixPtY, pixelSize - 1, pixelSize - 1);
          voidRay = false;
          break;
        }
      }

      if (voidRay) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(pixPtX + 1, pixPtY, pixelSize - 1, pixelSize - 1);
        g2d.setColor(Color.WHITE);
      }

      if (DRAW_RAYS) {
        ray.draw(g2d);
      }
    }
  }

  // --------------------------------------------------------------------------------

  //  public void trace() {
  //    for (Ray ray : rList) {
  //      ray.trace(lList);
  //    }
  //  }

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

    for (Geometry geometry : gList) {
      for (CtrlPoint test : geometry.getCtrlPointList()) {
        if (test.contains(x, y) != null) {
          curr = test;

          dx = curr.getX() - x;
          dy = curr.getY() - y;

          return;
        }
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

  @Override
  public void mouseDragged(MouseEvent evt) {
    if (curr == null) {
      return;
    }

    int x = evt.getPoint().x - Hwh.getW(this) / 2;
    //    int y = evt.getPoint().y - Hwh.getH(this) / 2;
    int y = (evt.getPoint().y - Hwh.getH(this) / 2) * -1;;

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
