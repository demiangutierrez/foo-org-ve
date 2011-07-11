package org.cyrano.pacman.base;

import java.util.ArrayList;
import java.util.List;

import org.cyrano.pacman.map.CharMap;
import org.cyrano.pacman.map.DynaMap;
import org.cyrano.pacman.map.InteMap;
import org.cyrano.util.game.Timer;
import org.cyrano.util.keyboard.InputManager;

public class LevelExec {

  // --------------------------------------------------------------------------------
  // Score counters 
  // --------------------------------------------------------------------------------

  private int score;

  private int sml;
  private int big;

  // --------------------------------------------------------------------------------

  private CharMap charMap;
  private DynaMap dynaMap;
  private InteMap inteMap;

  // --------------------------------------------------------------------------------

  private List<BaseSprite> spriteList = //
  new ArrayList<BaseSprite>();

  private SpriteMatrix spriteMatrix;

  private BaseSprite playSprite;

  private Timer timer;

  // XXX: should be here???
  private InputManager inputManager = new InputManager();

  // --------------------------------------------------------------------------------

  public LevelExec( //
      CharMap charMap, DynaMap dynaMap, InteMap inteMap, //
      Timer timer) {

    this.charMap = charMap;
    this.dynaMap = dynaMap;
    this.inteMap = inteMap;

    this.timer = timer;

    spriteMatrix = new SpriteMatrix( //
        charMap.getW(), charMap.getH());
  }

  // --------------------------------------------------------------------------------

  public void init() {
    spriteList.clear();

    initLayer(dynaMap.getBaseBeanList());
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

  public Interaction get(BaseSprite sprite1, BaseSprite sprite2) {
    return inteMap.get(sprite1, sprite2);
  }

  public Interaction get(BaseSprite sprite1, char tilekey) {
    return inteMap.get(sprite1, tilekey);
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
    return charMap.getData();
  }

  // --------------------------------------------------------------------------------

  public BaseSprite getPlaySprite() {
    return playSprite;
  }

  // --------------------------------------------------------------------------------

  public int getW() {
    return charMap.getW();
  }

  public int getH() {
    return charMap.getH();
  }

  // --------------------------------------------------------------------------------

  public Timer getTimer() {
    return timer;
  }

  // --------------------------------------------------------------------------------

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  // --------------------------------------------------------------------------------

  public int getSml() {
    return sml;
  }

  public void setSml(int sml) {
    this.sml = sml;
  }

  // --------------------------------------------------------------------------------

  public int getBig() {
    return big;
  }

  public void setBig(int big) {
    this.big = big;
  }

  // --------------------------------------------------------------------------------

  // XXX: should be here???
  public InputManager getInputManager() {
    return inputManager;
  }
}
