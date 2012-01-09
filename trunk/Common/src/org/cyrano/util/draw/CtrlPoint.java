package org.cyrano.util.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.beans.PropertyChangeSupport;

public class CtrlPoint {

  public static final int DEFAULT_SIDE_SIZE = 10;

  // --------------------------------------------------------------------------------

  public enum Type {
    RECT, OVAL, STAR
  }

  // --------------------------------------------------------------------------------

  protected PropertyChangeSupport propertyChangeSupport = //
  new PropertyChangeSupport(this);

  // --------------------------------------------------------------------------------

  protected int size = DEFAULT_SIDE_SIZE;

  protected Type type = Type.RECT;

  protected boolean fixed;

  protected Color color;

  protected double x;
  protected double y;

  // --------------------------------------------------------------------------------

  public CtrlPoint() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public CtrlPoint contains(double x, double y) {
    if (x >= this.x - size / 2 && x <= this.x + size / 2 && //
        y >= this.y - size / 2 && y <= this.y + size / 2) {
      return this;
    }

    return null;
  }

  // --------------------------------------------------------------------------------

  public void draw(Graphics2D g2d) {

    if (color != null) {
      g2d.setColor(color);
    }

    switch (type) {
      case RECT :
        g2d.drawRect( //
            (int) (x - size / 2), //
            (int) (y - size / 2), //
            size, size);
        break;
      case OVAL :
        g2d.drawOval( //
            (int) (x - size / 2), //
            (int) (y - size / 2), //
            size, size);
        break;
      case STAR :
        g2d.drawLine(//
            (int) (x - size / 2), (int) (y - size / 2), //
            (int) (x + size / 2), (int) (y + size / 2));
        g2d.drawLine(//
            (int) (x - size / 2), (int) (y + size / 2), //
            (int) (x + size / 2), (int) (y - size / 2));

        g2d.drawLine(//
            (int) (x - size / 2), (int) y, //
            (int) (x + size / 2), (int) y);
        g2d.drawLine(//
            (int) x, (int) (y + size / 2), //
            (int) x, (int) (y - size / 2));
        break;
      default :
        throw new IllegalArgumentException(type.toString());
    }
  }

  // --------------------------------------------------------------------------------

  public double getX() {
    return x;
  }

  public void setX(double x) {
    setX(x, true);
  }

  public void setX(double x, boolean firePropertyChange) {
    if (firePropertyChange) {
      propertyChangeSupport.firePropertyChange( //
          "x", this.x, x);
    }

    this.x = x;
  }

  // --------------------------------------------------------------------------------

  public double getY() {
    return y;
  }

  public void setY(double y) {
    setY(y, true);
  }

  public void setY(double y, boolean firePropertyChange) {
    if (firePropertyChange) {
      propertyChangeSupport.firePropertyChange( //
          "y", this.y, y);
    }

    this.y = y;
  }

  // --------------------------------------------------------------------------------

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  // --------------------------------------------------------------------------------

  public boolean getFixed() {
    return fixed;
  }

  public void setFixed(boolean fixed) {
    this.fixed = fixed;
  }

  // --------------------------------------------------------------------------------

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  // --------------------------------------------------------------------------------

  public PropertyChangeSupport getPropertyChangeSupport() {
    return propertyChangeSupport;
  }
}
