package org.cyrano.boxcollision.test4;

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

import org.cyrano.boxcollision.base.CollisionDetector.CollisionInfo;
import org.cyrano.boxcollision.base.UniverseCollisionDetector;
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

    // Player...

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

    BoxImpl box1 = new Asteroid();

    box1.cx = 100;
    box1.cy = 100;

    box1.bw = 50;
    box1.bh = 50;

    box1.vx = 100;
    box1.vy = 5;

    box1.mv = true;
    box1.color = Color.GREEN;

    drawBoxList.add(box1);

    box1 = new Asteroid();

    box1.cx = 200;
    box1.cy = 200;

    box1.bw = 150;
    box1.bh = 100;

    box1.vx = 100;
    box1.vy = -50;

    box1.mv = true;
    box1.color = Color.RED;

    drawBoxList.add(box1);

    // --------------------

    box1 = new Asteroid();
    box1.cx = 400;
    box1.cy = 75;

    box1.bw = 100;
    box1.bh = 100;

    box1.vx = -150;
    box1.vy = -2;

    box1.mv = true;
    box1.color = Color.CYAN;

    //    drawBoxList.add(box1);

    // ----------------------------------------

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
    double updateDt = dt;

    CollisionInfo ci = UniverseCollisionDetector.calcTimeToCollide( //
        drawBoxList, statBoxList);

    if (ci != null && ci.time < updateDt) {
      updateDt = ci.time;
    } else {
      ci = null;
    }

    BoxImpl[] boxArray = drawBoxList.toArray(new BoxImpl[0]);

    for (int i = 0; i < boxArray.length; i++) {
      boxArray[i].updatePos(updateDt, drawBoxList);
    }

    if (ci != null) {
      handleCollision(ci);
    }

    return dt - updateDt;
  }

  private void handleCollision(CollisionInfo col) {

    if (col.box1 instanceof Asteroid && col.box2 instanceof Asteroid) {
      CollisionManager.hitAsteroidAsteroid( //
          (Asteroid) col.box1, (Asteroid) col.box2, col.box1Side, col.box2Side, drawBoxList);
    }

    // --------------------------------------------------------------------------------

    if (col.box1 instanceof Asteroid && col.box2 instanceof Player) {
      CollisionManager.hitAsteroidPlayer( //
          (Asteroid) col.box1, (Player) col.box2, col.box1Side, col.box2Side, drawBoxList);
    }

    if (col.box1 instanceof Player && col.box2 instanceof Asteroid) {
      CollisionManager.hitAsteroidPlayer( //
          (Asteroid) col.box2, (Player) col.box1, col.box1Side, col.box2Side, drawBoxList);
    }

    // --------------------------------------------------------------------------------

    if (col.box1 instanceof Player && col.box2 instanceof Bullet) {
      CollisionManager.hitPlayerBullet( //
          (Player) col.box1, (Bullet) col.box2, col.box1Side, col.box2Side, drawBoxList);
    }

    if (col.box1 instanceof Bullet && col.box2 instanceof Player) {
      CollisionManager.hitPlayerBullet( //
          (Player) col.box2, (Bullet) col.box1, col.box1Side, col.box2Side, drawBoxList);
    }

    // --------------------------------------------------------------------------------

    if (col.box1 instanceof Bullet && col.box2 instanceof Asteroid) {
      CollisionManager.hitBulletAsteroid( //
          (Bullet) col.box1, (Asteroid) col.box2, col.box1Side, col.box2Side, drawBoxList);
    }

    if (col.box1 instanceof Asteroid && col.box2 instanceof Bullet) {
      CollisionManager.hitBulletAsteroid( //
          (Bullet) col.box2, (Asteroid) col.box1, col.box1Side, col.box2Side, drawBoxList);
    }
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
