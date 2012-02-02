package org.cyrano.boxcollision.test1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.boxcollision.base.CollisionDetector.CollisionInfo;
import org.cyrano.boxcollision.base.CollisionDetector.Side;
import org.cyrano.boxcollision.base.UniverseCollisionDetector;
import org.cyrano.util.misc.Hwh;

/**
 * @author Demián Gutierrez
 */
public class GamePanelMove extends JPanel implements Runnable, MouseListener, MouseMotionListener {

  private boolean running = true;

  private List<BoxImpl> drawBoxList = new ArrayList<BoxImpl>();
  //  private List<BoxImpl> doneBoxList = new ArrayList<BoxImpl>();
  private List<BoxImpl> statBoxList = new ArrayList<BoxImpl>();

  //  private Box box1 = new Box();
  //  private Box box2 = new Box();

  private BoxImpl dragBox;
  private boolean start;

  // --------------------------------------------------------------------------------

  public GamePanelMove() {
    BoxImpl box1 = new BoxImpl();

    box1.cx = 100;
    box1.cy = 100;

    box1.bw = 50;
    box1.bh = 200;

    box1.mv = false;
    box1.color = Color.CYAN;

    statBoxList.add(box1);

    // --------------------

    box1 = new BoxImpl();
    box1.cx = 150;
    box1.cy = 50;

    box1.bw = 250;
    box1.bh = 50;

    box1.mv = false;
    box1.color = Color.CYAN;

    statBoxList.add(box1);

    // --------------------

    box1 = new BoxImpl();
    box1.cx = 150;
    box1.cy = 300;

    box1.bw = 250;
    box1.bh = 50;

    box1.mv = false;
    box1.color = Color.CYAN;

    statBoxList.add(box1);

    // --------------------

    BoxImpl box2 = new BoxImpl();

    box2.cx = 400;
    box2.cy = 100;

    box2.bw = 50;
    box2.bh = 200;

    box2.mv = false;
    box2.color = Color.CYAN;

    statBoxList.add(box2);

    BoxImpl box3 = new BoxImpl();

    box3.cx = 175;
    box3.cy = 175;

    box3.bw = 50;
    box3.bh = 50;

    box3.vx = 120;
    box3.vy = 120;

    box3.mv = true;
    box3.color = Color.RED;

    drawBoxList.add(box3);

    // ----------------------------------------

    Thread thread = new Thread(this);
    thread.start();

    addMouseListener(this);
    addMouseMotionListener(this);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void run() {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    long t1 = System.currentTimeMillis();

    int sleep = 20;
    int count = 0;

    while (running) {
      try {
        Thread.sleep(sleep);

        repaint();

        count++;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      long t2 = System.currentTimeMillis();

      double dt = (t2 - t1) / 1000.0;
      //      double dt = sleep / 1000.0;

      //      synchronized (this) {
      //        try {
      //          wait();
      //        } catch (InterruptedException e) {
      //          e.printStackTrace();
      //        }
      //      }

      // ----------------------------------------
      // Update Timer
      // ----------------------------------------

      // timer.handleDelta(dt);

      // ----------------------------------------
      // Update Sprites
      // ----------------------------------------

      updateAll(dt);
      repaint();

      t1 = t2;
    }
  }

  // --------------------------------------------------------------------------------

  private void updateAll(double dt) {
    CollisionInfo ci = UniverseCollisionDetector.calcTimeToCollide( //
        drawBoxList, statBoxList);

    if (ci != null && ci.time < dt) {
      dt = ci.time;
    } else {
      ci = null;
    }

    for (BoxImpl box : drawBoxList) {
      box.updatePos(dt);
    }

    if (ci != null) {
      handleCollision(ci);
    }
  }

  private void handleCollision(CollisionInfo col) {
    //    running = false;

    BoxImpl box;
    Side side;

    if (((BoxImpl) col.box1).mv == true) {
      side = col.box1Side;
      box = (BoxImpl) col.box1;
    } else {
      side = col.box2Side;
      box = (BoxImpl) col.box2;
    }

    switch (side) {
      case LFT :
      case RGH :
        box.vx *= -1;
        break;

      case TOP :
      case BOT :
        box.vy *= -1;
        break;
    }

    //    Box.timeToCollide(collisionInfo)

  }

  // --------------------------------------------------------------------------------
  // JPanel
  // --------------------------------------------------------------------------------

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  // --------------------------------------------------------------------------------

  public void drawBox(Graphics2D g2d, BoxImpl box, Color color) {
    g2d.setColor(color);
    g2d.drawRect(box.cx, box.cy, box.bw, box.bh);
  }

  @Override
  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    for (BoxImpl box : statBoxList) {
      drawBox(g2d, box, box.color);
    }

    for (BoxImpl box : drawBoxList) {
      drawBox(g2d, box, box.color);
    }

    //    CollisionInfo ci = new CollisionInfo();
    //    ci.box1 = box1;
    //    ci.box2 = box2;
    //
    //    Box.timeToCollide(ci);
    //
    //    drawBox(g2d, box1, Color.RED, ci);
    //    drawBox(g2d, box2, Color.GREEN, ci);
  }

  //  private void drawBox(Graphics2D g2d, Box box, Color color, CollisionInfo ci) {
  //    Rectangle r = new Rectangle();
  //
  //    if (ci.time != Double.MAX_VALUE) {
  //      r.x = (int) (box.cx + box.vx * ci.time * val);
  //      r.y = (int) (box.cy + box.vy * ci.time * val);
  //    } else {
  //      r.x = (int) (box.cx + box.vx * 1 * val);
  //      r.y = (int) (box.cy + box.vy * 1 * val);
  //    }
  //
  //    r.width = box.bw;
  //    r.height = box.bh;
  //
  //    g2d.setColor(Color.GRAY);
  //    g2d.draw(r);
  //
  //    r.x = (int) (box.cx + box.vx * ci.time);
  //    r.y = (int) (box.cy + box.vy * ci.time);
  //    r.width = box.bw;
  //    r.height = box.bh;
  //
  //    g2d.setColor(color);
  //    g2d.draw(r);
  //
  //    Side side = null;
  //
  //    if (box == ci.box1) {
  //      side = ci.box1Side;
  //    }
  //    if (box == ci.box2) {
  //      side = ci.box2Side;
  //    }
  //
  //    if (side != null) {
  //      g2d.setColor(Color.WHITE);
  //      switch (side) {
  //        case LFT :
  //          g2d.drawLine(r.x, r.y, r.x, r.y + r.height);
  //          break;
  //
  //        case RGH :
  //          g2d.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
  //          break;
  //
  //        case TOP :
  //          g2d.drawLine(r.x, r.y, r.x + r.width, r.y);
  //          break;
  //
  //        case BOT :
  //          g2d.drawLine(r.x, r.y + r.height, r.x + r.width, r.y + r.height);
  //          break;
  //      }
  //    }
  //  }

  @Override
  public void mouseClicked(MouseEvent evt) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  int dx;
  int dy;

  @Override
  public void mousePressed(MouseEvent evt) {
    synchronized (drawBoxList) {

      for (BoxImpl box : drawBoxList) {
        Rectangle r;

        r = new Rectangle(box.cx, box.cy, box.bw, box.bh);

        if (r.contains(evt.getPoint())) {
          dx = evt.getPoint().x - box.cx;
          dy = evt.getPoint().y - box.cy;
          dragBox = box;
          start = true;
          return;
        }

        r = new Rectangle(box.sx, box.sy, box.bw, box.bh);

        if (r.contains(evt.getPoint())) {
          dx = evt.getPoint().x - box.sx;
          dy = evt.getPoint().y - box.sy;
          dragBox = box;
          start = false;
          return;
        }
      }

      for (BoxImpl box : statBoxList) {
        Rectangle r;

        r = new Rectangle(box.cx, box.cy, box.bw, box.bh);

        if (r.contains(evt.getPoint())) {
          dx = evt.getPoint().x - box.cx;
          dy = evt.getPoint().y - box.cy;
          dragBox = box;
          start = true;
          return;
        }

        r = new Rectangle(box.sx, box.sy, box.bw, box.bh);

        if (r.contains(evt.getPoint())) {
          dx = evt.getPoint().x - box.sx;
          dy = evt.getPoint().y - box.sy;
          dragBox = box;
          start = false;
          return;
        }
      }
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    dragBox = null;
  }

  @Override
  public void mouseDragged(MouseEvent evt) {
    if (dragBox == null) {
      return;
    }

    if (start) {
      dragBox.cx = evt.getPoint().x - dx;
      dragBox.cy = evt.getPoint().y - dy;
    } else {
      dragBox.sx = evt.getPoint().x - dx;
      dragBox.sy = evt.getPoint().y - dy;
    }

    repaint();
  }

  @Override
  public void mouseMoved(MouseEvent evt) {
  }

  double val = 1;

  public void setVal(double val) {
    this.val = val;
    repaint();
  }
}
