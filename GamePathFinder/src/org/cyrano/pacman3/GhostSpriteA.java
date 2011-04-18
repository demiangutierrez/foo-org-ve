package org.cyrano.pacman3;

import java.util.List;

public class GhostSpriteA extends GhostSprite {

  private List<IntPoint> pathList;

  // --------------------------------------------------------------------------------

  public GhostSpriteA(int grdX, int grdY, int speed, TextMap textMap, BaseSprite tgtSprite) {
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

    PathCalculator pathCalculator = new PathCalculator( //
        data, textMap.getW(), textMap.getH(), false);

    IntPoint src = new IntPoint();
    src.x = grdCurr.x;
    src.y = grdCurr.y;

    IntPoint tgt = new IntPoint();
    tgt.x = tgtSprite.getGrdCurr().x;
    tgt.y = tgtSprite.getGrdCurr().y;

    pathList = pathCalculator.calculate(src, tgt);

    if (!pathList.isEmpty()) {
      grdNext = pathList.remove(0);
    }
  }
}
