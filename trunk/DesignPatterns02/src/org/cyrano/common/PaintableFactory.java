package org.cyrano.common;

// Factory
public interface PaintableFactory {

  public Paintable create(int x1, int y1, int x2, int y2);
}
