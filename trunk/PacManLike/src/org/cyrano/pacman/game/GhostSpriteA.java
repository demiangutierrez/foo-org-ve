package org.cyrano.pacman.game;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.PathCalculator;
import org.cyrano.util.ImageCache;
import org.cyrano.util.PointInt;
import org.cyrano.util.game.Timer;

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
    char[][] data = levelExec.getData();

    PathCalculator pathCalculator = new PathCalculator( //
        data, levelExec.getW(), levelExec.getH(), false);

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

  // --------------------------------------------------------------------------------

  // logic here is akward, I mean, who steps on who? this is the target or
  // wootwoot (change variable name by the way) is the target? This handles
  // all or only char interactions 

  @Override
  public void execStepOn(BaseSprite wootwoot, Timer timer) {
    
    if (wootwoot instanceof PacManSprite) {
      if (destroy > 0) {
        dead = true;
      } else {
        PacManSprite pacManSprite = (PacManSprite) wootwoot;
        

        if (pacManSprite.destroy <= destroy) {
          actionListenerProxy.fireActionEvent(new ActionEvent(pacManSprite, 0, "die"));
        } else {
          actionListenerProxy.fireActionEvent(new ActionEvent(this, 0, "die"));
        }
        
      }
    }
    
    //    wootwoot.stepOn(this, timer); // XXX: This will cause problem if two pacmans!!!
  }

}
