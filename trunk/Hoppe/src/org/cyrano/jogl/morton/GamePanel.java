package org.cyrano.jogl.morton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import org.cyrano.util.Hwh;
import org.cyrano.util.PointInt;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel {

  private List<PointInt> pointDblList;

  private PointInt dragPoint;

  private int dx;
  private int dy;

  // --------------------------------------------------------------------------------

  private static final int RAND_POINT_INSET = 20;

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

      @Override
      public void mouseClicked/* */(MouseEvent evt) {
        GamePanel.this.mouseClicked/* */(evt);
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged/* */(MouseEvent evt) {
        GamePanel.this.mouseDragged/* */(evt);
      }
    });
  }

  public void sort() {

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

    if (pointDblList == null) {
      initPointDblList();
    }

    Graphics2D g2d = (Graphics2D) g;

    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    PointInt prevPoint = null;

    for (PointInt currPoint : pointDblList) {
      g2d.setColor(Color.YELLOW);
      g2d.drawOval(currPoint.getIX() - 5, currPoint.getIY() - 5, 10, 10);

      if (prevPoint != null) {
        g2d.setColor(Color.GREEN);
        g2d.drawLine( //
            prevPoint.getIX(), prevPoint.getIY(), //
            currPoint.getIX(), currPoint.getIY());
      }

      prevPoint = currPoint;
    }
  }

  private void initPointDblList() {
    pointDblList = new ArrayList<PointInt>();

    Random r = new Random(0);

    for (int i = 0; i < 100; i++) {
      int x = (int) (r.nextDouble() * (Hwh.getW(this) - 2 * RAND_POINT_INSET) + RAND_POINT_INSET);
      int y = (int) (r.nextDouble() * (Hwh.getH(this) - 2 * RAND_POINT_INSET) + RAND_POINT_INSET);
      pointDblList.add(new PointInt(x, y));
    }

    //    for (int i = 10; i <= 1000; i += 50) {
    //      for (int j = 10; j <= 700; j += 50) {
    //        pointDblList.add(new PointInt(i, j));
    //      }
    //    }

    //    for (int i = 0; i < 1280; i += 32) {
    //      for (int j = 0; j < 1024; j += 32) {
    //        pointDblList.add(new PointInt(i, j));
    //      }
    //    }

    sortPointDblList();
  }

  private void sortPointDblList() {
    Collections.sort(pointDblList, new Comparator<PointInt>() {
      @Override
      public int compare(PointInt o1, PointInt o2) {
        int[] io1 = interleave(o1.getIY(), o1.getIX());
        int[] io2 = interleave(o2.getIY(), o2.getIX());

        //        System.err.println( //
        //            /*      */fill(Integer.toBinaryString(io1[0]), '0', 32) + //
        //                "-" + fill(Integer.toBinaryString(io1[1]), '0', 32));
        //
        //        System.err.println( //
        //            /*      */fill(Integer.toBinaryString(io2[0]), '0', 32) + //
        //                "-" + fill(Integer.toBinaryString(io2[1]), '0', 32));

        if (io1[0] != io2[0]) {
          return io1[0] - io2[0];
        }

        return io1[1] - io2[1];
      }
    });
  }

  // --------------------------------------------------------------------------------
  // Mouse handling
  // --------------------------------------------------------------------------------

  private void mousePressed(MouseEvent evt) {
    for (PointInt pointDbl : pointDblList) {
      Rectangle r = new Rectangle( //
          pointDbl.getIX() - 5, pointDbl.getIY() - 5, 10, 10);

      if (r.contains(evt.getPoint())) {
        dx = evt.getPoint().x - pointDbl.getIX();
        dy = evt.getPoint().y - pointDbl.getIY();
        dragPoint = pointDbl;
        return;
      }
    }

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

    sortPointDblList();

    repaint();
  }

  protected void mouseClicked(MouseEvent evt) {
    for (PointInt pointDbl : pointDblList) {
      Rectangle r = new Rectangle( //
          pointDbl.getIX() - 5, pointDbl.getIY() - 5, 10, 10);

      if (r.contains(evt.getPoint())) {

        int[] io1 = interleave(pointDbl.getIY(), pointDbl.getIX());

        System.err.println(pointDbl.getIX() + ";" + pointDbl.getIY());
        System.err.println( //
            /*      */fill(Integer.toBinaryString(io1[0]), '0', 32) + //
                "-" + fill(Integer.toBinaryString(io1[1]), '0', 32));
      }
    }
  }

  public static String fill(String str, char fill, int size) {
    String ret = str;

    for (int i = 0; i < size - str.length(); i++) {
      ret = fill + ret;
    }

    return ret;
  }

  public static class DoubleLong {
    long l1;
    long l2;
  }

  public long fillLong() {
    return 0;
  }

  public static long[] interleave(long al, long bl) {
    long[] ret = new long[2];

    for (int i = 0; i < Long.SIZE / 2; i++) {
      ret[0] >>>= 1;
      ret[0] |= (al & 0x8000000000000000l);
      al <<= 1;

      ret[0] >>>= 1;
      ret[0] |= (bl & 0x8000000000000000l);
      bl <<= 1;
    }

    for (int i = 0; i < Long.SIZE / 2; i++) {
      ret[1] >>>= 1;
      ret[1] |= (al & 0x8000000000000000l);
      al <<= 1;

      ret[1] >>>= 1;
      ret[1] |= (bl & 0x8000000000000000l);
      bl <<= 1;
    }

    return ret;
  }

  public static int[] interleave(int al, int bl) {
    int[] ret = new int[2];

    for (int i = 0; i < Integer.SIZE / 2; i++) {
      ret[0] <<= 1;
      ret[0] |= ((al & 0x80000000) >>> (Integer.SIZE - 1));
      al <<= 1;

      ret[0] <<= 1;
      ret[0] |= ((bl & 0x80000000) >>> (Integer.SIZE - 1));
      bl <<= 1;
    }

    for (int i = 0; i < Integer.SIZE / 2; i++) {
      ret[1] <<= 1;
      ret[1] |= ((al & 0x80000000) >>> (Integer.SIZE - 1));
      al <<= 1;

      ret[1] <<= 1;
      ret[1] |= ((bl & 0x80000000) >>> (Integer.SIZE - 1));
      bl <<= 1;
    }

    return ret;
  }

  public static void main(String[] args) {
    int a = 0x00000000;
    int b = 0xFFFFFFFF;

    int[] val = interleave(a, b);

    System.err.println("a: " + fill(Integer.toBinaryString(a), '0', 32));
    System.err.println("b: " + fill(Integer.toBinaryString(b), '0', 32));

    System.err.println("0: " + fill(Integer.toBinaryString(val[0]), '0', 32));
    System.err.println("1: " + fill(Integer.toBinaryString(val[1]), '0', 32));
  }
}
