package org.cyrano.swing.spline.bezier;

import java.awt.Color;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.cyrano.util.draw.CtrlPoint;

public class TriCtrlPoint extends CtrlPoint implements PropertyChangeListener {

  public enum PointMode {
    IND, LCK, SYN
  }

  // --------------------------------------------------------------------------------

  private CtrlPoint lftCtrlPoint;
  private CtrlPoint rghCtrlPoint;

  private PointMode mode;

  // --------------------------------------------------------------------------------

  @Override
  public void draw(Graphics2D g2d) {
    if (lftCtrlPoint != null) {
      lftCtrlPoint.draw(g2d);

      g2d.setColor(Color.CYAN);
      g2d.drawLine( //
          (int) x, //
          (int) y, //
          (int) lftCtrlPoint.getX(), //
          (int) lftCtrlPoint.getY());
    }

    if (rghCtrlPoint != null) {
      rghCtrlPoint.draw(g2d);

      g2d.setColor(Color.CYAN);
      g2d.drawLine( //
          (int) x, //
          (int) y, //
          (int) rghCtrlPoint.getX(), //
          (int) rghCtrlPoint.getY());
    }

    super.draw(g2d);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void setX(double x) {
    if (lftCtrlPoint != null) {
      lftCtrlPoint.setX(lftCtrlPoint.getX() + x - this.x, false);
    }

    if (rghCtrlPoint != null) {
      rghCtrlPoint.setX(rghCtrlPoint.getX() + x - this.x, false);
    }

    super.setX(x);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void setY(double y) {
    if (lftCtrlPoint != null) {
      lftCtrlPoint.setY(lftCtrlPoint.getY() + y - this.y, false);
    }

    if (rghCtrlPoint != null) {
      rghCtrlPoint.setY(rghCtrlPoint.getY() + y - this.y, false);
    }

    super.setY(y);
  }

  // --------------------------------------------------------------------------------

  public CtrlPoint contains(double x, double y) {
    if (lftCtrlPoint != null && lftCtrlPoint.contains(x, y) != null) {
      return lftCtrlPoint.contains(x, y);
    }

    if (rghCtrlPoint != null && rghCtrlPoint.contains(x, y) != null) {
      return rghCtrlPoint.contains(x, y);
    }

    return super.contains(x, y);
  }

  // --------------------------------------------------------------------------------

  public CtrlPoint getLftCtrlPoint() {
    return lftCtrlPoint;
  }

  public void setLftCtrlPoint(CtrlPoint lftCtrlPoint) {
    updatePropertyChangeListener(this.lftCtrlPoint, lftCtrlPoint);
    this.lftCtrlPoint = lftCtrlPoint;
  }

  // --------------------------------------------------------------------------------

  public CtrlPoint getRghCtrlPoint() {
    return rghCtrlPoint;
  }

  public void setRghCtrlPoint(CtrlPoint rghCtrlPoint) {
    updatePropertyChangeListener(this.rghCtrlPoint, rghCtrlPoint);
    this.rghCtrlPoint = rghCtrlPoint;
  }

  // --------------------------------------------------------------------------------

  public PointMode getMode() {
    return mode;
  }

  public void setMode(PointMode mode) {
    switch (mode) {
      case IND :
        setType(Type.OVAL);
        break;
      case LCK :
        setType(Type.STAR);
        break;
      case SYN :
        setType(Type.RECT);
        break;

      default :
        throw new IllegalArgumentException(mode.toString());
    }

    this.mode = mode;
  }

  // --------------------------------------------------------------------------------

  private void updatePropertyChangeListener(CtrlPoint oldPt, CtrlPoint newPt) {
    if (oldPt != null) {
      oldPt.getPropertyChangeSupport(). //
          removePropertyChangeListener(this);
    }

    if (newPt != null) {
      newPt.getPropertyChangeSupport(). //
          addPropertyChangeListener(this);
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (mode == PointMode.IND) {
      return;
    }

    CtrlPoint curr = (CtrlPoint) evt.getSource();
    CtrlPoint pair = (curr == lftCtrlPoint) //
        ? rghCtrlPoint
        : lftCtrlPoint;

    if (pair == null) {
      return;
    }

    switch (mode) {
      case LCK :
        updateLCK(evt, curr, pair);
        break;

      case SYN :
        updateSYN(evt, curr, pair);
        break;

      default :
        throw new IllegalStateException(mode.toString());
    }
  }

  // --------------------------------------------------------------------------------

  private void updateLCK(PropertyChangeEvent evt, CtrlPoint curr, CtrlPoint pair) {

    // ----------------------------------------
    // do the math (it's easy... really :)
    // ----------------------------------------

    /*   */if (evt.getPropertyName().equals("x")) {
      double xc = (Double) evt.getNewValue() - x;
      double yc = curr.getY() - y;
      double xp = pair.getX() - x;
      double yp = pair.getY() - y;

      double d = Math.sqrt(xp * xp + yp * yp);

      if (xc != 0) {
        xp = d * Math.sqrt(1 / ((yc * yc) / (xc * xc) + 1)) * Math.signum(xc) * -1;;
        yp = yc / xc * xp;
      } else {
        xp = 0;
        yp = d * Math.signum(yc) * -1;;
      }

      pair.setX(x + xp, false);
      pair.setY(y + yp, false);
    } else if (evt.getPropertyName().equals("y")) {
      double xc = curr.getX() - x;
      double yc = (Double) evt.getNewValue() - y;
      double xp = pair.getX() - x;
      double yp = pair.getY() - y;

      double d = Math.sqrt(xp * xp + yp * yp);

      if (yc != 0) {
        yp = d * Math.sqrt(1 / ((xc * xc) / (yc * yc) + 1)) * Math.signum(yc) * -1;
        xp = xc / yc * yp;
      } else {
        xp = d * Math.signum(xc) * -1;
        yp = 0;
      }

      pair.setX(x + xp, false);
      pair.setY(y + yp, false);
    } else {
      throw new IllegalArgumentException();
    }
  }

  // --------------------------------------------------------------------------------

  private void updateSYN(PropertyChangeEvent evt, CtrlPoint curr, CtrlPoint pair) {
    /*   */if (evt.getPropertyName().equals("x")) {
      double dx = (Double) evt.getNewValue() - x;
      pair.setX(x - dx, false);
    } else if (evt.getPropertyName().equals("y")) {
      double dy = (Double) evt.getNewValue() - y;
      pair.setY(y - dy, false);
    } else {
      throw new IllegalArgumentException();
    }
  }
}
