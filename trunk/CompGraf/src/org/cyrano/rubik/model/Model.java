package org.cyrano.rubik.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.cyrano.util.geometry.Matrix;
import org.cyrano.util.geometry.MatrixOps;

public class Model {

  private Sparse<Cubie> cube;

  private int n;

  // --------------------------------------------------------------------------------

  public Model(int n) {
    this.cube = new Sparse<Cubie>( //
        -n / 2, -n / 2, -n / 2, //
        +n / 2, +n / 2, +n / 2);

    this.n = n;

    build();
  }

  // --------------------------------------------------------------------------------

  public Sparse<Cubie> getCube() {
    return cube;
  }

  // --------------------------------------------------------------------------------

  public int getN() {
    return n;
  }

  // --------------------------------------------------------------------------------

  private void build() {
    byte idCount = 0;

    int beg = -n / 2;
    int end = +n / 2;

    for (int z = beg; z <= end; z++) {
      for (int y = beg; y <= end; y++) {
        for (int x = beg; x <= end; x++) {

          if (n % 2 == 0 && (x == 0 || y == 0 || z == 0)) {
            continue;
          }

          if (x != -n / 2 && x != +n / 2 && //
              y != -n / 2 && y != +n / 2 && //
              z != -n / 2 && z != +n / 2) {
            continue;
          }

          // ----------------------------------------

          Cubie cubie = new Cubie();

          cubie.getInitialPosition().x = x;
          cubie.getInitialPosition().y = y;
          cubie.getInitialPosition().z = z;

          cubie.getCurrentPosition().x = x;
          cubie.getCurrentPosition().y = y;
          cubie.getCurrentPosition().z = z;

          cube.set(x, y, z, cubie);

          // ----------------------------------------

          if (x == beg) {
            cubie.getFaceletList().add( //
                createFacelet(Axis.X_NEG, ++idCount));
          }

          if (x == end) {
            cubie.getFaceletList().add( //
                createFacelet(Axis.X_POS, ++idCount));
          }

          // ----------------------------------------

          if (y == beg) {
            cubie.getFaceletList().add( //
                createFacelet(Axis.Y_NEG, ++idCount));
          }

          if (y == end) {
            cubie.getFaceletList().add( //
                createFacelet(Axis.Y_POS, ++idCount));
          }

          // ----------------------------------------

          if (z == beg) {
            cubie.getFaceletList().add( //
                createFacelet(Axis.Z_NEG, ++idCount));
          }

          if (z == end) {
            cubie.getFaceletList().add( //
                createFacelet(Axis.Z_POS, ++idCount));
          }

          // ----------------------------------------
          // 1x1x1 requires 6 facelets
          // ----------------------------------------

          if (cubie.getFaceletList().size() == 0 || //
              cubie.getFaceletList().size() >= 7) {
            //cubie.faceletList.size() >= 4) {
            throw new IllegalStateException( //
                "facelet list size: " + cubie.getFaceletList().size());
          }
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  private Facelet createFacelet(Axis normal, byte id) {
    Facelet ret = new Facelet();

    int u[] = normal.unitVector();

    ret.normal.x = u[0];
    ret.normal.y = u[1];
    ret.normal.z = u[2];

    ret.faceletColor = FaceletColor.fromNormal( //
        ret.normal.x, ret.normal.y, ret.normal.z);

    ret.id = id;

    return ret;
  }

  // --------------------------------------------------------------------------------

  private void rotate(Axis axis, Turn turn, int l) {

    int beg = -n / 2;
    int end = +n / 2;

    List<Cubie> cubieList = new ArrayList<Cubie>();

    System.err.println("****************************************");

    for (int j = beg; j <= end; j++) {
      for (int i = beg; i <= end; i++) {

        if (n % 2 == 0 && //
            (i == 0 || j == 0 || 1 == 0)) {
          continue;
        }

        int[] coord = axis.trans(i, j, l);

        Cubie cubie = cube.get( //
            coord[0], coord[1], coord[2]);

        int[][] transform = Transforms.getTransformInt(axis, turn);

        cubie.getCurrentPosition().transform(transform);

        // ----------------------------------------

        System.err.println(num2(cubie.getCubeId(), 2) + ", (i,h,l), " //
            + num1(i/*   */) + ", " + num1(j/*   */) + ", " + num1(l/*   */));
        System.err.println(num2(cubie.getCubeId(), 2) + ", (0,1,2), " //
            + num1(coord[0]) + ", " + num1(coord[1]) + ", " + num1(coord[2]));

        System.err.println(num2(cubie.getCubeId(), 2) + ", (x,y,z), " //
            + num1(cubie.getCurrentPosition().x) + ", " //
            + num1(cubie.getCurrentPosition().y) + ", " //
            + num1(cubie.getCurrentPosition().z));

        System.err.println("---------------------------------------------------------------------");

        // ----------------------------------------
        Matrix mNewTransform = new Matrix(4, 4);
        mNewTransform.setData(transform);
        Matrix mCurTransform = new Matrix(4, 4);
        mCurTransform.setData(cubie.getCurrRotationMatrix());

        mCurTransform = MatrixOps.matrixMult( //
            mCurTransform, mNewTransform);

        cubie.setCurrRotationMatrix(mCurTransform.getData());
        cubie.setAnimRotationMatrix(mCurTransform.getData());
        // ----------------------------------------

        //        for (Facelet facelet : cubie.getFaceletList()) {
        //          facelet.normal.transform(transform);
        //        }

        cubieList.add(cubie);
      }
    }

    for (Cubie cubie : cubieList) {
      cube.set( //
          cubie.getCurrentPosition().x, //
          cubie.getCurrentPosition().y, //
          cubie.getCurrentPosition().z, //
          cubie);
    }
  }

  // --------------------------------------------------------------------------------

  private Queue<Command> commandQueue = new LinkedList<Command>();

  private Stack<Command> undoStack = new Stack<Command>();
  private Stack<Command> redoStack = new Stack<Command>();

  private double RADS_PER_SECOND = 3 * Math.PI / 2;

  public void undo() {
    commandQueue.clear();
    Command undo = undoStack.pop();
    commandQueue.add(undo);
    redoStack.add(undo);
  }

  public void redo() {
    commandQueue.clear();
    Command redo = redoStack.pop();
    commandQueue.add(redo);
    undoStack.add(redo);
  }

  public void animateCommand(long delta) {
    if (commandQueue.isEmpty()) {
      return;
    }

    Command command = commandQueue.peek();
    redoStack.clear();

    System.err.println("command was not null");

    double radsToAdd = delta * RADS_PER_SECOND / 1000;

    boolean end = false;

    if ((command.currTheta + radsToAdd) >= Math.PI / 2) {
      radsToAdd = Math.PI / 2 - command.currTheta;
      end = true;
    }

    rotateAnim(command.axis, command.turn, command.l, radsToAdd);

    command.currTheta += radsToAdd;

    if (end) {
      rotate(command.axis, command.turn, command.l);

      if (!commandQueue.isEmpty()) {
        undoStack.add(commandQueue.poll());
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void command(Command c) {
    commandQueue.add(c);
    //    if (command != null) {
    //      return;
    //    }
    //
    //    command = c;
  }

  // --------------------------------------------------------------------------------

  private void rotateAnim(Axis axis, Turn turn, int l, double radsToAdd) {

    int beg = -n / 2;
    int end = +n / 2;

    System.err.println("****************************************");

    for (int j = beg; j <= end; j++) {
      for (int i = beg; i <= end; i++) {

        if (n % 2 == 0 && //
            (i == 0 || j == 0 || 1 == 0)) {
          continue;
        }

        int[] coord = axis.trans(i, j, l);

        Cubie cubie = cube.get( //
            coord[0], coord[1], coord[2]);

        double[][] transform = Transforms.getTransformDbl(axis, turn, radsToAdd);

        // cubie.getCurrentPosition().transform(transform);

        // ----------------------------------------

        System.err.println(num2(cubie.getCubeId(), 2) + ", (i,h,l), " //
            + num1(i/*   */) + ", " + num1(j/*   */) + ", " + num1(l/*   */));
        System.err.println(num2(cubie.getCubeId(), 2) + ", (0,1,2), " //
            + num1(coord[0]) + ", " + num1(coord[1]) + ", " + num1(coord[2]));

        System.err.println(num2(cubie.getCubeId(), 2) + ", (x,y,z), " //
            + num1(cubie.getCurrentPosition().x) + ", " //
            + num1(cubie.getCurrentPosition().y) + ", " //
            + num1(cubie.getCurrentPosition().z));

        System.err.println("---------------------------------------------------------------------");

        // ----------------------------------------
        Matrix mNewTransform = new Matrix(4, 4);
        mNewTransform.setData(transform);
        Matrix mAnimTransform = new Matrix(4, 4);
        mAnimTransform.setData(cubie.getAnimRotationMatrix());

        mAnimTransform = MatrixOps.matrixMult( //
            mAnimTransform, mNewTransform);

        cubie.setAnimRotationMatrix(mAnimTransform.getData());
        // ----------------------------------------

        //        for (Facelet facelet : cubie.getFaceletList()) {
        //          facelet.normal.transform(transform);
        //        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  private String num1(double num) {
    if (num < 0) {
      return /*  */Double.toString(num);
    } else {
      return "+" + Double.toString(num);
    }
  }

  // --------------------------------------------------------------------------------

  private String num2(byte num, int fill) {
    String val = Byte.toString(num);

    for (int i = 0; i < fill - val.length(); i++) {
      val = "0" + val;
    }

    return val;
  }
}
