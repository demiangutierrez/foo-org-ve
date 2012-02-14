package org.cyrano.space.quadtree2;

import java.util.ArrayList;
import java.util.List;

import org.cyrano.util.geometry.PointDbl;

public class Line {

  public PointDbl p1;
  public PointDbl p2;

  public Line(PointDbl p1, PointDbl p2) {
    this.p1 = p1;
    this.p2 = p2;
  }

  public List<PointDbl> getPointDblList() {
    List<PointDbl> ret = new ArrayList<PointDbl>();

    ret.add(p1);
    ret.add(p2);

    return ret;
  }
}
