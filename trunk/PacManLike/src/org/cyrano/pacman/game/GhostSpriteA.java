package org.cyrano.pacman.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.pacman.pathfinder.PathCalculator;
import org.cyrano.util.ImageCache;
import org.cyrano.util.PointInt;

public class GhostSpriteA extends GhostSprite {

  private List<PointInt> pathList;

  // --------------------------------------------------------------------------------

  public GhostSpriteA() {
    // Empty
  }

  protected void loadImgs() throws IOException {
    super.loadImgs();

    xPacList = new ArrayList<BufferedImage>();
    xPacList.add(ImageCache.getInstance().getImage("xghsA1.png"));
    xPacList.add(ImageCache.getInstance().getImage("xghsA2.png"));
    xPacList.add(ImageCache.getInstance().getImage("xghsA3.png"));
    xPacList.add(ImageCache.getInstance().getImage("xghsA4.png"));
    xPacList.add(ImageCache.getInstance().getImage("xghsA5.png"));
    xPacList.add(ImageCache.getInstance().getImage("xghsA6.png"));

    bimgList = xPacList;
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

    PathCalculator pathCalculator = new PathCalculator( //
        new GhostSpriteESS(this), //
        levelExec.getW(), levelExec.getH(), false);

    PointInt src = new PointInt();
    src.x = grdCurr.x;
    src.y = grdCurr.y;

    PointInt tgt = new PointInt();
    tgt.x = levelExec.getPlaySprite().getGrdCurr().x;
    tgt.y = levelExec.getPlaySprite().getGrdCurr().y;

    pathList = pathCalculator.calculate(src, tgt);

    if (!pathList.isEmpty()) {
      grdNext = pathList.remove(0);
    }
  }
}
