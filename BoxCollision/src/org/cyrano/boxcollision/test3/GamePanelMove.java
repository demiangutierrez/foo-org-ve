package org.cyrano.boxcollision.test3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.util.misc.Hwh;

/**
 * @author Demián Gutierrez
 */
public class GamePanelMove extends JPanel implements //
      Runnable,
      MouseListener,
      MouseMotionListener,
      KeyListener {

  private boolean running = true;

  private List<BoxImpl> drawBoxList = new ArrayList<BoxImpl>();
  private List<BoxImpl> statBoxList = new ArrayList<BoxImpl>();

  private BoxImpl dragBox;
  private Player playerBox;
  private boolean start;

  // --------------------------------------------------------------------------------

  public GamePanelMove() {
    setFocusable(true);
    grabFocus();

    // PLayer...

    playerBox = new Player();

    playerBox.cx = 300;
    playerBox.cy = 300;

    playerBox.bw = 50;
    playerBox.bh = 50;

    playerBox.vx = 0;
    playerBox.vy = 0;

    playerBox.mv = true;
    playerBox.color = Color.GREEN;

    drawBoxList.add(playerBox);

    // ----

    Thread thread = new Thread(this);
    thread.start();

    addMouseListener(this);
    addMouseMotionListener(this);

    addKeyListener(this);
  }

  // --------------------------------------------------------------------------------

  int mod = 0;

  @Override
  public void run() {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    long t1 = System.currentTimeMillis();

    int sleep = 50;
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
      //      double dt = 0.2;

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

      while (dt > 0) {
        dt = updateAll(dt);
      }

      mod++;

      if (mod % 10 == 0) {
        System.err.println("List size: " + drawBoxList.size());
      }

      repaint();

      t1 = t2;
    }
  }

  // --------------------------------------------------------------------------------

  private double updateAll(double dt) {
    BoxImpl[] boxArray = drawBoxList.toArray(new BoxImpl[0]);

    for (int i = 0; i < boxArray.length; i++) {
      boxArray[i].updatePos(dt, drawBoxList);
    }

    return 0;
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
    g2d.drawRect(box.minX(), box.minY(), box.getW(), box.getH());
  }

  @Override
  public void update(Graphics g) {
    synchronized (drawBoxList) {

      Graphics2D g2d = (Graphics2D) g;

      g2d.setBackground(Color.BLACK);
      g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

      g2d.setColor(Color.YELLOW);
      g2d.drawRect(0, 0, 700, 500);

      for (BoxImpl box : statBoxList) {
        drawBox(g2d, box, box.color);
      }

      for (BoxImpl box : drawBoxList) {
        box.draw(g2d);
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
    synchronized (drawBoxList) {

      for (BoxImpl box : drawBoxList) {
        Rectangle r;

        r = new Rectangle(box.minX(), box.minY(), box.getW(), box.getH());

        if (r.contains(evt.getPoint())) {
          dx = evt.getPoint().x - box.minX();
          dy = evt.getPoint().y - box.minY();
          dragBox = box;
          start = true;
          return;
        }
      }

      for (BoxImpl box : statBoxList) {
        Rectangle r;

        r = new Rectangle(box.minX(), box.minY(), box.getW(), box.getH());

        if (r.contains(evt.getPoint())) {
          dx = evt.getPoint().x - box.minX();
          dy = evt.getPoint().y - box.minY();
          dragBox = box;
          start = true;
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

  @Override
  public void keyPressed(KeyEvent evt) {

    switch (evt.getKeyCode()) {
      case KeyEvent.VK_J :
        playerBox.lft = true;
        break;
      case KeyEvent.VK_L :
        playerBox.rgh = true;
        break;
      case KeyEvent.VK_I :
        playerBox.fwr = true;
        break;
      case KeyEvent.VK_K :
        playerBox.bck = true;
        break;
      case KeyEvent.VK_SPACE :
        playerBox.fire = true;
        break;

      default :
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent evt) {
    switch (evt.getKeyCode()) {
      case KeyEvent.VK_J :
        playerBox.lft = false;
        break;
      case KeyEvent.VK_L :
        playerBox.rgh = false;
        break;
      case KeyEvent.VK_I :
        playerBox.fwr = false;
        break;
      case KeyEvent.VK_K :
        playerBox.bck = false;
        break;
      case KeyEvent.VK_SPACE :
        playerBox.fire = false;
        break;

      default :
        break;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
}
