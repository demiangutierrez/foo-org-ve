package boxcollision7;

import java.util.List;

import boxcollision7.CollisionDetector.CollisionInfo;

public class UniverseCollisionDetector {

  public static CollisionInfo calcTimeToCollide( //
      List<? extends Box> dynList, List<? extends Box> staList) {

    synchronized (dynList) {
      return syncCalcTimeToCollide(dynList, staList);
    }
  }

  // --------------------------------------------------------------------------------

  private static CollisionInfo syncCalcTimeToCollide( //
      List<? extends Box> dynList, List<? extends Box> staList) {

    CollisionInfo minCol = null;

    for (int i = 0; i < dynList.size(); i++) {
      for (int j = i + 1; j < dynList.size(); j++) {
        CollisionInfo curCol = new CollisionInfo( //
            dynList.get(i), dynList.get(j));

        CollisionDetector.calcTimeToCollide(curCol);

        if (minCol == null || curCol.time < minCol.time) {
          minCol = curCol;
        }
      }
    }

    for (int i = 0; i < dynList.size(); i++) {
      for (int j = 0; j < staList.size(); j++) {
        CollisionInfo curCol = new CollisionInfo( //
            dynList.get(i), staList.get(j));

        CollisionDetector.calcTimeToCollide(curCol);

        if (minCol == null || curCol.time < minCol.time) {
          minCol = curCol;
        }
      }
    }

    return minCol;
  }
}
