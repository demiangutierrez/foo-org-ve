package org.cyrano.rubik.model;

import java.util.ArrayList;
import java.util.List;

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
          cubie.position.x = x;
          cubie.position.y = y;
          cubie.position.z = z;

          cube.set(x, y, z, cubie);

          // ----------------------------------------

          if (x == beg) {
            cubie.faceletList.add( //
                createFacelet(Axis.X_NEG));
          }

          if (x == end) {
            cubie.faceletList.add( //
                createFacelet(Axis.X_POS));
          }

          // ----------------------------------------

          if (y == beg) {
            cubie.faceletList.add( //
                createFacelet(Axis.Y_NEG));
          }

          if (y == end) {
            cubie.faceletList.add( //
                createFacelet(Axis.Y_POS));
          }

          // ----------------------------------------

          if (z == beg) {
            cubie.faceletList.add( //
                createFacelet(Axis.Z_NEG));
          }

          if (z == end) {
            cubie.faceletList.add( //
                createFacelet(Axis.Z_POS));
          }

          // ----------------------------------------
          // 1x1x1 requires 6 facelets
          // ----------------------------------------

          if (cubie.faceletList.size() == 0 || //
              cubie.faceletList.size() >= 7) {
            //cubie.faceletList.size() >= 4) {
            throw new IllegalStateException( //
                "facelet list size: " + cubie.faceletList.size());
          }
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  private Facelet createFacelet(Axis normal) {
    Facelet ret = new Facelet();

    int u[] = normal.unitVector();

    ret.normal.x = u[0];
    ret.normal.y = u[1];
    ret.normal.z = u[2];

    ret.faceletColor = FaceletColor.fromNormal( //
        ret.normal.x, ret.normal.y, ret.normal.z);

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void rotate(Axis axis, Turn turn, int l) {

    int beg = -n / 2;
    int end = +n / 2;

    List<Cubie> cubieList = new ArrayList<Cubie>();

    for (int j = beg; j <= end; j++) {
      for (int i = beg; i <= end; i++) {

        if (n % 2 == 0 && //
            (i == 0 || j == 0 || 1 == 0)) {
          continue;
        }

        int[] coord = axis.trans(i, j, l);

        Cubie cubie = cube.get( //
            coord[0], coord[1], coord[2]);

        int[][] transform = Transforms.getTransform(axis, turn);

        cubie.position.transform(transform);

        for (Facelet facelet : cubie.faceletList) {
          facelet.normal.transform(transform);
        }

        cubieList.add(cubie);
      }
    }

    for (Cubie cubie : cubieList) {
      cube.set(cubie.position.x, cubie.position.y, cubie.position.z, cubie);
    }
  }
}
