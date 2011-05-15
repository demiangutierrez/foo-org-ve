package org.cyrano.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import org.cyrano.util.PointAbs;
import org.cyrano.util.PointDbl;

public class Axis {

  public PointDbl pSrc;

  public PointDbl unit;

  // --------------------------------------------------------------------------------

  public Axis() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void initFromSideNor(PointAbs sp1, PointAbs sp2) {
    if (unit != null) {
      throw new IllegalStateException("unitVector != null");
    }

    double midx = (sp2.getDX() - sp1.getDX()) / 2;
    double midy = (sp2.getDY() - sp1.getDY()) / 2;

    pSrc = new PointDbl();

    pSrc.x = sp1.getDX() + midx;
    pSrc.y = sp1.getDY() + midy;

    unit = new PointDbl();

    unit.x = -(sp2.getDY() - sp1.getDY());
    unit.y = +(sp2.getDX() - sp1.getDX());

    double mod = Math.sqrt(unit.x * unit.x + unit.y * unit.y);

    unit.x /= mod;
    unit.y /= mod;
  }

  // --------------------------------------------------------------------------------

  public void initFromSideSeg(PointDbl sp1, PointDbl sp2) {
    if (unit != null) {
      throw new IllegalStateException("unitVector != null");
    }

    throw new UnsupportedOperationException();
  }

  // --------------------------------------------------------------------------------

  public void calcProjectionXY(PointAbs ptp, PointAbs res) {
    double modPtp = ((ptp.getDX() - pSrc.x) * unit.x + (ptp.getDY() - pSrc.y) * unit.y);

    res.setDX(unit.x * modPtp + pSrc.x);
    res.setDY(unit.y * modPtp + pSrc.y);
  }

  // --------------------------------------------------------------------------------

  public void calcProjectionOX(PointAbs ptp, PointAbs res) {
    double modPtp = ((ptp.getDX() - pSrc.x) * unit.x + (ptp.getDY() - pSrc.y) * unit.y);

    res.setDX(modPtp + pSrc.x);
    res.setDY(pSrc.y);
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics2D g2d, PointAbs src, double len, Color color) {
    g2d.setColor(color);
    g2d.setStroke(new BasicStroke(1));

    int tgtX = (int) (src.getIX() + unit.x * len);
    int tgtY = (int) (src.getIY() + unit.y * len);

    g2d.drawLine(src.getIX(), src.getIY(), tgtX, tgtY);
  }
}
