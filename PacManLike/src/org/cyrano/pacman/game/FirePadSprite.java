package org.cyrano.pacman.game;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.pacman.base.BaseBean;
import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.util.ImageCache;

public class FirePadSprite extends BaseSprite {

  protected List<BufferedImage> fireAList;
  protected List<BufferedImage> fireBList;
  protected List<BufferedImage> fireCList;

  protected int state = 0; // 0 none, 1, 2, 3 fire and back to 0

  // --------------------------------------------------------------------------------

  public FirePadSprite() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void loadImgs() throws IOException {
    fireAList = new ArrayList<BufferedImage>();
    fireAList.add(ImageCache.getInstance().getImage("fireA1.png"));
    fireAList.add(ImageCache.getInstance().getImage("fireA2.png"));
    fireAList.add(ImageCache.getInstance().getImage("fireA3.png"));

    fireBList = new ArrayList<BufferedImage>();
    fireBList.add(ImageCache.getInstance().getImage("fireB1.png"));
    fireBList.add(ImageCache.getInstance().getImage("fireB2.png"));

    fireCList = new ArrayList<BufferedImage>();
    fireCList.add(ImageCache.getInstance().getImage("fireC1.png"));
    fireCList.add(ImageCache.getInstance().getImage("fireC2.png"));

    bimgList = null;
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void calcLook() {
    // Empty
  }

  @Override
  protected void calcNext() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void init(LevelExec levelExec, BaseBean baseBean, String[] parmArray) {
    state = Integer.parseInt(parmArray[0]);
    init(baseBean.getX(), baseBean.getY(), speed, levelExec);
  }

  // --------------------------------------------------------------------------------

  private double fireDelta;
  private double fireStepsPerSec = 1;

  @Override
  public void updatePos(double dt) {
    fireDelta += dt;

    double fireDeltaPerStp = 1 / fireStepsPerSec;

    while (fireDelta > fireDeltaPerStp) {
      fireDelta -= fireDeltaPerStp;

      state++;
    }

    switch (state % 8) {
      case 0 :
      case 1 :
      case 7 :
        bimgList = null;
        break;
      case 2 :
      case 6 :
        bimgList = fireAList;
        break;
      case 3 :
      case 5 :
        bimgList = fireBList;
        break;
      case 4 :
        bimgList = fireCList;
        break;
    }
  }

  @Override
  public void execStepOn(BaseSprite source) {
    if (state % 8 == 0 || state % 8 == 1 || state % 8 == 7) {
      return;
    }

    actionListenerProxy.fireActionEvent(new ActionEvent(source, 0, "die"));
  }
}
