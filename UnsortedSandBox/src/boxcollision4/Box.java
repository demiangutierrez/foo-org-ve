package boxcollision4;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Box {

  public enum Side {
    LFT, RGH, TOP, BOT
  }

  public enum Mark {
    BEGX, ENDX, BEGY, ENDY
  }

  public boolean/**/mv;

  public double/* */cx;
  public double/* */cy;

  public double/* */bw;
  public double/* */bh;

  public double/* */vx;
  public double/* */vy;

  public double/* */ax;
  public double/* */ay;

  public Color color;

  public String id;

  // --------------------------------------------------------------------------------

  public Box() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void updatePos(double dt) {
    if (!mv) {
      return;
    }

    cx = cx + vx * dt;
    cy = cy + vy * dt;
  }

  // --------------------------------------------------------------------------------

  public static void timeToCollide(CollisionInfo collisionInfo) {
    Box box1 = collisionInfo.box1;
    Box box2 = collisionInfo.box2;

    System.err.println( //
        /**/(int) box1.cx + ";" + (int) (box1.cx + box1.bw) + //
            "*X*" + //
            (int) box2.cx + ";" + (int) (box2.cx + box2.bw));
    System.err.println( //
        /**/(int) box1.cy + ";" + (int) (box1.cy + box1.bh) + //
            "*Y*" + //
            (int) box2.cy + ";" + (int) (box2.cy + box2.bh));

    Point2D crashX = calcTimesToPotImpactX(box1, box2);
    Point2D crashY = calcTimesToPotImpactY(box1, box2);

    MarkTimeBean[] overlay;

    System.err.println("crashX, crashY: " + crashX + "..." + crashY);

    if (crashX == null || crashY == null) {
      collisionInfo.time = Double.MAX_VALUE;
      return;
    }

    overlay = calcOverlayArray(crashX, crashY);

    if (overlay == null) {
      collisionInfo.time = Double.MAX_VALUE;
      return;
    }

    System.err.println(overlay[0] + ";->" + overlay[1] + "<-;" + overlay[2] + ";" + overlay[3]);

    collisionInfo.time = overlay[1].time;

    switch (overlay[1].mark) {
      case BEGX :
        if (box1.cx < box2.cx) {
          collisionInfo.box1Side = Side.RGH;
          collisionInfo.box2Side = Side.LFT;
        } else {
          collisionInfo.box1Side = Side.LFT;
          collisionInfo.box2Side = Side.RGH;
        }
        break;
      case BEGY :
        if (box1.cy < box2.cy) {
          collisionInfo.box1Side = Side.BOT;
          collisionInfo.box2Side = Side.TOP;
        } else {
          collisionInfo.box1Side = Side.TOP;
          collisionInfo.box2Side = Side.BOT;
        }
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private static Point getBoxOverX(Box box) {
    return new Point((int) box.cx, (int) (box.cx + box.bw));
  }

  // --------------------------------------------------------------------------------

  private static Point getBoxOverY(Box box) {
    return new Point((int) box.cy, (int) (box.cy + box.bh));
  }

  // --------------------------------------------------------------------------------

  private static Point2D calcTimesToPotImpactX(Box box1, Box box2) {
    Point bp1 = getBoxOverX(box1);
    Point bp2 = getBoxOverX(box2);

    int vRel;

    if (bp1.getX() >= bp2.getX() && bp1.getX() <= bp2.getY() || //
        bp1.getY() >= bp2.getX() && bp1.getY() <= bp2.getY()) {
      //return null;
      return new Point2D.Double(0, Double.MAX_VALUE);
    }

    if (box1.cx < box2.cx) {
      vRel = (int) (box1.vx - box2.vx);
    } else {
      vRel = (int) (box2.vx - box1.vx);
    }

    System.err.println("*****vREL X" + vRel);

    if (vRel < 0) {
      return null; // will never crash
    }

    int distCrash1 = Math.abs(bp1.x - bp2.y) - 1;
    int distCrash2 = Math.abs(bp1.y - bp2.x) - 1;

    int distCrashBeg = Math.min(distCrash1, distCrash2);
    int distCrashEnd = Math.max(distCrash1, distCrash2);

    //    if (vRel == 0) {
    //      return new Point2D.Double(0, Double.MAX_VALUE);
    //    }

    return new Point2D.Double( //
        (distCrashBeg / (double) vRel), distCrashEnd / (double) vRel);
  }

  // --------------------------------------------------------------------------------

  private static Point2D calcTimesToPotImpactY(Box box1, Box box2) {
    Point bp1 = getBoxOverY(box1);
    Point bp2 = getBoxOverY(box2);

    int vRel;

    if (bp1.getX() >= bp2.getX() && bp1.getX() <= bp2.getY() || //
        bp1.getY() >= bp2.getX() && bp1.getY() <= bp2.getY()) {
      //return null;
      return new Point2D.Double(0, Double.MAX_VALUE);
    }

    if (box1.cy < box2.cy) {
      vRel = (int) (box1.vy - box2.vy);
    } else {
      vRel = (int) (box2.vy - box1.vy);
    }

    System.err.println("*****vREL Y" + vRel);

    if (vRel < 0) {
      return null; // will never crash
    }

    int distCrash1 = Math.abs(bp1.x - bp2.y) - 1;
    int distCrash2 = Math.abs(bp1.y - bp2.x) - 1;

    int distCrashBeg = Math.min(distCrash1, distCrash2);
    int distCrashEnd = Math.max(distCrash1, distCrash2);

    //    if (vRel == 0) {
    //      return new Point2D.Double(0, Double.MAX_VALUE);
    //    }

    return new Point2D.Double( //
        (distCrashBeg / (double) vRel), distCrashEnd / (double) vRel);
  }

  // --------------------------------------------------------------------------------

  private static MarkTimeBean[] calcOverlayArray(Point2D crashX, Point2D crashY) {
    if ((crashX.getX() < crashY.getX() || crashX.getX() > crashY.getY()) && // 
        (crashX.getY() < crashY.getX() || crashX.getY() > crashY.getY())) {
      return null;
    }

    MarkTimeBean[] ret = new MarkTimeBean[]{ //
    new MarkTimeBean(crashX.getX(), Mark.BEGX), new MarkTimeBean(crashX.getY(), Mark.ENDX), //
        new MarkTimeBean(crashY.getX(), Mark.BEGY), new MarkTimeBean(crashY.getY(), Mark.ENDY)};

    // ----------------------------------------
    // A primitive sort...
    // ----------------------------------------

    boolean sorted = true;

    while (sorted) {
      sorted = false;

      for (int i = 0; i < ret.length - 1; i++) {
        if (ret[i].time > ret[i + 1].time) {
          MarkTimeBean aux;

          aux/*    */= ret[i + 1];
          ret[i + 1] = ret[i/**/];
          ret[i/**/] = aux;

          sorted = true;
        }
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static class CollisionInfo {

    // ----------------------------------------

    public Side/*  */box1Side;
    public Box/*   */box1;

    public Side/*  */box2Side;
    public Box/*   */box2;

    // ----------------------------------------

    public double/**/time;
  }

  // --------------------------------------------------------------------------------

  private static class MarkTimeBean {

    public double/**/time;
    public Mark/*  */mark;

    public MarkTimeBean(double time, Mark mark) {
      this.time = time;
      this.mark = mark;
    }

    @Override
    public String toString() {
      return time + ";" + mark;
    }
  }
}
