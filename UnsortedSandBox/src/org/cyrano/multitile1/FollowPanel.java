package org.cyrano.multitile1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class FollowPanel extends JPanel {

  private int cW = 20;
  private int cH = 20;

  private int gW = 30;
  private int gH = 20;

  private int pX = 320;
  private int pY = 240;

  private int pW = 100;
  private int pH = 50;

  private int xMin;
  private int yMin;

  private int xMax;
  private int yMax;

  private double angle = Math.PI / 4;

  // --------------------------------------------------------------------------------

  public FollowPanel() {

    setFocusable(true);
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent evt) {
        switch (evt.getKeyCode()) {
          case KeyEvent.VK_J :
            angle += Math.PI / 16;
            repaint();
            break;
          case KeyEvent.VK_L :
            angle -= Math.PI / 16;
            repaint();
            break;
          case KeyEvent.VK_I : {
            AffineTransform at = AffineTransform.getRotateInstance(angle, pX, pY);
            double[] xy = {pX + 10, pY};
            at.transform(xy, 0, xy, 0, xy.length / 2);
            pX = (int) xy[0];
            pY = (int) xy[1];

            //            angle += Math.PI / 16;
            repaint();
            break;
          }
          case KeyEvent.VK_K : {
            AffineTransform at = AffineTransform.getRotateInstance(angle, pX, pY);
            double[] xy = {pX - 10, pY};
            at.transform(xy, 0, xy, 0, xy.length / 2);
            pX = (int) xy[0];
            pY = (int) xy[1];
            repaint();
            break;
          }
        }
      }
    });
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

    g2d.setColor(Color.GRAY);

    for (int i = 0; i < gW; i++) {
      for (int j = 0; j < gH; j++) {
        g2d.drawRect(i * cW + 2, j * cH + 2, cW - 4, cH - 4);
      }
    }

    AffineTransform at = AffineTransform.getRotateInstance(angle, pX, pY);

    // Corners
    double[] xy1 = {pX - pW / 2, pY - pH / 2};
    double[] xy2 = {pX + pW / 2, pY - pH / 2};
    double[] xy3 = {pX + pW / 2, pY + pH / 2};
    double[] xy4 = {pX - pW / 2, pY + pH / 2};

    at.transform(xy1, 0, xy1, 0, xy1.length / 2);
    at.transform(xy2, 0, xy2, 0, xy2.length / 2);
    at.transform(xy3, 0, xy3, 0, xy3.length / 2);
    at.transform(xy4, 0, xy4, 0, xy4.length / 2);

    xMin = Math.min(Math.min((int) xy1[0], (int) xy2[0]), Math.min((int) xy3[0], (int) xy4[0]));
    yMin = Math.min(Math.min((int) xy1[1], (int) xy2[1]), Math.min((int) xy3[1], (int) xy4[1]));

    xMax = Math.max(Math.max((int) xy1[0], (int) xy2[0]), Math.max((int) xy3[0], (int) xy4[0]));
    yMax = Math.max(Math.max((int) xy1[1], (int) xy2[1]), Math.max((int) xy3[1], (int) xy4[1]));

    int xBeg = (int) Math.floor/**/((double) xMin / cW);
    int yBeg = (int) Math.floor/**/((double) yMin / cH);
    int xEnd = (int) Math.ceil/* */((double) xMax / cW);
    int yEnd = (int) Math.ceil/* */((double) yMax / cH);

    g2d.setColor(Color.WHITE);
    g2d.drawLine((int) xy1[0], (int) xy1[1], (int) xy2[0], (int) xy2[1]);
    g2d.drawLine((int) xy2[0], (int) xy2[1], (int) xy3[0], (int) xy3[1]);
    g2d.drawLine((int) xy3[0], (int) xy3[1], (int) xy4[0], (int) xy4[1]);
    g2d.drawLine((int) xy4[0], (int) xy4[1], (int) xy1[0], (int) xy1[1]);

    g2d.setColor(Color.YELLOW);
    g2d.drawRect(xMin, yMin, xMax - xMin, yMax - yMin);

    //    inside(new double[]{xy1[0], xy1[1], xy2[0], xy2[1], xy3[0], xy3[1], xy4[0], xy4[1], xy1[0], xy1[1]});

    g2d.setColor(Color.RED);
    g2d.drawRect(xBeg * cW, yBeg * cH, (xEnd - xBeg) * cW, (yEnd - yBeg) * cH);
  }

  public boolean inside(double[] points) {

    System.err.println("***************");
    for (int i = 2; (i + 1) < points.length; i += 2) {
      Line2D line = new Line2D.Double(points[i - 2], points[i - 1], points[i], points[i + 1]);
      //System.err.println(line);
    }

    return true;
  }
}
