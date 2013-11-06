/**
 * @author Demián Gutierrez
 */
package org.cyrano.jogl._15.pick_2;

import java.util.Comparator;

public class RayInfoComparator implements Comparator<RayInfo> {

  public RayInfoComparator() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  /* 
  ** -1 o1  < o2
  **  0 o1 == o2
  ** +1 o1  > o2
  */
  public int compare(RayInfo o1, RayInfo o2) {

    if (o1.inside == false && o2.inside == true) {
      return +1;
    }

    if (o1.inside == true && o2.inside == false) {
      return -1;
    }

    if (o1.t <= 0 && o2.t > 0) {
      return +1;
    }

    if (o2.t <= 0 && o1.t > 0) {
      return -1;
    }

    if (o1.t < o2.t) {
      return -1;
    }

    if (o1.t > o2.t) {
      return +1;
    }

    return 0;
  };
}
