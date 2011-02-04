package boxcollision6;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.util.Hwh;

import boxcollision6.Box.CollisionInfo;
import boxcollision6.Box.Side;


/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel /*implements Runnable*/implements MouseListener, MouseMotionListener {

  private boolean running = true;

  private List<Box> boxList = new ArrayList<Box>();

  private Box box1 = new Box();
  private Box box2 = new Box();

  private Box dragBox;
  private boolean start;

  // --------------------------------------------------------------------------------

  public GamePanel() {
    setDoubleBuffered(true);

    box1 = new Box();

    box1.cx = 100;
    box1.cy = 300;

    box1.bw = 75;
    box1.bh = 50;

    box1.sx = 400;
    box1.sy = 100;

    box1.calcV();

    boxList.add(box1);

    box2 = new Box();

    box2.cx = 110;
    box2.cy = 100;

    box2.bw = 50;
    box2.bh = 75;

    box2.sx = 400;
    box2.sy = 300;

    box2.calcV();

    boxList.add(box2);

    // ----------------------------------------

    //    Thread thread = new Thread(this);
    //    thread.start();

    addMouseListener(this);
    addMouseMotionListener(this);
  }

  // --------------------------------------------------------------------------------

  //  @Override
  //  public void run() {
  //    try {
  //      Thread.sleep(500);
  //    } catch (InterruptedException e) {
  //      e.printStackTrace();
  //    }
  //
  //    long t1 = System.currentTimeMillis();
  //
  //    int sleep = 20;
  //    int count = 0;
  //
  //    while (running) {
  //      try {
  //        Thread.sleep(sleep);
  //
  //        repaint();
  //
  //        count++;
  //      } catch (InterruptedException e) {
  //        e.printStackTrace();
  //      }
  //
  //      long t2 = System.currentTimeMillis();
  //
  //      //      double dt = (t2 - t1) / 1000.0;
  //      double dt = 0.02;
  //
  //      // ----------------------------------------
  //      // Update Timer
  //      // ----------------------------------------
  //
  //      // timer.handleDelta(dt);
  //
  //      // ----------------------------------------
  //      // Update Sprites
  //      // ----------------------------------------
  //
  //      updateAll(dt);
  //      repaint();
  //
  //      t1 = t2;
  //    }
  //  }

  // --------------------------------------------------------------------------------

  //  private void updateAll(double dt) {
  //    CollisionInfo outCol = null;
  //
  //    synchronized (drawBoxList) {
  //      while (!drawBoxList.isEmpty()) {
  //        Box curBox = drawBoxList.remove(0);
  //
  //        if (!curBox.mv) {
  //          doneBoxList.add(curBox);
  //          continue;
  //        }
  //
  //        CollisionInfo col = null;
  //
  //        double minTtc = Double.MAX_VALUE;
  //
  //        for (Box tgtBox : drawBoxList) {
  //          CollisionInfo ci = new CollisionInfo();
  //          ci.box1 = curBox;
  //          ci.box2 = tgtBox;
  //          Box.timeToCollide(ci);
  //          double curTtc = ci.time;
  //
  //          if (curTtc < minTtc) {
  //            minTtc = curTtc;
  //            col = ci;
  //          }
  //        }
  //
  //        doneBoxList.add(curBox);
  //
  //        System.err.println(dt + ";" + minTtc);
  //        if (minTtc < dt) {
  //          // running = false;
  //          outCol = col;
  //
  //          System.err.println("****************************************");
  //          System.err.println("Box1: " + (int) col.box1.cx + ";" + (int) col.box1.cy + ";" + //
  //              col.box1Side + ";" + col.box1.color + ";" + col.box1.id);
  //          System.err.println("Box2: " + (int) col.box2.cx + ";" + (int) col.box2.cy + ";" + //
  //              col.box2Side + ";" + col.box2.color + ";" + col.box2.id);
  //
  //          dt = minTtc;
  //        }
  //      }
  //
  //      for (Box box : doneBoxList) {
  //        box.updatePos(dt);
  //      }
  //
  //      if (outCol != null) {
  //        handleCollision(outCol);
  //      }
  //
  //      List<Box> aux = doneBoxList;
  //      doneBoxList = drawBoxList;
  //      drawBoxList = aux;
  //      //    running = false;
  //      System.err.println("bye updateAll");
  //    }
  //  }

  //  private void handleCollision(CollisionInfo col) {
  //    switch (col.box1Side) {
  //      case LFT :
  //      case RGH :
  //        col.box1.vx *= -1;
  //        System.err.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!!!!!!!!!!!!!!11");
  //        break;
  //
  //      case TOP :
  //      case BOT :
  //        col.box1.vy *= -1;
  //        System.err.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBB!!!!!!!!!!!!!!!!11");
  //        break;
  //    }
  //    
  ////    Box.timeToCollide(collisionInfo)
  //
  //  }

  // --------------------------------------------------------------------------------
  // JPanel
  // --------------------------------------------------------------------------------

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  // --------------------------------------------------------------------------------

  public void drawBox(Graphics2D g2d, Box box, Color color) {

    //    g2d.setColor(color.brighter());

    g2d.setColor(Color.CYAN);
    g2d.drawRect((int) box.sx, (int) box.sy, (int) box.bw, (int) box.bh);
    //    g2d.setColor(color.darker());
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

  @Override
  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    drawBox(g2d, box1, Color.RED);
    drawBox(g2d, box2, Color.GREEN);

    CollisionInfo ci = new CollisionInfo();
    ci.box1 = box1;
    ci.box2 = box2;

    Box.timeToCollide(ci);

    System.err.println("*****RED");
    drawBox(g2d, box1, Color.RED, ci);
    System.err.println("*****GREEN");
    drawBox(g2d, box2, Color.GREEN, ci);
  }

  private void drawBox(Graphics2D g2d, Box box, Color color, CollisionInfo ci) {
    Rectangle r = new Rectangle();

    System.err.println("**############################################ " + ci.time);
    if (ci.time != Double.MAX_VALUE) {
      r.x = (int) (box.cx + box.vx * ci.time * val);
      r.y = (int) (box.cy + box.vy * ci.time * val);
      System.err.println("**############################################ " + ci.time * val);
    } else {
      r.x = (int) (box.cx + box.vx * 1 * val);
      r.y = (int) (box.cy + box.vy * 1 * val);
      System.err.println("**############################################ " + 1 * val);
    }

    r.width = box.bw;
    r.height = box.bh;

    g2d.setColor(Color.GRAY);
    g2d.draw(r);

    System.err.println("**++++++++++++ " + box.vx + ";" + box.vy);

    r.x = (int) (box.cx + box.vx * ci.time);
    r.y = (int) (box.cy + box.vy * ci.time);
    r.width = box.bw;
    r.height = box.bh;

    g2d.setColor(color);
    g2d.draw(r);

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
          g2d.drawLine(r.x, r.y, r.x, r.y + r.height);
          break;

        case RGH :
          g2d.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
          break;

        case TOP :
          g2d.drawLine(r.x, r.y, r.x + r.width, r.y);
          break;

        case BOT :
          g2d.drawLine(r.x, r.y + r.height, r.x + r.width, r.y + r.height);
          break;
      }
    }
  }

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
    for (Box box : boxList) {
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

  @Override
  public void mouseReleased(MouseEvent e) {
    dragBox = null;
  }

  @Override
  public void mouseDragged(MouseEvent evt) {
    if (dragBox == null) {
      return;
    }

    System.err.println(dx + ";" + dy);
    if (start) {
      dragBox.cx = evt.getPoint().x - dx;
      dragBox.cy = evt.getPoint().y - dy;
    } else {
      dragBox.sx = evt.getPoint().x - dx;
      dragBox.sy = evt.getPoint().y - dy;
    }

    dragBox.calcV();
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
