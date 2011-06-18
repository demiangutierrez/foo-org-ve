package org.cyrano.pacman.base;

import java.util.ArrayList;
import java.util.List;

import org.cyrano.util.game.Timer;

public class LevelExec {

  private LevelLoad levelLoad;

  private List<BaseSprite> spriteList = //
  new ArrayList<BaseSprite>();

  private SpriteMatrix spriteMatrix;

  private BaseSprite playSprite;

  private Timer timer;

  // --------------------------------------------------------------------------------

  public LevelExec(LevelLoad levelLoad, Timer timer) {
    this.levelLoad = levelLoad;
    this.timer = timer;

    spriteMatrix = new SpriteMatrix( //
        levelLoad.getW(), levelLoad.getH());
  }

  // --------------------------------------------------------------------------------

  public void init() {
    spriteList.clear();

    initLayer(levelLoad.getBaseBeanList());
  }

  // --------------------------------------------------------------------------------

  private void initLayer(List<BaseBean> baseBeanList) {
    for (BaseBean baseBean : baseBeanList) {
      BaseSprite baseSprite = baseBean.init(this);

      if (baseSprite.isPlayer()) {
        if (playSprite != null) {
          throw new IllegalStateException("playSprite != null");
        }

        playSprite = baseSprite;
      }

      spriteList.add(baseSprite);
      spriteMatrix.add(baseBean.getX(), baseBean.getY(), baseSprite);
    }
  }

  // --------------------------------------------------------------------------------

  public List<BaseSprite> getSpriteList() {
    return spriteList;
  }

  // --------------------------------------------------------------------------------

  public SpriteMatrix getSpriteMatrix() {
    return spriteMatrix;
  }

  // --------------------------------------------------------------------------------

  public char[][] getData() {
    return levelLoad.getData();
  }

  // --------------------------------------------------------------------------------

  public BaseSprite getPlaySprite() {
    return playSprite;
  }

  // --------------------------------------------------------------------------------

  public int getW() {
    return levelLoad.getW();
  }

  public int getH() {
    return levelLoad.getH();
  }

  // --------------------------------------------------------------------------------

  public Timer getTimer() {
    return timer;
  }
}
