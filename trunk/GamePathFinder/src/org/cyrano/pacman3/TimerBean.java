package org.cyrano.pacman3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerBean {

  private ActionListener actionListener;

  private ActionEvent actionEvent;

  private double delta;

  public TimerBean(ActionListener actionListener, ActionEvent actionEvent, double delta) {
    this.actionListener = actionListener;
    this.actionEvent/**/= actionEvent;

    this.delta = delta;
  }

  public ActionListener getActionListener() {
    return actionListener;
  }

  public ActionEvent getActionEvent() {
    return actionEvent;
  }

  public double getDelta() {
    return delta;
  }

  public void setDelta(double delta) {
    this.delta = delta;
  }
}
