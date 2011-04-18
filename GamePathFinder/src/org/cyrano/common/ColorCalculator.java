package org.cyrano.common;

import java.awt.Color;

public class ColorCalculator {

  private double dR;
  private double dG;
  private double dB;

  // --------------------------------------------------------------------------------

  public ColorCalculator() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void init(int w, int h) {
    dR = 1.0 / w;
    dG = 1.0 / h;
    dB = 1.0 / (w * h);
  }

  // --------------------------------------------------------------------------------

  public Color calculate(int x, int y) {
    double r = x * dR;
    double g = y * dG;

    double b = 0.5f;

    return new Color( //
        (float) r, (float) g, (float) b);
  }
}
