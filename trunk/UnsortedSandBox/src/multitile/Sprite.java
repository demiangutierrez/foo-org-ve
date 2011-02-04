package multitile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import org.cyrano.util.PointDbl;
import org.cyrano.util.PointInt;

public class Sprite {

  //  private List<BufferedImage> bimgList = new ArrayList<BufferedImage>();

  private int index;

  protected PointDbl scrCurr = new PointDbl();
  protected PointDbl scrNext = new PointDbl();
  protected PointDbl scrLook = new PointDbl();

  protected PointInt grdCurr = new PointInt();
  protected PointInt grdNext = new PointInt();

  protected int speed = 75;
  //  protected boolean moving = false;

  //  private double currX;
  //  private double currY;
  //
  //  private int gridX;
  //  private int gridY;

  private int w;
  private int h;

  private double stepsPerSecond = 2;
  private double dr;

  private Point goingto;

  private int direction;

  // --------------------------------------------------------------------------------

  public Sprite() {
    w = 3;
    h = 3;
    //    try {
    //      bimgList.add(ImageIO.read(getClass().getResource("xpac1.png")));
    //      bimgList.add(ImageIO.read(getClass().getResource("xpac2.png")));
    //      bimgList.add(ImageIO.read(getClass().getResource("xpac3.png")));
    //      bimgList.add(ImageIO.read(getClass().getResource("xpac4.png")));
    //      bimgList.add(ImageIO.read(getClass().getResource("xpac5.png")));
    //      bimgList.add(ImageIO.read(getClass().getResource("xpac6.png")));
    //      bimgList.add(ImageIO.read(getClass().getResource("xpac5.png")));
    //      bimgList.add(ImageIO.read(getClass().getResource("xpac4.png")));
    //      bimgList.add(ImageIO.read(getClass().getResource("xpac3.png")));
    //      bimgList.add(ImageIO.read(getClass().getResource("xpac2.png")));
    //    } catch (IOException e) {
    //      throw new RuntimeException(e);
    //    }
  }

  // --------------------------------------------------------------------------------

  public Point getGoingto() {
    return goingto;
  }

  public void setGoingto(Point goingto) {
    this.goingto = goingto;
  }

  // --------------------------------------------------------------------------------

  public void setX(int x) {
    grdCurr.x = x;
    grdNext.x = x;
    scrCurr.x = x * MultiPanel.TILE_W;
    scrNext.x = x * MultiPanel.TILE_W;
  }

  //  public double getX() {
  //    return currX;
  //  }

  // --------------------------------------------------------------------------------

  public void setY(int y) {
    grdCurr.y = y;
    grdNext.y = y;
    scrCurr.y = y * MultiPanel.TILE_H;
    scrNext.y = y * MultiPanel.TILE_H;
  }

  //  public double getY() {
  //    return currY;
  //  }

  // --------------------------------------------------------------------------------

  public void update(double dt) {
    int st;

    dr += dt;

    st = (int) (dr * stepsPerSecond);
    dr = dr * stepsPerSecond - st;

    index += st;
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics2D g2d) {

    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        g2d.setColor(Color.RED.darker());
        g2d.fillRect(//
            (int) (scrCurr.x + x * MultiPanel.TILE_W), //
            (int) (scrCurr.y + y * MultiPanel.TILE_H), //
            MultiPanel.TILE_W, MultiPanel.TILE_H);
        g2d.setColor(Color.GREEN);
        g2d.drawRect(//
            (int) (scrCurr.x + x * MultiPanel.TILE_W), //
            (int) (scrCurr.y + y * MultiPanel.TILE_H), //
            MultiPanel.TILE_W, MultiPanel.TILE_H);
      }
    }
  }

  // --------------------------------------------------------------------------------

  public boolean needToMove() {
    return scrCurr.x != scrNext.x || scrCurr.y != scrNext.y;
  }

  public void updatePos(double dt) {
    if (!needToMove()) {
      System.err.println("xxxxx");
      internalCalcNext(direction);
    }

    //    if (!needToMove()) {
    //      return;
    //    }

    while (dt > 0 && needToMove()) {
      double dst2go = speed * dt;

      double dstTgt = Math.sqrt( //
          /**/(scrCurr.x - scrNext.x) * (scrCurr.x - scrNext.x) + //
              (scrCurr.y - scrNext.y) * (scrCurr.y - scrNext.y));

      // If I've reached the target...
      // Where do I go now?
      if (dstTgt == 0) {
        System.err.println("yyyyy");
        internalCalcNext(direction);
        continue;
        // break;
      }

      if (dstTgt < dst2go) {
        dt -= dstTgt / speed;

        scrCurr.x = scrNext.x;
        scrCurr.y = scrNext.y;

        grdCurr.x = grdNext.x;
        grdCurr.y = grdNext.y;

        // If I've reached the target...
        // Where do I go now?
        System.err.println("zzzzz");
        internalCalcNext(direction);
        //        internalExecNext();
      } else {
        double dx = (scrNext.x - scrCurr.x) / dstTgt;
        double dy = (scrNext.y - scrCurr.y) / dstTgt;

        scrCurr.x += speed * dx * dt;
        scrCurr.y += speed * dy * dt;

        dt = 0;
      }
    }
  }

  // --------------------------------------------------------------------------------

  protected void internalCalcNext(int direction) {
    System.err.println("internalCalcNext: " + direction);

    if (direction == MultiPanel.NO) {
      return;
    }

    switch (direction) {
      case MultiPanel.LF :
        grdNext.x = grdCurr.x - 1;
        grdNext.y = grdCurr.y;
        break;
      case MultiPanel.RG :
        grdNext.x = grdCurr.x + 1;
        grdNext.y = grdCurr.y;
        break;
      case MultiPanel.UP :
        grdNext.x = grdCurr.x;
        grdNext.y = grdCurr.y - 1;
        break;
      case MultiPanel.DW :
        grdNext.x = grdCurr.x;
        grdNext.y = grdCurr.y + 1;
        break;
    }

    scrCurr.x = grdCurr.x * MultiPanel.TILE_W;
    scrCurr.y = grdCurr.y * MultiPanel.TILE_H;

    scrNext.x = grdNext.x * MultiPanel.TILE_W;
    scrNext.y = grdNext.y * MultiPanel.TILE_H;

    //    moving = grdCurr.x != grdNext.x || grdCurr.y != grdNext.y;
  }

  public void setDirection(int direction) {
    System.err.println("setDirection: " + direction);
    this.direction = direction;
  }

}
