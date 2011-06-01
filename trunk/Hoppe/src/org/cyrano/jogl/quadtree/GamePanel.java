package org.cyrano.jogl.quadtree;

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
import org.cyrano.util.PointDbl;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel {

  private List<PointDbl> pointDblList = new ArrayList<PointDbl>();

  private PointDbl dragPoint;

  private int dx;
  private int dy;

  private QuadNode quadNode;

  // --------------------------------------------------------------------------------

  public GamePanel() {
    pointDblList.add(new PointDbl(/**/200, /**/100));
    pointDblList.add(new PointDbl(/**/100, /* */50));
    pointDblList.add(new PointDbl(/**/200, /* */10));
    pointDblList.add(new PointDbl(/*  */5, /**/250));
    pointDblList.add(new PointDbl(/* */50, /**/400));
    pointDblList.add(new PointDbl(/**/500, /**/400));
    pointDblList.add(new PointDbl(/**/100, /**/111));
    pointDblList.add(new PointDbl(/**/150, /**/150));
    
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

    if (quadNode == null) {
      initQuadNode(); // Init here or there will be no w/h
    }
    
    for (PointDbl pointDbl : pointDblList) {
      g2d.setColor(Color.YELLOW);
      g2d.drawOval(pointDbl.getIX() - 5, pointDbl.getIY() - 5, 10, 10);
    }
    
    quadNode.draw(g2d);
  }
  
  private void initQuadNode() {
    quadNode = new QuadNode(null, 0, 0, Hwh.getW(this), Hwh.getH(this));

    for (PointDbl pointDbl : pointDblList) {
      quadNode.insert(pointDbl);
    }
  }

  // --------------------------------------------------------------------------------

  //  private void drawPol(Graphics2D g2d, List<PointInt> ch, Color color) {
  //    g2d.setColor(color);
  //
  //    PointInt frstPoint = null;
  //    PointInt prevPoint = null;
  //
  //    for (PointInt currPoint : ch) {
  //      if (prevPoint != null) {
  //        g2d.drawLine(prevPoint.x, prevPoint.y, currPoint.x, currPoint.y);
  //      }
  //
  //      if (frstPoint == null) {
  //        frstPoint = currPoint;
  //      }
  //
  //      prevPoint = currPoint;
  //    }
  //
  //    g2d.drawLine(prevPoint.x, prevPoint.y, frstPoint.x, frstPoint.y);
  //  }

  // --------------------------------------------------------------------------------
  // Mouse handling
  // --------------------------------------------------------------------------------

  private void mousePressed(MouseEvent evt) {
    for (PointDbl pointDbl : pointDblList) {
      Rectangle r = new Rectangle( //
          pointDbl.getIX() - 5, pointDbl.getIY() - 5, 10, 10);

      if (r.contains(evt.getPoint())) {
        dx = evt.getPoint().x - pointDbl.getIX();
        dy = evt.getPoint().y - pointDbl.getIY();
        dragPoint = pointDbl;
        return;
      }
    }

    QuadNode qn = quadNode.getQuadFor(new PointDbl(evt.getPoint().x, evt.getPoint().y));
    qn.setColor(Color.GREEN);
    repaint();
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
    
    dragPoint.x = evt.getPoint().x - dx;
    dragPoint.y = evt.getPoint().y - dy;

    initQuadNode();

    repaint();
  }
}
