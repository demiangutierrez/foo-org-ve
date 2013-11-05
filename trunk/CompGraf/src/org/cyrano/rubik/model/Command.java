package org.cyrano.rubik.model;

public class Command {

  public double currTheta;

  public final Axis axis;

  public final Turn turn;

  public final int l;

  // --------------------------------------------------------------------------------

  public Command(Axis axis, Turn turn, int l) {
    this.axis = axis;
    this.turn = turn;

    this.l = l;
  }
}
