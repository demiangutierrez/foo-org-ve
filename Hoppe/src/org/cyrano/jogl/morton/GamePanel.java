package org.cyrano.jogl.morton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.Collections;

import javax.swing.JPanel;

import org.cyrano.util.Hwh;
import org.cyrano.util.PointAbs;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel {

  private PointGen pointGen;
  private PointSet pointSet;
  private PointAbs drgPoint;

  private int dx;
  private int dy;

  public static boolean drawText = false;

  // --------------------------------------------------------------------------------

  public GamePanel() {
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

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent evt) {
        GamePanel.this.componentResized(evt);
      }
    });
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
    if (pointGen == null) {
      pointGen = new PointGen();
      pointGen.setScrWH(Hwh.getW(this), Hwh.getH(this));
      pointGen.initPointList();

      pointSet = pointGen.getPointSet();

      sortPointSet();
    }

    Graphics2D g2d = (Graphics2D) g;

    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    AffineTransform prev = null;

    // XXX: Not here
    if (PointGen.LESSTZ) {
      prev = g2d.getTransform();

      g2d.translate(Hwh.getW(this) / 2, Hwh.getH(this) / 2);

      g2d.setColor(Color.WHITE);
      g2d.drawLine(-Hwh.getW(this) / 2, 0, Hwh.getW(this) / 2, 0);
      g2d.drawLine(0, -Hwh.getH(this) / 2, 0, Hwh.getH(this) / 2);
    }

    PointAbs prevPoint = null;

    int rc = 0;
    int gc = 64;
    int bc = 128;
    int index = 0;

    for (PointAbs currPoint : pointSet.getPointAbsList()) {
      g2d.setColor(Color.YELLOW);
      //g2d.drawOval(currPoint.getIX() - 5, currPoint.getIY() - 5, 10, 10);
      g2d.drawOval(pointGen.getScrX(currPoint) - 5, pointGen.getScrY(currPoint) - 5, 10, 10);

      if (drawText) {
        //g2d.drawString(Integer.toString(index), //
        //    currPoint.getIX() + 7, currPoint.getIY() + 7);
        g2d.drawString(Integer.toString(index), //
            pointGen.getScrX(currPoint) + 7, pointGen.getScrY(currPoint) + 7);
      }

      if (prevPoint != null) {
        g2d.setColor(new Color(rc, gc, bc));
        //g2d.drawLine( //
        //    prevPoint.getIX(), prevPoint.getIY(), //
        //    currPoint.getIX(), currPoint.getIY());
        g2d.drawLine( //
            pointGen.getScrX(prevPoint), pointGen.getScrY(prevPoint), //
            pointGen.getScrX(currPoint), pointGen.getScrY(currPoint));

        rc = (rc + 2) % 256;
        gc = (gc + 4) % 256;
        bc = (bc + 8) % 256;
      }

      prevPoint = currPoint;

      index++;
    }

    // XXX: Not here
    if (PointGen.LESSTZ) {
      g2d.setTransform(prev);
    }
  }

  // --------------------------------------------------------------------------------

  private void sortPointSet() {
    Collections.sort( //
        pointSet.getPointAbsList(), //
        new MortonComparator(pointSet.getMinPoint()));
  }

  // --------------------------------------------------------------------------------
  // Mouse handling
  // --------------------------------------------------------------------------------

  private void mousePressed(MouseEvent evt) {
    for (PointAbs pointDbl : pointSet.getPointAbsList()) {

      // to compensate transform (see update)
      int evtpx = (int) (evt.getPoint().getX());
      int evtpy = (int) (evt.getPoint().getY());

      if (PointGen.LESSTZ) {
        evtpx -= Hwh.getW(this) / 2;
        evtpy -= Hwh.getH(this) / 2;
      }

      Rectangle r = new Rectangle( //
          pointGen.getScrX(pointDbl) - 5, //
          pointGen.getScrY(pointDbl) - 5, //
          10, 10);

      if (r.contains(evtpx, evtpy)) {
        dx = evtpx - pointGen.getScrX(pointDbl);
        dy = evtpy - pointGen.getScrY(pointDbl);

        drgPoint = pointDbl;

        return;
      }
    }

    repaint();
  }

  // --------------------------------------------------------------------------------

  private void mouseReleased(MouseEvent e) {
    drgPoint = null;
  }

  // --------------------------------------------------------------------------------

  private void mouseDragged(MouseEvent evt) {
    if (drgPoint == null) {
      return;
    }

    // to compensate transform (see update)
    int evtpx = (int) (evt.getPoint().getX());
    int evtpy = (int) (evt.getPoint().getY());

    if (PointGen.LESSTZ) {
      evtpx -= Hwh.getW(this) / 2;
      evtpy -= Hwh.getH(this) / 2;
    }

    pointGen.setScrX(drgPoint, evtpx - dx);
    pointGen.setScrY(drgPoint, evtpy - dy);

    sortPointSet();

    repaint();
  }

  // --------------------------------------------------------------------------------

  private void componentResized(ComponentEvent evt) {
    if (pointGen != null) {
      pointGen.setScrWH( //
          Hwh.getW(this), Hwh.getH(this));
      repaint();
    }
  }
}
