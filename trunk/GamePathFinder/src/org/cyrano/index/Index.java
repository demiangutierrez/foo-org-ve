/*
 * Created on 12/05/2009
 */
package org.cyrano.index;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Demián Gutierrez
 */
public class Index {

  private Map<Boundable, List<Boundable>> reverseMap = //
  new HashMap<Boundable, List<Boundable>>();

  private List<Boundable>[][] areaArray;

  // --------------------------------------------------------------------------------

  private int areaW;
  private int areaH;

  private int arrayW;
  private int arrayH;

  // --------------------------------------------------------------------------------

  @SuppressWarnings("unchecked")
  public Index(int rectW, int rectH, int areaW, int areaH) {
    this.areaW = areaW;
    this.areaH = areaH;

    arrayW = (int) Math.ceil((double) rectW / areaW);
    arrayH = (int) Math.ceil((double) rectH / areaH);

    areaArray = new List[arrayW][arrayH];
  }

  // --------------------------------------------------------------------------------

  public long time = 0;

  public void create(Boundable boundable) {
    Rectangle bounds = boundable.getBounds();

    List<Boundable> areaList = getArea( //
        (int) bounds.getCenterX(), (int) bounds.getCenterY(), true);

    areaList.add(boundable);

    if (!boundable.isStatic()) {
      reverseMap.put(boundable, areaList);
    }
  }

  // --------------------------------------------------------------------------------

  public void delete(Boundable boundable) {
    if (boundable.isStatic()) {
      throw new IllegalArgumentException("boundable.isStatic()");
    }

    List<Boundable> areaList = reverseMap.get(boundable);

    areaList.remove(boundable);
    reverseMap.remove(boundable);
  }

  // --------------------------------------------------------------------------------

  public void update(Boundable boundable) {
    delete(boundable);
    create(boundable);
  }

  // --------------------------------------------------------------------------------

  public List<Boundable> getBoundables(Rectangle r) {
    int arrayXBeg = (int) (r.getMinX() / areaW);
    int arrayYBeg = (int) (r.getMinY() / areaH);
    int arrayXEnd = (int) (r.getMaxX() / areaW);
    int arrayYEnd = (int) (r.getMaxY() / areaH);

    arrayXBeg = (arrayXBeg - 1) >= 0 ? arrayXBeg - 1 : arrayXBeg;
    arrayYBeg = (arrayYBeg - 1) >= 0 ? arrayYBeg - 1 : arrayYBeg;

    arrayXEnd = arrayXEnd + 1 < arrayW ? arrayXEnd + 1 : arrayXEnd;
    arrayYEnd = arrayYEnd + 1 < arrayH ? arrayYEnd + 1 : arrayYEnd;

    List<Boundable> ret = new ArrayList<Boundable>();

    for (int y = arrayYBeg; y <= arrayYEnd; y++) {
      for (int x = arrayXBeg; x <= arrayXEnd; x++) {
        List<Boundable> curr = (List<Boundable>) areaArray[x][y];
        if (curr != null) {
          ret.addAll(curr);
        }
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public List<Boundable> getArea(int x, int y, boolean create) {
    int arrayX = (int) (x / areaW);
    int arrayY = (int) (y / areaH);

    if (areaArray[arrayX][arrayY] == null && create) {
      areaArray[arrayX][arrayY] = new ArrayList<Boundable>();
    }

    return (List<Boundable>) areaArray[arrayX][arrayY];
  }
}
