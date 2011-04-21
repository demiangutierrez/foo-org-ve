package org.cyrano.pacman.base;

import java.util.ArrayList;
import java.util.List;

public class LevelExec {

  private List<BaseSprite> dynaList = new ArrayList<BaseSprite>(); // Dyna Beans: Lay 3
  private List<BaseSprite> sta2List = new ArrayList<BaseSprite>(); // Stat Beans: Lay 2
  private List<BaseSprite> sta1List = new ArrayList<BaseSprite>(); // Stat Beans: Lay 1

  private BaseSprite/**/[][] dyna; // Dyna Sprite: Lay 3
  private BaseSprite/**/[][] sta2; // Base Sprite: Lay 2
  private BaseSprite/**/[][] sta1; // Base Sprite: Lay 1

  private BaseSprite playSprite;

  private LevelLoad levelLoad;

  // --------------------------------------------------------------------------------

  public LevelExec(LevelLoad levelLoad) {
    this.levelLoad = levelLoad;

    dyna = new BaseSprite[levelLoad.getW()][levelLoad.getH()];
    sta1 = new BaseSprite[levelLoad.getW()][levelLoad.getH()];
    sta2 = new BaseSprite[levelLoad.getW()][levelLoad.getH()];
  }

  // --------------------------------------------------------------------------------

  public void init() {
    dynaList.clear();
    sta2List.clear();
    sta1List.clear();

    for (int y = 0; y < levelLoad.getH(); y++) {
      for (int x = 0; x < levelLoad.getW(); x++) {
        dyna[x][y] = null;
        sta2[x][y] = null;
        sta1[x][y] = null;
      }
    }

    playSprite = levelLoad.getPlayBean().init(this);

    initLayer(levelLoad.getDynaList(), dyna, dynaList);
    initLayer(levelLoad.getSta2List(), sta2, sta2List);
    initLayer(levelLoad.getSta1List(), sta1, sta1List);
  }

  // --------------------------------------------------------------------------------

  private void initLayer(List<BaseBean> baseBeanList, //
      BaseSprite[][] baseSpriteArray, List<BaseSprite> baseSpriteList) {

    for (BaseBean baseBean : baseBeanList) {
      BaseSprite baseSprite = baseBean.init(this);
      baseSpriteList.add(baseSprite);

      if (baseSpriteArray[baseBean.getX()][baseBean.getY()] != null) {
        throw new IllegalStateException( //
            "baseSpriteArray[baseBean.getX()][baseBean.getY()] != null");
      }

      baseSpriteArray[baseBean.getX()][baseBean.getY()] = baseSprite;
    }
  }

  // --------------------------------------------------------------------------------

  public List<BaseSprite> getDynaList() {
    return dynaList;
  }

  public List<BaseSprite> getSta2List() {
    return sta2List;
  }

  public List<BaseSprite> getSta1List() {
    return sta1List;
  }

  // --------------------------------------------------------------------------------

  public BaseSprite[][] getDyna() {
    return dyna;
  }

  public BaseSprite[][] getSta2() {
    return sta2;
  }

  public BaseSprite[][] getSta1() {
    return sta1;
  }

  // --------------------------------------------------------------------------------

  public char[][] getData() {
    return levelLoad.getData();
  }

  // --------------------------------------------------------------------------------

  public BaseSprite getPlaySprite() {
    return playSprite;
  }
}
