package org.cyrano.pacman.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Demián Gutierrez
 */
public class SpriteMatrix {

  private List<BaseSprite>[][] spriteListMatrix;

  // --------------------------------------------------------------------------------

  @SuppressWarnings("unchecked")
  public SpriteMatrix(int w, int h) {
    spriteListMatrix = new List[w][h];
  }

  // --------------------------------------------------------------------------------

  public void add(int x, int y, BaseSprite baseSprite) {
    if (spriteListMatrix[x][y] == null) {
      spriteListMatrix[x][y] = new ArrayList<BaseSprite>();
    }

    spriteListMatrix[x][y].add(baseSprite);
  }

  // --------------------------------------------------------------------------------

  public void del(int x, int y, BaseSprite baseSprite) {
    if (/* */spriteListMatrix[x][y] == null || //
        /**/!spriteListMatrix[x][y].contains(baseSprite)) {
      throw new IllegalArgumentException();
    }

    spriteListMatrix[x][y].remove(baseSprite);

    if (spriteListMatrix[x][y].isEmpty()) {
      spriteListMatrix[x][y] = null;
    }
  }

  // --------------------------------------------------------------------------------

  public List<BaseSprite> get(int x, int y) {
    return spriteListMatrix[x][y];
  }

  // --------------------------------------------------------------------------------

  public boolean testStepOn(int x, int y, BaseSprite baseSprite) {
    if (spriteListMatrix[x][y] == null) {
      return true;
    }

    for (BaseSprite currSprite : spriteListMatrix[x][y]) {
      if (!currSprite.testStepOn(baseSprite)) {
        return false;
      }
    }

    return true;
  }

  // --------------------------------------------------------------------------------

  public boolean execStepOn(int x, int y, BaseSprite baseSprite) {
    if (spriteListMatrix[x][y] == null) {
      return true;
    }

    for (BaseSprite currSprite : spriteListMatrix[x][y]) {
      currSprite.execStepOn(baseSprite);
    }

    return true;
  }
}
