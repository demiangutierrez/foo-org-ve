package org.cyrano.pathfollow;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.cyrano.util.misc.Hwh;

public class Sprite {

  private List<BufferedImage> bimgList = new ArrayList<BufferedImage>();

  private int index;

  private double x;
  private double y;

  private double stepsPerSecond = 2;
  private double dr;

  private Point goingto;

  // --------------------------------------------------------------------------------

  public Sprite() {
    try {
      bimgList.add(ImageIO.read(getClass().getResource("xpac1.png")));
      bimgList.add(ImageIO.read(getClass().getResource("xpac2.png")));
      bimgList.add(ImageIO.read(getClass().getResource("xpac3.png")));
      bimgList.add(ImageIO.read(getClass().getResource("xpac4.png")));
      bimgList.add(ImageIO.read(getClass().getResource("xpac5.png")));
      bimgList.add(ImageIO.read(getClass().getResource("xpac6.png")));
      bimgList.add(ImageIO.read(getClass().getResource("xpac5.png")));
      bimgList.add(ImageIO.read(getClass().getResource("xpac4.png")));
      bimgList.add(ImageIO.read(getClass().getResource("xpac3.png")));
      bimgList.add(ImageIO.read(getClass().getResource("xpac2.png")));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  // --------------------------------------------------------------------------------

  public Point getGoingto() {
    return goingto;
  }

  public void setGoingto(Point goingto) {
    this.goingto = goingto;
  }

  // --------------------------------------------------------------------------------

  public void setX(double x) {
    this.x = x;
  }

  public double getX() {
    return x;
  }

  // --------------------------------------------------------------------------------

  public void setY(double y) {
    this.y = y;
  }

  public double getY() {
    return y;
  }

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
    AffineTransform prev = g2d.getTransform();

    BufferedImage curr = bimgList.get(index % bimgList.size());

    int w = Hwh.getW(curr);
    int h = Hwh.getH(curr);

    // Perform the rotation so x-axis is aligned with direction vector
    AffineTransform at = AffineTransform.getRotateInstance( //
        goingto.x - x, goingto.y - y, x, y);
    g2d.transform(at);

    g2d.drawImage(curr, (int) (x - w / 2), (int) (y - h / 2), null);

    g2d.setTransform(prev);
  }
}
