package org.cyrano.boxcollision.test4;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import org.cyrano.boxcollision.base.Box;

public class Player extends BoxImpl {

  private double hx = +0;
  private double hy = -1;

  private double curFireTime = 0;
  private double maxFireTime = 0.75;

  @Override
  public boolean collides(Box box) {
    return true;
  }

  private double vang;

  private void turnLft(double dt) {
    vang += (-20) * dt;
  }

  private void turnRgh(double dt) {
    vang += (+20) * dt;
  }

  private void turn(double dt) {
    AffineTransform at = AffineTransform.getRotateInstance(vang * Math.PI / 180 * dt); // Cache

    double[] points = {hx, hy};

    at.transform(points, 0, points, 0, 1);

    hx = points[0];
    hy = points[1];
  }

  private void accFrw(double dt) {
    double aax = hx * (+10);
    double aay = hy * (+10);

    vx += aax * dt;
    vy += aay * dt;
  }

  private void accBck(double dt) {
    double aax = hx * (-10);
    double aay = hy * (-10);

    vx += aax * dt;
    vy += aay * dt;
  }

  public boolean fwr;
  public boolean bck;
  public boolean lft;
  public boolean rgh;
  public boolean fire;

  @Override
  public void updatePos(double dt, List<BoxImpl> drawBoxList) {
    if (fwr) {
      accFrw(dt);
    }
    if (bck) {
      accBck(dt);
    }
    if (lft) {
      turnLft(dt);
    }
    if (rgh) {
      turnRgh(dt);
    }

    turn(dt);

    curFireTime += dt;

    if (fire && curFireTime > maxFireTime) {
      Bullet b = new Bullet();

      b.mv = true;

      b.cx = midX() + (int) (hx * (getW() / 2 + 15)) - 6;
      b.cy = midY() + (int) (hy * (getH() / 2 + 15)) - 6;

      b.vx = hx * 50;
      b.vy = hy * 50;

      b.bw = 12;
      b.bh = 12;

      b.color = new Color((float) (Math.random() * 0.5 + 0.5), (float) (Math.random() * 0.5 + 0.5), (float) (Math
          .random() * 0.5 + 0.5));

      drawBoxList.add(b);

      curFireTime = 0;
    }

    super.updatePos(dt, drawBoxList);
  }

  public void draw(Graphics2D g2d) {
    g2d.setColor(Color.RED);
    g2d.drawOval(minX(), minY(), getW(), getH());

    g2d.setColor(Color.WHITE);
    g2d.setStroke(new BasicStroke(2));
    g2d.drawLine(midX(), midY(), midX() + (int) (hx * getW() / 2), midY() + (int) (hy * getH() / 2));
  }
}
