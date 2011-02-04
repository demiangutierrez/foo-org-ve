package boxcollision2;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Box {

  public double cx;
  public double cy;

  public double bw;
  public double bh;

  public double vx;
  public double vy;

  // --------------------------------------------------------------------------------

  public Box(int cx, int cy, int bw, int bh, int vx, int vy) {
    this.cx = cx;
    this.cy = cy;
    this.bw = bw;
    this.bh = bh;
    this.vx = vx;
    this.vy = vy;
  }

  // --------------------------------------------------------------------------------

  public void updatePos(double dt) {
    cx = cx + vx * dt;
    cy = cy + vy * dt;
  }

  // --------------------------------------------------------------------------------

  public static double timeToCollide(Box box1, Box box2) {
    System.err.println( //
        /**/(int)box1.cx + ";" + (int)(box1.cx + box1.bw) + //
            "*X*" + //
            (int)box2.cx + ";" + (int)(box2.cx + box2.bw));
    System.err.println( //
        /**/(int)box1.cy + ";" + (int)(box1.cy + box1.bh) + //
            "*Y*" + //
            (int)box2.cy + ";" + (int)(box2.cy + box2.bh));
    
    if ((box1.cx + box1.bw) >= box2.cx) {
      System.err.println("MARK");
    }

    Point2D crashX = calcTimesToPotImpactX(box1, box2);
    Point2D crashY = calcTimesToPotImpactY(box1, box2);

    double[] overlay;

    System.err.println("crashX, crashY: " + crashX + "..." + crashY);
    if (crashX == null || crashY == null) {
      return Double.MAX_VALUE;
    }

    overlay = calcOverlayArray(crashX, crashY);

    if (overlay == null) {
      return Double.MAX_VALUE;
    }
    System.err.println(overlay[0] + ";->" + overlay[1] + "<-;" + overlay[2] + ";" + overlay[3]);
    return overlay[1];
  }

  private static Point getBoxOverX(Box box) {
    return new Point((int) box.cx, (int) (box.cx + box.bw));
  }

  private static Point getBoxOverY(Box box) {
    return new Point((int) box.cy, (int) (box.cy + box.bh));
  }

  private static Point2D calcTimesToPotImpactX(Box box1, Box box2) {
    Point bp1 = getBoxOverX(box1);
    Point bp2 = getBoxOverX(box2);

    int vRel;

    if (bp1.getX() >= bp2.getX() && bp1.getX() <= bp2.getY() || //
        bp1.getY() >= bp2.getX() && bp1.getX() <= bp2.getY() || //
        bp2.getX() >= bp1.getX() && bp2.getX() <= bp1.getY() || //
        bp2.getY() >= bp1.getX() && bp2.getX() <= bp1.getY()) {
      return new Point2D.Double(0, Double.MAX_VALUE);
    }
    
    if ((box1.cx + box1.bw) < (box2.cx)) {
      vRel = (int) (box1.vx - box2.vx);
    } else {
      return null;
//      vRel = (int) (box2.vy - box1.vy);
    }

    System.err.println("*****X" + vRel);

    if (vRel < 0) {
      return null; // will never crash
    }

    int distCrash1 = Math.abs(bp1.x - bp2.y);
    int distCrash2 = Math.abs(bp1.y - bp2.x);

    int distCrashBeg = Math.min(distCrash1, distCrash2);
    int distCrashEnd = Math.max(distCrash1, distCrash2);

    if (vRel == 0) {
      return new Point2D.Double(0, Double.MAX_VALUE);
    }

    return new Point2D.Double((distCrashBeg / (double) vRel), distCrashEnd / (double) vRel);
  }

  private static Point2D calcTimesToPotImpactY(Box box1, Box box2) {
    Point bp1 = getBoxOverY(box1);
    Point bp2 = getBoxOverY(box2);

    int vRel;

    if ((box1.cy + box1.bh) < (box2.cy)) {
      vRel = (int) (box1.vy - box2.vy);
    } else {
      return null;
//      vRel = (int) (box2.vy - box1.vy);
    }

    System.err.println("*****Y" + vRel);

    if (vRel < 0) {
      return null; // will never crash
    }

    int distCrash1 = Math.abs(bp1.x - bp2.y);
    int distCrash2 = Math.abs(bp1.y - bp2.x);

    int distCrashBeg = Math.min(distCrash1, distCrash2);
    int distCrashEnd = Math.max(distCrash1, distCrash2);

    if (vRel == 0) {
      return new Point2D.Double(0, Double.MAX_VALUE);
    }

    return new Point2D.Double((distCrashBeg / (double) vRel), distCrashEnd / (double) vRel);
  }

  private static double[] calcOverlayArray(Point2D crash1, Point2D crash2) {
    if (crash1.getX() >= crash2.getX() && crash1.getX() <= crash2.getY() || //
        crash1.getY() >= crash2.getX() && crash1.getX() <= crash2.getY() || //
        crash2.getX() >= crash1.getX() && crash2.getX() <= crash1.getY() || //
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
