package org.cyrano.boxcollision.test5;

import org.cyrano.axis.Axis;
import org.cyrano.util.geometry.PointAbs;
import org.cyrano.util.geometry.PointDbl;

/**
 * @author Demián Gutierrez
 */
public class AxisProblem {

  public Axis axis;

  // --------------------------------------------------------------------------------

  public Polygon pol1;

  public PointDbl pBegPol1XY = new PointDbl();
  public PointDbl pEndPol1XY = new PointDbl();

  public PointDbl pBegPol1OX = new PointDbl();
  public PointDbl pEndPol1OX = new PointDbl();

  public double pol1VOX;

  // --------------------------------------------------------------------------------

  public Polygon pol2;

  public PointDbl pBegPol2XY = new PointDbl();
  public PointDbl pEndPol2XY = new PointDbl();

  public PointDbl pBegPol2OX = new PointDbl();
  public PointDbl pEndPol2OX = new PointDbl();

  public double pol2VOX;

  // --------------------------------------------------------------------------------

  private void calcProjectionXYForPolygon(Polygon pol, PointAbs pBeg, PointAbs pEnd) {
    boolean begSet = false;
    boolean endSet = false;

    for (PointAbs pointAbs : pol.getSrcPointList()) {
      PointAbs pCur = new PointDbl();

      axis.calcPointProjectionXY(pointAbs, pCur);

      if (!begSet) {
        pBeg.setDX(pCur.getDX());
        pBeg.setDY(pCur.getDY());
        begSet = true;
        continue;
      }

      if (!endSet) {
        pEnd.setDX(pCur.getDX());
        pEnd.setDY(pCur.getDY());
        endSet = true;
        continue;
      }

      double d0 = PointAbs.dist(pBeg, pEnd);
      double d1 = PointAbs.dist(pBeg, pCur);
      double d2 = PointAbs.dist(pCur, pEnd);

      if (d0 < d1 && d2 <= d1) {
        pEnd.setDX(pCur.getDX());
        pEnd.setDY(pCur.getDY());
        continue;
      }

      if (d0 < d2 && d1 <= d2) {
        pBeg.setDX(pCur.getDX());
        pBeg.setDY(pCur.getDY());
        continue;
      }
    }
  }

  // --------------------------------------------------------------------------------

  private void calcProjectionOXForPolygon(Polygon pol, PointAbs pBeg, PointAbs pEnd) {
    boolean begSet = false;
    boolean endSet = false;

    for (PointAbs pointAbs : pol.getSrcPointList()) {
      PointAbs pCur = new PointDbl();

      axis.calcPointProjectionOX(pointAbs, pCur);

      if (!begSet) {
        pBeg.setDX(pCur.getDX());
        //pBeg.setDY(pCur.getDY());
        begSet = true;
        continue;
      }

      if (!endSet) {
        pEnd.setDX(pCur.getDX());
        //pEnd.setDY(pCur.getDY());
        endSet = true;
        continue;
      }

      double d0 = Math.abs(pBeg.getDX() - pEnd.getDX());
      double d1 = Math.abs(pBeg.getDX() - pCur.getDX());
      double d2 = Math.abs(pCur.getDX() - pEnd.getDX());

      if (d0 < d1 && d2 <= d1) {
        pEnd.setDX(pCur.getDX());
        //pEnd.setDY(pCur.getDY());
        continue;
      }

      if (d0 < d2 && d1 <= d2) {
        pBeg.setDX(pCur.getDX());
        //pBeg.setDY(pCur.getDY());
        continue;
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void calcProjection() {
    calcProjectionXYForPolygon(pol1, pBegPol1XY, pEndPol1XY);
    calcProjectionXYForPolygon(pol2, pBegPol2XY, pEndPol2XY);

    calcProjectionOXForPolygon(pol1, pBegPol1OX, pEndPol1OX);
    calcProjectionOXForPolygon(pol2, pBegPol2OX, pEndPol2OX);

    PointDbl vPol1 = new PointDbl();
    vPol1.setIX(pol1.vx);
    vPol1.setIY(pol1.vy);
    PointDbl vRes1 = new PointDbl();
    axis.calcVectorProjectionOX(vPol1, vRes1);
    pol1VOX = vRes1.getDX();

    PointDbl vPol2 = new PointDbl();
    vPol2.setIX(pol2.vx);
    vPol2.setIY(pol2.vy);
    PointDbl vRes2 = new PointDbl();
    axis.calcVectorProjectionOX(vPol2, vRes2);
    pol2VOX = vRes2.getDX();
  }
}
