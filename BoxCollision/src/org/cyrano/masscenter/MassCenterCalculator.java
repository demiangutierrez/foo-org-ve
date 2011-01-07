package org.cyrano.masscenter;

import java.awt.Point;
import java.util.List;

import org.cyrano.boxcollision.base.Box;

public class MassCenterCalculator {

  public static Point calcMassCenter(List<Box> boxList, boolean useMass) {
    Point ret = new Point();

    double totMass = 0;
    double curMass = 1;

    for (Box box : boxList) {
      if (useMass) {
        curMass/* */= box.getW() * box.getH();
      }

      totMass/**/+= curMass;

      ret.x += (curMass * (box.maxX() + box.minX()) / 2);
      ret.y += (curMass * (box.maxY() + box.minY()) / 2);
    }

    ret.x /= totMass;
    ret.y /= totMass;

    return ret;
  }
}
