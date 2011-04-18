package org.cyrano.pacman2;

import java.util.List;

public class GhostSpriteC extends GhostSprite {

  private List<IntPoint> pathList;

  private int maxPoints = 5;
  private int curPoints;

  // --------------------------------------------------------------------------------

  public GhostSpriteC(int grdX, int grdY, int speed, TextMap textMap, BaseSprite tgtSprite) {
    super(grdX, grdY, speed, textMap, tgtSprite);
  }

  // --------------------------------------------------------------------------------

  protected void calcNext() {
    grdCurr.x = grdNext.x;
    grdCurr.y = grdNext.y;

    char[][] data = textMap.getData();

    if (pathList == null || pathList.isEmpty() || curPoints >= maxPoints) {
      PathCalculator pathCalculator = new PathCalculator( //
          data, textMap.getW(), textMap.getH(), false);

      IntPoint src = new IntPoint();
      src.x = grdCurr.x;
      src.y = grdCurr.y;

      IntPoint tgt = new IntPoint();
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
