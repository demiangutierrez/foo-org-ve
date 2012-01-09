package org.cyrano.rubik.model;

import java.util.HashMap;
import java.util.Map;

public class Sparse<Type> {

  private Map<String, Type> typeByIndexMap = //
  new HashMap<String, Type>();

  // --------------------------------------------------------------------------------

  private final int xMin;
  private final int yMin;
  private final int zMin;
  private final int xMax;
  private final int yMax;
  private final int zMax;

  // --------------------------------------------------------------------------------

  public Sparse( //
      int xMin, int yMin, int zMin, //
      int xMax, int yMax, int zMax) {

    this.xMin = xMin;
    this.yMin = yMin;
    this.zMin = zMin;
    this.xMax = xMax;
    this.yMax = yMax;
    this.zMax = zMax;
  }

  // --------------------------------------------------------------------------------

  public Type get(int x, int y, int z) {
    checkIndices(x, y, z);

    return typeByIndexMap.get(calcHashString(x, y, z));
  }

  // --------------------------------------------------------------------------------

  public void set(int x, int y, int z, Type t) {
    checkIndices(x, y, z);

    typeByIndexMap.put(calcHashString(x, y, z), t);
  }

  // --------------------------------------------------------------------------------

  private void checkIndices(int x, int y, int z) {
    if (x < xMin || x > xMax) {
      throw new IndexOutOfBoundsException("x = " + x);
    }

    if (y < yMin || y > yMax) {
      throw new IndexOutOfBoundsException("y = " + y);
    }

    if (z < zMin || z > zMax) {
      throw new IndexOutOfBoundsException("z = " + z);
    }
  }

  // --------------------------------------------------------------------------------

  private String calcHashString(int x, int y, int z) {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(x);
    strbuf.append(";");
    strbuf.append(y);
    strbuf.append(";");
    strbuf.append(z);

    return strbuf.toString();
  }
}
