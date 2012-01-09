package org.cyrano.util.geometry;

import java.text.NumberFormat;

/**
 * @author Demián Gutierrez
 */
public class Matrix {

  private double[] data;

  private int fs;
  private int cs;

  // --------------------------------------------------------------------------------

  public Matrix(int fs, int cs) {
    this.data = new double[fs * cs];

    this.fs = fs;
    this.cs = cs;
  }

  // --------------------------------------------------------------------------------

  public double[] getData() {
    return data;
  }

  public void setData(double[] data) {
    assert (data.length % cs == /* */0);
    assert (data.length / cs == /**/fs);

    this.data = data;
  }

  // --------------------------------------------------------------------------------

  public int gFs() {
    return fs;
  }

  public int gCs() {
    return cs;
  }

  // --------------------------------------------------------------------------------

  public void/*  */set(int f, int c, double v) {
    assert (f < fs && c < cs);

    /*   */data[cs * f + c] = v;
  }

  public double/**/get(int f, int c) {
    assert (f < fs && c < cs);

    return data[cs * f + c];
  }

  // --------------------------------------------------------------------------------

  @Override
  public String toString() {
    StringBuffer ret = new StringBuffer();

    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(4);
    nf.setMinimumFractionDigits(4);
    nf.setGroupingUsed(false);

    for (int f = 0; f < fs; f++) {
      for (int c = 0; c < cs; c++) {
        ret.append(nf.format(get(f, c)));
        ret.append(" ");
      }

      ret.append("\n");
    }

    return ret.toString();
  }
}
