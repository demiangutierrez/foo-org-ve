package org.cyrano.pacman.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Demián Gutierrez
 */
public class LayerMatrix {

  private List<BaseSprite>[][] array;

  // --------------------------------------------------------------------------------

  @SuppressWarnings("unchecked")
  public LayerMatrix(int w, int h) {
    array = new List[w][h];
  }

  // --------------------------------------------------------------------------------

  public void add(int x, int y, BaseSprite baseSprite) {
    if (array[x][y] == null) {
      array[x][y] = new ArrayList<BaseSprite>();
    }

    array[x][y].add(baseSprite);
  }

  // --------------------------------------------------------------------------------

  public void del(int x, int y, BaseSprite baseSprite) {
    if (array[x][y] == null || !array[x][y].contains(baseSprite)) {
      throw new IllegalArgumentException( //
          "array[x][y] == null || !array[x][y].contains(baseSprite)");
    }

    array[x][y].remove(baseSprite);

    if (array[x][y].isEmpty()) {
      array[x][y] = null;
    }
  }

  // --------------------------------------------------------------------------------

  public List<BaseSprite> get(int x, int y) {
    return array[x][y];
  }

  // --------------------------------------------------------------------------------

  public boolean testStepOn(int x, int y, BaseSprite baseSprite) {
    if (array[x][y] != null) {
      for (BaseSprite currSprite : array[x][y]) {
        if (!currSprite.testStepOn(baseSprite, null)) {
          return false;
        }
      }
    }

    return true;
  }

  // --------------------------------------------------------------------------------

  public boolean execStepOn(int x, int y, BaseSprite baseSprite) {
    if (array[x][y] == null) {
      for (BaseSprite currSprite : array[x][y]) {
        currSprite.execStepOn(baseSprite, null);
      }
    }

    return true;
  }
}
