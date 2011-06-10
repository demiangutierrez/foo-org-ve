package org.cyrano.pacman.game;

import java.util.List;

import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.LevelLoad;
import org.cyrano.pacman.base.PathCalculator;
import org.cyrano.util.PointInt;

public class GhostSpriteC extends GhostSprite {

  private List<PointInt> pathList;

  private int maxPoints = 5;
  private int curPoints;

  // --------------------------------------------------------------------------------

  public GhostSpriteC(int grdX, int grdY, int speed, LevelLoad textMap, BaseSprite tgtSprite) {
    super(grdX, grdY, speed, textMap, tgtSprite);
  }

  // --------------------------------------------------------------------------------

  protected void calcNext() {
    grdCurr.x = grdNext.x;
    grdCurr.y = grdNext.y;

    // TODO: Generalize this
    if (dead) {
      grdNext = grdCurr;
      return;
    }

    char[][] data = textMap.getData();

    if (pathList == null || pathList.isEmpty() || curPoints >= maxPoints) {
      PathCalculator pathCalculator = new PathCalculator( //
          data, textMap.getW(), textMap.getH(), false);

      PointInt src = new PointInt();
      src.x = grdCurr.x;
      src.y = grdCurr.y;

      PointInt tgt = new PointInt();
      tgt.x = tgtSprite.getGrdCurr().x;
      tgt.y = tgtSprite.getGrdCurr().y;

      pathList = pathCalculator.calculate(src, tgt);

      curPoints = 0;
    }

    if (!pathList.isEmpty()) {
      grdNext = pathList.remove(0);
      curPoints++;
    }
  }
}
