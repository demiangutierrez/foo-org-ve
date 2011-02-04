package boxcollision2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.util.Hwh;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel implements Runnable {

  private boolean running = true;

  private List<Box> drawBoxList = new ArrayList<Box>();
  private List<Box> doneBoxList = new ArrayList<Box>();

  // --------------------------------------------------------------------------------

  public GamePanel() {
    Box box;

    box = new Box(/* */31, /* */30, 100, 100, /* */40, /**/30);
    drawBoxList.add(box);

    box = new Box(/**/230, /**/430, 100, 100, /**/-40, /**/-50);
    drawBoxList.add(box);

//    box = new Box(/* */30, /* */30, 100, 100, /**/0, /* */50);
//    drawBoxList.add(box);
//
//    box = new Box(/* */30, /**/430, 100, 100, /**/0, /**/-50);
//    drawBoxList.add(box);

    Thread th = new Thread(this);
    th.start();
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
    synchronized (drawBoxList) {
      while (!drawBoxList.isEmpty()) {
        Box curBox = drawBoxList.remove(0);
        Box colBox = null;

        double minTtc = Double.MAX_VALUE;

        for (Box tgtBox : drawBoxList) {
          double curTtc = Box.timeToCollide(curBox, tgtBox);

          if (curTtc < minTtc) {
            minTtc = curTtc;
            colBox = curBox;
          }
        }

        doneBoxList.add(curBox);

        System.err.println(dt + ";" + minTtc);
        if (minTtc < dt) {
          running = false;

          dt = minTtc;
        }
      }

      for (Box box : doneBoxList) {
        box.updatePos(dt);
      }
      List<Box> aux = doneBoxList;
      doneBoxList = drawBoxList;
      drawBoxList = aux;
      //    running = false;
      System.err.println("bye updateAll");
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

  //  public void drawBox(Graphics2D g2d, Rectangle box, Point vBox, Color color, int dstAxis) {
  //    g2d.setStroke(new BasicStroke(1));
  //    g2d.setColor(color);
  //    g2d.draw(box);
  //
  //    g2d.setColor(color.darker());
  //    g2d.setStroke(new BasicStroke(2));
  //    g2d.drawLine(//
  //        (int) box.getCenterX()/*     */, (int) box.getCenterY()/*      */, //
  //        (int) box.getCenterX() + vBox.x, (int) box.getCenterY() + vBox.y);
  //
  //    g2d.setColor(color);
  //    g2d.setStroke(new BasicStroke(3));
  //
  //    Point boxOverX = getBoxOverX(box);
  //    g2d.drawLine(boxOverX.x, 0, boxOverX.y, 0);
  //
  //    Point boxOverY = getBoxOverY(box);
  //    g2d.drawLine(0, boxOverY.x, 0, boxOverY.y);
  //
  //    g2d.drawLine(//
  //        (int) box.getCenterX()/*     */, dstAxis/*      */, //
  //        (int) box.getCenterX() + vBox.x, dstAxis);
  //
  //    g2d.drawLine(//
  //        dstAxis, (int) box.getCenterY()/*     */, //
  //        dstAxis, (int) box.getCenterY() + vBox.y);
  //  }

  @Override
  public void update(Graphics g) {
    synchronized (drawBoxList) {
      Graphics2D g2d = (Graphics2D) g;

      g2d.setBackground(Color.WHITE);
      g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

      for (Box box : drawBoxList) {
        g2d.drawRect((int) box.cx, (int) box.cy, (int) box.bw, (int) box.bh);
      }

      //    AffineTransform prevAt = g2d.getTransform();
      //    g2d.transform(AffineTransform.getTranslateInstance(10, 10));
      //
      //    g2d.setColor(Color.BLACK);
      //    g2d.drawLine(0, 0, 0, Hwh.getH(this));
      //    g2d.drawLine(0, 0, Hwh.getW(this), 0);
      //
      //    drawBox(g2d, box1, vBox1, Color.RED, 5);
      //    drawBox(g2d, box2, vBox2, Color.BLUE, 10);
      //
      //    Point2D crashX = calcTimesToPotImpactX(box1, box2, vBox1, vBox2);
      //    Point2D crashY = calcTimesToPotImpactY(box1, box2, vBox1, vBox2);
      //
      //    //    Rectangle boxC1 = new Rectangle(box1);
      //    //    boxC1.x = (int)(boxC1.x + vBox1.x * crash.getX());
      //    //    boxC1.y = (int)(boxC1.y + vBox1.y * crash.getX());
      //    //    drawBox(g2d, boxC1, vBox1, Color.GREEN, 10);
      //    //
      //    //    Rectangle boxC2 = new Rectangle(box2);
      //    //    boxC2.x = (int)(boxC2.x + vBox2.x * crash.getX());
      //    //    boxC2.y = (int)(boxC2.y + vBox2.y * crash.getX());
      //    //    drawBox(g2d, boxC2, vBox2, Color.GREEN, 10);
      //    //
      //    //
      //    //    boxC1 = new Rectangle(box1);
      //    //    boxC1.x = (int)(boxC1.x + vBox1.x * crash.getY());
      //    //    boxC1.y = (int)(boxC1.y + vBox1.y * crash.getY());
      //    //    drawBox(g2d, boxC1, vBox1, Color.CYAN, 10);
      //    //
      //    //    boxC2 = new Rectangle(box2);
      //    //    boxC2.x = (int)(boxC2.x + vBox2.x * crash.getY());
      //    //    boxC2.y = (int)(boxC2.y + vBox2.y * crash.getY());
      //    //    drawBox(g2d, boxC2, vBox2, Color.CYAN, 10);
      //
      //    double[] overlay = calcOverlayArray(crashX, crashY);
      //
      //    Rectangle boxC1 = new Rectangle(box1);
      //    boxC1.x = (int) (boxC1.x + vBox1.x * overlay[1]);
      //    boxC1.y = (int) (boxC1.y + vBox1.y * overlay[1]);
      //    drawBox(g2d, boxC1, vBox1, Color.GREEN, 10);
      //
      //    Rectangle boxC2 = new Rectangle(box2);
      //    boxC2.x = (int) (boxC2.x + vBox2.x * overlay[1]);
      //    boxC2.y = (int) (boxC2.y + vBox2.y * overlay[1]);
      //    drawBox(g2d, boxC2, vBox2, Color.GREEN, 10);
      //
      //    boxC1 = new Rectangle(box1);
      //    boxC1.x = (int) (boxC1.x + vBox1.x * overlay[2]);
      //    boxC1.y = (int) (boxC1.y + vBox1.y * overlay[2]);
      //    drawBox(g2d, boxC1, vBox1, Color.CYAN, 10);
      //
      //    boxC2 = new Rectangle(box2);
      //    boxC2.x = (int) (boxC2.x + vBox2.x * overlay[2]);
      //    boxC2.y = (int) (boxC2.y + vBox2.y * overlay[2]);
      //    drawBox(g2d, boxC2, vBox2, Color.CYAN, 10);
      //
      //    g2d.setTransform(prevAt);
    }
  }
}
