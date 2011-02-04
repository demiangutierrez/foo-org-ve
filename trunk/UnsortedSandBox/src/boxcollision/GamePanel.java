package boxcollision;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import org.cyrano.util.Hwh;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel {

  private Rectangle box1;
  private Rectangle box2;
  private Point vBox1;
  private Point vBox2;

  // --------------------------------------------------------------------------------

  public GamePanel() {
    box1 = new Rectangle(30, 30, 100, 100);
    box2 = new Rectangle(230, 130, 200, 100);

    vBox1 = new Point(60, 70);
    vBox2 = new Point(-80, -20);
  }

  // --------------------------------------------------------------------------------
  // JPanel
  // --------------------------------------------------------------------------------

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  // --------------------------------------------------------------------------------

  public void drawBox(Graphics2D g2d, Rectangle box, Point vBox, Color color, int dstAxis) {
    g2d.setStroke(new BasicStroke(1));
    g2d.setColor(color);
    g2d.draw(box);

    g2d.setColor(color.darker());
    g2d.setStroke(new BasicStroke(2));
    g2d.drawLine(//
        (int) box.getCenterX()/*     */, (int) box.getCenterY()/*      */, //
        (int) box.getCenterX() + vBox.x, (int) box.getCenterY() + vBox.y);

    g2d.setColor(color);
    g2d.setStroke(new BasicStroke(3));

    Point boxOverX = getBoxOverX(box);
    g2d.drawLine(boxOverX.x, 0, boxOverX.y, 0);

    Point boxOverY = getBoxOverY(box);
    g2d.drawLine(0, boxOverY.x, 0, boxOverY.y);

    g2d.drawLine(//
        (int) box.getCenterX()/*     */, dstAxis/*      */, //
        (int) box.getCenterX() + vBox.x, dstAxis);

    g2d.drawLine(//
        dstAxis, (int) box.getCenterY()/*     */, //
        dstAxis, (int) box.getCenterY() + vBox.y);
  }

  @Override
  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setBackground(Color.WHITE);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    AffineTransform prevAt = g2d.getTransform();
    g2d.transform(AffineTransform.getTranslateInstance(10, 10));

    g2d.setColor(Color.BLACK);
    g2d.drawLine(0, 0, 0, Hwh.getH(this));
    g2d.drawLine(0, 0, Hwh.getW(this), 0);

    drawBox(g2d, box1, vBox1, Color.RED, 5);
    drawBox(g2d, box2, vBox2, Color.BLUE, 10);

    Point2D crashX = calcTimesToPotImpactX(box1, box2, vBox1, vBox2);
    Point2D crashY = calcTimesToPotImpactY(box1, box2, vBox1, vBox2);

    //    Rectangle boxC1 = new Rectangle(box1);
    //    boxC1.x = (int)(boxC1.x + vBox1.x * crash.getX());
    //    boxC1.y = (int)(boxC1.y + vBox1.y * crash.getX());
    //    drawBox(g2d, boxC1, vBox1, Color.GREEN, 10);
    //
    //    Rectangle boxC2 = new Rectangle(box2);
    //    boxC2.x = (int)(boxC2.x + vBox2.x * crash.getX());
    //    boxC2.y = (int)(boxC2.y + vBox2.y * crash.getX());
    //    drawBox(g2d, boxC2, vBox2, Color.GREEN, 10);
    //
    //
    //    boxC1 = new Rectangle(box1);
    //    boxC1.x = (int)(boxC1.x + vBox1.x * crash.getY());
    //    boxC1.y = (int)(boxC1.y + vBox1.y * crash.getY());
    //    drawBox(g2d, boxC1, vBox1, Color.CYAN, 10);
    //
    //    boxC2 = new Rectangle(box2);
    //    boxC2.x = (int)(boxC2.x + vBox2.x * crash.getY());
    //    boxC2.y = (int)(boxC2.y + vBox2.y * crash.getY());
    //    drawBox(g2d, boxC2, vBox2, Color.CYAN, 10);

    double[] overlay = calcOverlayArray(crashX, crashY);

    Rectangle boxC1 = new Rectangle(box1);
    boxC1.x = (int) (boxC1.x + vBox1.x * overlay[1]);
    boxC1.y = (int) (boxC1.y + vBox1.y * overlay[1]);
    drawBox(g2d, boxC1, vBox1, Color.GREEN, 10);

    Rectangle boxC2 = new Rectangle(box2);
    boxC2.x = (int) (boxC2.x + vBox2.x * overlay[1]);
    boxC2.y = (int) (boxC2.y + vBox2.y * overlay[1]);
    drawBox(g2d, boxC2, vBox2, Color.GREEN, 10);

    boxC1 = new Rectangle(box1);
    boxC1.x = (int) (boxC1.x + vBox1.x * overlay[2]);
    boxC1.y = (int) (boxC1.y + vBox1.y * overlay[2]);
    drawBox(g2d, boxC1, vBox1, Color.CYAN, 10);

    boxC2 = new Rectangle(box2);
    boxC2.x = (int) (boxC2.x + vBox2.x * overlay[2]);
    boxC2.y = (int) (boxC2.y + vBox2.y * overlay[2]);
    drawBox(g2d, boxC2, vBox2, Color.CYAN, 10);

    g2d.setTransform(prevAt);
  }

  private Point getBoxOverX(Rectangle box) {
    return new Point((int) box.getMinX(), (int) box.getMaxX());
  }

  private Point getBoxOverY(Rectangle box) {
    return new Point((int) box.getMinY(), (int) box.getMaxY());
  }

  private Point2D calcTimesToPotImpactX(Rectangle b1, Rectangle b2, Point v1, Point v2) {
    Point bp1 = getBoxOverX(b1);
    Point bp2 = getBoxOverX(b2);

    int vRel = v1.x - v2.x;

    if (vRel <= 0) {
      return null; // will never crash
    }

    int distCrash1 = Math.abs(bp1.x - bp2.y);
    int distCrash2 = Math.abs(bp1.y - bp2.x);

    int distCrashBeg = Math.min(distCrash1, distCrash2);
    int distCrashEnd = Math.max(distCrash1, distCrash2);

    return new Point2D.Double((distCrashBeg / (double) vRel), distCrashEnd / (double) vRel);
  }

  private Point2D calcTimesToPotImpactY(Rectangle b1, Rectangle b2, Point v1, Point v2) {
    Point bp1 = getBoxOverY(b1);
    Point bp2 = getBoxOverY(b2);

    int vRel = v1.y - v2.y;

    if (vRel <= 0) {
      return null; // will never crash
    }

    int distCrash1 = Math.abs(bp1.x - bp2.y);
    int distCrash2 = Math.abs(bp1.y - bp2.x);

    int distCrashBeg = Math.min(distCrash1, distCrash2);
    int distCrashEnd = Math.max(distCrash1, distCrash2);

    return new Point2D.Double((distCrashBeg / (double) vRel), distCrashEnd / (double) vRel);
  }

  private double[] calcOverlayArray(Point2D crash1, Point2D crash2) {
    if (crash1.getX() >= crash2.getX() && crash1.getX() <= crash2.getY()
        || //
        crash1.getY() >= crash2.getX() && crash1.getX() <= crash2.getY() || crash2.getX() >= crash1.getX()
        && crash2.getX() <= crash1.getY() || //
        crash2.getY() >= crash1.getX() && crash2.getX() <= crash1.getY()) {

      double[] ret = new double[]{crash1.getX(), crash1.getY(), crash2.getX(), crash2.getY()};

      // A primitive sort...
      boolean sort = true;
      while (sort) {
        sort = false;
        for (int i = 0; i < ret.length - 1; i++) {
          if (ret[i] > ret[i + 1]) {
            double aux = ret[i + 1];
            ret[i + 1] = ret[i];
            ret[i] = aux;
            sort = true;
          }
        }
      }

      return ret;
    }

    return null;
  }
}
