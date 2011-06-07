package org.cyrano.jogl.morton;

import java.util.Comparator;

import org.cyrano.util.PointAbs;
import org.cyrano.util.PointDbl;
import org.cyrano.util.PointInt;

public class MortonComparator implements Comparator<PointAbs> {

  private static final boolean INTERLEAVE = false;

  // --------------------------------------------------------------------------------

  private PointAbs minPoint;

  // --------------------------------------------------------------------------------

  public MortonComparator(PointAbs minPoint) {
    this.minPoint = minPoint;
  }

  // --------------------------------------------------------------------------------
  // Comparator
  // --------------------------------------------------------------------------------

  @Override
  public int compare(PointAbs o1, PointAbs o2) {

    // XXX: cast is temporal...

    if (PointGen.USEINT) {
      if (INTERLEAVE) {
        return compareINT((PointInt) o1, (PointInt) o2);
      } else {
        return compareMSB((PointInt) o1, (PointInt) o2);
      }
    } else {
      return compareMSB((PointDbl) o1, (PointDbl) o2);
    }
  }

  // --------------------------------------------------------------------------------

  private int compareINT(PointInt o1, PointInt o2) {

    int x1 = o1.getIX();
    int y1 = o1.getIY();

    x1 += minPoint.getIX();
    y1 += minPoint.getIY();

    int x2 = o2.getIX();
    int y2 = o2.getIY();

    y2 += minPoint.getIY();
    x2 += minPoint.getIX();

    int[] io1 = MortonUtil.interleave(x1, y1);
    int[] io2 = MortonUtil.interleave(x2, y2);

    return io1[0] != io2[0] ? //
        io1[0] - io2[0]
        : //
        io1[1] - io2[1];
  }

  // --------------------------------------------------------------------------------

  private int compareMSB(PointInt o1, PointInt o2) {

    int[] p1 = new int[2];
    int[] p2 = new int[2];

    p1[0] = o1.getIX();
    p1[1] = o1.getIY();

    p2[0] = o2.getIX();
    p2[1] = o2.getIY();

    return MortonUtil.compare(p1, p2) ? +1 : -1;
  }

  // --------------------------------------------------------------------------------

  private int compareMSB(PointDbl o1, PointDbl o2) {

    double[] p1 = new double[2];
    double[] p2 = new double[2];

    p1[0] = o1.getDX();
    p1[1] = o1.getDY();

    p2[0] = o2.getDX();
    p2[1] = o2.getDY();

    return MortonUtil.compare(p1, p2) ? +1 : -1;
  }
}
