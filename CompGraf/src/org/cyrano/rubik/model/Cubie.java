package org.cyrano.rubik.model;

import java.util.ArrayList;
import java.util.List;

public class Cubie {

  private static byte seqCubeId = 1;

  // --------------------------------------------------------------------------------

  private List<Facelet> faceletList = //
  new ArrayList<Facelet>();

  private byte cubeId;

  public int currAnimAngle = 0;

  // --------------------------------------------------------------------------------

  private VectorInt initialPosition = new VectorInt();

  private VectorInt currentPosition = new VectorInt();

  // --------------------------------------------------------------------------------

  private double[] currRotationMatrix = new double[]{ //
  /**/1, 0, 0, 0, //
      0, 1, 0, 0, //
      0, 0, 1, 0, //
      0, 0, 0, 1};

  private double[] animRotationMatrix = new double[]{ //
  /**/1, 0, 0, 0, //
      0, 1, 0, 0, //
      0, 0, 1, 0, //
      0, 0, 0, 1};

  // --------------------------------------------------------------------------------

  public Cubie() {
    cubeId = seqCubeId++;
  }

  // --------------------------------------------------------------------------------

  public byte getCubeId() {
    return cubeId;
  }

  // --------------------------------------------------------------------------------

  public List<Facelet> getFaceletList() {
    return faceletList;
  }

  // --------------------------------------------------------------------------------

  public VectorInt getInitialPosition() {
    return initialPosition;
  }

  public VectorInt getCurrentPosition() {
    return currentPosition;
  }

  // --------------------------------------------------------------------------------

  public double[] getCurrRotationMatrix() {
    return currRotationMatrix;
  }

  public void setCurrRotationMatrix(double[] currRotationMatrix) {
    this.currRotationMatrix = currRotationMatrix;
  }

  // --------------------------------------------------------------------------------

  public double[] getAnimRotationMatrix() {
    return animRotationMatrix;
  }

  public void setAnimRotationMatrix(double[] animRotationMatrix) {
    this.animRotationMatrix = animRotationMatrix;
  }
}
