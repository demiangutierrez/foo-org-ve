package org.cyrano.pacman.game;

import java.util.List;

import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.PathCalculator;
import org.cyrano.pacman.base.LevelLoad;
import org.cyrano.util.base.PointInt;

public class GhostSpriteB extends GhostSprite {

  private List<PointInt> pathList;

  // --------------------------------------------------------------------------------

  public GhostSpriteB(int grdX, int grdY, int speed, LevelLoad textMap, BaseSprite tgtSprite) {
    super(grdX, grdY, speed, textMap, tgtSprite);
  }

  // --------------------------------------------------------------------------------

  protected void calcNext() {
    grdCurr.x = grdNext.x;
    grdCurr.y = grdNext.y;

    // TODO: Generalize this
    if (dead) {
      grdNext = grdCurr;
      pathList.clear();
      System.err.println("deadif: " + grdNext.x + ";" + grdNext.y + " - " + grdCurr.x + ";" + grdCurr.y);
      return;
    }

    char[][] data = textMap.getData();

    if (pathList == null || pathList.isEmpty()) {
      PathCalculator pathCalculator = new PathCalculator( //
          data, textMap.getW(), textMap.getH(), false);

      PointInt src = new PointInt();
      src.x = grdCurr.x;
      src.y = grdCurr.y;

      PointInt tgt = new PointInt();
      tgt.x = tgtSprite.getGrdCurr().x;
      tgt.y = tgtSprite.getGrdCurr().y;

      pathList = pathCalculator.calculate(src, tgt);
    }

    if (!pathList.isEmpty()) {
      grdNext = pathList.remove(0);
      if (grdNext.x == grdCurr.x && grdNext.y == grdCurr.y) {
        if (!pathList.isEmpty()) {
          grdNext = pathList.remove(0); // FIXME: Fucking patch!!!
        }
      }
    }
    System.err.println("normal: " + grdNext.x + ";" + grdNext.y + " - " + grdCurr.x + ";" + grdCurr.y);
  }
}
