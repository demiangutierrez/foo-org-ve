package org.cyrano.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import org.cyrano.util.PointAbs;
import org.cyrano.util.PointDbl;

/**
 * @author Demián Gutierrez
 */
public class Axis {

  public PointAbs pSrc;

  public PointAbs unit;

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

    pSrc.setDX(sp1.getDX() + midx);
    pSrc.setDY(sp1.getDY() + midy);

    unit = new PointDbl();

    unit.setDX(-(sp2.getDY() - sp1.getDY()));
    unit.setDY(+(sp2.getDX() - sp1.getDX()));

    double mod = Math.sqrt( //
        unit.getDX() * unit.getDX() + unit.getDY() * unit.getDY());

    unit.setDX(unit.getDX() / mod);
    unit.setDY(unit.getDY() / mod);
  }

  // --------------------------------------------------------------------------------

  public void initFromSideSeg(PointDbl sp1, PointDbl sp2) {
    if (unit != null) {
      throw new IllegalStateException("unitVector != null");
    }

    throw new UnsupportedOperationException();
  }

  // --------------------------------------------------------------------------------

  public void calcPointProjectionXY(PointAbs ptp, PointAbs res) {
    double modPtp = //
    /**/(ptp.getDX() - pSrc.getDX()) * unit.getDX() + //
        (ptp.getDY() - pSrc.getDY()) * unit.getDY();

    res.setDX(unit.getDX() * modPtp + pSrc.getDX());
    res.setDY(unit.getDY() * modPtp + pSrc.getDY());
  }

  public void calcPointProjectionOX(PointAbs ptp, PointAbs res) {
    double modPtp = //
    /**/(ptp.getDX() - pSrc.getDX()) * unit.getDX() + //
        (ptp.getDY() - pSrc.getDY()) * unit.getDY();

    res.setDX(modPtp + pSrc.getDX());

    res.setDY(pSrc.getDY()); // could be anything
  }

  public void calcVectorProjectionOX(PointAbs ptp, PointAbs res) {
    double modPtp = //
    /**/(ptp.getDX()) * unit.getDX() + //
        (ptp.getDY()) * unit.getDY();

    res.setDX(modPtp);

    res.setDY(pSrc.getDY()); // could be anything
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics2D g2d, PointAbs src, double len, Color color) {
    g2d.setColor(color);
    g2d.setStroke(new BasicStroke(1));

    int tgtX = (int) (src.getIX() + unit.getDX() * len);
    int tgtY = (int) (src.getIY() + unit.getDY() * len);

    g2d.drawLine(src.getIX(), src.getIY(), tgtX, tgtY);
  }
}
