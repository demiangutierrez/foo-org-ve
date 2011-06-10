package org.cyrano.pacman.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Sparse3DMatrix<T> {

  protected Map<String, Map<String, Map<String, T>>> xMap = //
  new HashMap<String, Map<String, Map<String, T>>>();

  // --------------------------------------------------------------------------------

  protected int w;
  protected int h;
  protected int d;

  // --------------------------------------------------------------------------------

  public Sparse3DMatrix(int w, int h, int d) {
    this.w = w;
    this.h = h;
    this.d = d;
  }

  // --------------------------------------------------------------------------------

  protected void checkXYZ(int x, int y, int z) {
    if (x < 0 || x >= w) {
      throw new IllegalArgumentException("x < 0 || x >= w");
    }

    if (y < 0 || y >= h) {
      throw new IllegalArgumentException("y < 0 || y >= h");
    }

    if (z < 0 || z >= d) {
      throw new IllegalArgumentException("z < 0 || z >= d");
    }
  }

  // --------------------------------------------------------------------------------

  public T getTAt(int x, int y, int z) {
    checkXYZ(x, y, z);

    Map<String, Map<String, T>> yMap = //
    xMap.get(Integer.toString(x));

    if (yMap == null) {
      return null;
    }

    Map<String, T> zMap = //
    yMap.get(Integer.toString(y));

    if (zMap == null) {
      return null;
    }

    return zMap.get(Integer.toString(z));
  }

  // --------------------------------------------------------------------------------

  public void delTAt(int x, int y, int z) {
    checkXYZ(x, y, z);

    Map<String, Map<String, T>> yMap = //
    xMap.get(Integer.toString(x));

    if (yMap == null) {
      return;
    }

    Map<String, T> zMap = //
    yMap.get(Integer.toString(y));

    if (zMap == null) {
      return;
    }

    zMap.remove(Integer.toString(z));

    if (!zMap.isEmpty()) {
      return;
    }

    yMap.remove(Integer.toString(y));

    if (!yMap.isEmpty()) {
      return;
    }

    xMap.remove(Integer.toString(x));
  }

  // --------------------------------------------------------------------------------

  public void setTAt(int x, int y, int z, T baseBean) {
    checkXYZ(x, y, z);

    Map<String, Map<String, T>> yMap = //
    xMap.get(Integer.toString(x));

    if (yMap == null) {
      xMap.put(Integer.toString(x), //
          yMap = new HashMap<String, Map<String, T>>());
    }

    Map<String, T> zMap = //
    yMap.get(Integer.toString(y));

    if (zMap == null) {
      yMap.put(Integer.toString(y), //
          zMap = new HashMap<String, T>());
    }

    zMap.put(Integer.toString(z), baseBean);
  }

  // --------------------------------------------------------------------------------

  public int getW() {
    return w;
  }

  public int getH() {
    return h;
  }

  public int getD() {
    return d;
  }

  // --------------------------------------------------------------------------------

  public Iterator<T> iterator() {
    return new Sparse3DMatrixIterator<T>(this);
  }

  // --------------------------------------------------------------------------------
  // Sparse3DMatrixIterator
  // --------------------------------------------------------------------------------

  private static class Sparse3DMatrixIterator<T> implements Iterator<T> {

    private Map<String, Map<String, Map<String, T>>> xMap;

    private Iterator<Map<String, Map<String, T>>>/**/xMapItt;
    private Iterator</*        */Map<String, T>>/* */yMapItt;
    private Iterator</*                    */T>/*  */zMapItt;

    // --------------------------------------------------------------------------------

    public Sparse3DMatrixIterator(Sparse3DMatrix<T> sparse3DMatrix) {
      this.xMap = sparse3DMatrix.xMap;
    }

    // --------------------------------------------------------------------------------

    private Iterator<Map<String, T>> getYMapItt() {
      if (xMapItt == null) {
        xMapItt = xMap.values().iterator();
      }

      if (!xMapItt.hasNext()) {
        return null;
      }

      return xMapItt.next().values().iterator();
    }

    // --------------------------------------------------------------------------------

    private Iterator<T> getZMapItt() {
      if (yMapItt == null || !yMapItt.hasNext()) {
        yMapItt = getYMapItt();
      }

      if (yMapItt == null) {
        return null;
      }

      return yMapItt.next().values().iterator();
    }

    // --------------------------------------------------------------------------------

    @Override
    public boolean hasNext() {
      if (zMapItt == null || !zMapItt.hasNext()) {
        zMapItt = getZMapItt();
      }

      if (zMapItt == null) {
        return false;
      }

      return true;
    }

    // --------------------------------------------------------------------------------

    @Override
    public T next() {
      if (zMapItt == null || !zMapItt.hasNext()) {
        zMapItt = getZMapItt();
      }

      if (zMapItt == null || !zMapItt.hasNext()) {
        throw new NoSuchElementException();
      }

      return zMapItt.next();
    }

    // --------------------------------------------------------------------------------

    @Override
    public void remove() {
      throw new NotImplementedException();
    }
  }

  //  public static void print(Sparse3DMatrix<String> s3dm) {
  //    for (int i = 0; i < 10; i++) {
  //      for (int j = 0; j < 10; j++) {
  //        System.err.print("(");
  //        for (int k = 0; k < 2; k++) {
  //          String s = s3dm.getTAt(i, j, k);
  //          System.err.print(s);
  //
  //          if (k != 1) {
  //            System.err.print("/");
  //          }
  //        }
  //        System.err.print(") ");
  //      }
  //      System.err.println();
  //    }
  //  }
  //
  //  public static void main(String[] args) {
  //    Sparse3DMatrix<String> s3dm = new Sparse3DMatrix<String>(10, 10, 2);
  //
  //    List<String> l = new ArrayList<String>();
  //
  //    NumberFormat nf = NumberFormat.getInstance();
  //    nf.setMinimumIntegerDigits(3);
  //
  //    int index = 0;
  //    for (int i = 0; i < 10; i++) {
  //      for (int j = 0; j < 10; j++) {
  //        for (int k = 0; k < 2; k++) {
  //          s3dm.setTAt(i, j, k, "" + nf.format(index));
  //          l.add("" + nf.format(index));
  //          index++;
  //        }
  //      }
  //    }
  //
  //    Iterator<String> itt = s3dm.iterator();
  //
  //    while (itt.hasNext()) {
  //      String string = (String) itt.next();
  //      System.err.println(string);
  //      if (!l.remove(string)) {
  //        throw new NoSuchElementException();
  //      }
  //    }
  //
  //    print(s3dm);
  //
  //    System.err.println(l.size());
  //  }
}
