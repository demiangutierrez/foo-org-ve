package org.cyrano.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import org.cyrano.util.MathUtil;
import org.cyrano.util.PointAbs;

public class PointDib {

  public int extX = 5;
  public int extY = 5;

  public int intX = 2;
  public int intY = 2;

  public Color color;

  public PointAbs pointAbs;

  // --------------------------------------------------------------------------------

  public PointDib(PointAbs pointAbs, Color color) {
    this.pointAbs = pointAbs;
    this.color/**/= color;
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics2D g2d) {
    g2d.setColor(color);
    g2d.setStroke(new BasicStroke(1));

    g2d.drawOval(pointAbs.getIX() - extX, pointAbs.getIY() - extY, 2 * extX, 2 * extY);

    g2d.drawLine(pointAbs.getIX() - intX, pointAbs.getIY() - intY, //
        pointAbs.getIX() + intX, pointAbs.getIY() + intY);

    g2d.drawLine(pointAbs.getIX() - intX, pointAbs.getIY() + intY, //
        pointAbs.getIX() + intX, pointAbs.getIY() - intY);
  }

  // --------------------------------------------------------------------------------

  public boolean inside(int x, int y) {
    return //
    /**/MathUtil.betweenC(pointAbs.getIX() - extX, x, pointAbs.getIX() + extX) && //
        MathUtil.betweenC(pointAbs.getIY() - extY, y, pointAbs.getIY() + extY);
  }
}
