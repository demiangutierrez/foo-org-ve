package org.cyrano.pacman.base;

import java.util.ArrayList;
import java.util.List;

public class LevelExec {

  private List<BaseSprite> spriteList = //
  new ArrayList<BaseSprite>();

  private LayerMatrix spriteMtrx;

  private BaseSprite playSprite;

  private LevelLoad levelLoad;

  // --------------------------------------------------------------------------------

  public LevelExec(LevelLoad levelLoad) {
    this.levelLoad = levelLoad;

    spriteMtrx = new LayerMatrix( //
        levelLoad.getW(), levelLoad.getH());
  }

  // --------------------------------------------------------------------------------

  public void init() {
    spriteList.clear();

    initLayer(levelLoad.getDynaList());
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
      spriteMtrx.add(baseBean.getX(), baseBean.getY(), baseSprite);
    }
  }

  // --------------------------------------------------------------------------------

  public List<BaseSprite> getDynaList() {
    return spriteList;
  }

  // --------------------------------------------------------------------------------

  public LayerMatrix getLayerArray() {
    return spriteMtrx;
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
}
