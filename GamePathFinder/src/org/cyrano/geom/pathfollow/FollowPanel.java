package org.cyrano.geom.pathfollow;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

public class FollowPanel extends JPanel implements Runnable {

  private Point next;
  private double speed = 100;

  private PathReader pathReader;

  private Sprite sprite;

  // --------------------------------------------------------------------------------

  public FollowPanel() {

    // ----------------------------------------
    // Init path
    // ----------------------------------------

    pathReader = new PathReader();

    try {
      pathReader.load(getClass().getResource("path1.txt").getPath());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    // ----------------------------------------
    // Init sprite
    // ----------------------------------------

    sprite = new Sprite();
    sprite.setX(pathReader.curr().x);
    sprite.setY(pathReader.curr().y);

    next = pathReader.next();
    sprite.setGoingto(next);

    // ----------------------------------------
    // Start thread
    // ----------------------------------------

    Thread th = new Thread(this);
    th.start();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void run() {
    long t1 = System.nanoTime();

    int sleep = 20;
    int count = 0;

    while (true) {
      try {
        Thread.sleep(sleep);

        if (count % 2 == 0) {
          repaint();
        }

        count++;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      long t2 = System.nanoTime();

      // Time in seconds
      double dt = (t2 - t1) / 1000000000.0; // We should use int/long for performance

      sprite.update(dt);

      while (dt > 0) {
        // Dist to go in this loop
        double dst = speed * dt;

        // Dist to target
        double x = sprite.getX();
        double y = sprite.getY();

        double dstTgt = Math.sqrt((x - next.x) * (x - next.x) + (y - next.y) * (y - next.y));

        double xTh1 = next.x - x;
        double yTh1 = next.y - y;

        if (dstTgt < dst) {
          // We get first to the target
          dt -= dstTgt / speed;

          x = next.x;
          y = next.y;

          next = pathReader.next();
          sprite.setGoingto(next);
        } else {
          double dx = (next.x - x) / dstTgt;
          double dy = (next.y - y) / dstTgt;

          x += speed * dx * dt;
          y += speed * dy * dt;

          dt = 0;

          double xTh2 = next.x - x;
          double yTh2 = next.y - y;

          if (Math.signum(xTh1) != Math.signum(xTh2) || Math.signum(yTh1) != Math.signum(yTh2)) {
            x = next.x;
            y = next.y;

            next = pathReader.next();
            sprite.setGoingto(next);
          }

          t1 = t2; // And go again...
        }

        sprite.setX(x);
        sprite.setY(y);
      }
    }
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
    g2d.clearRect(0, 0, getWidth(), getHeight());

    pathReader.paint(g2d);

    sprite.paint(g2d);
  }
}
