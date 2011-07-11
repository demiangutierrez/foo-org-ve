package org.cyrano.pacman.game;

import java.io.IOException;

import org.cyrano.pacman.base.BaseBean;
import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.pacman.base.SpriteMatrix;
import org.cyrano.util.misc.ImageCache;

public class TeleportPadSprite extends BaseSprite {

  private int tgtX;
  private int tgtY;

  // --------------------------------------------------------------------------------

  public TeleportPadSprite() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void loadImgs() throws IOException {
    bimgList.add(ImageCache.getInstance().getImage("tport1.png"));
    bimgList.add(ImageCache.getInstance().getImage("tport2.png"));
    bimgList.add(ImageCache.getInstance().getImage("tport3.png"));
    bimgList.add(ImageCache.getInstance().getImage("tport4.png"));
    bimgList.add(ImageCache.getInstance().getImage("tport5.png"));
    bimgList.add(ImageCache.getInstance().getImage("tport6.png"));
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
    tgtX = Integer.parseInt(parmArray[0]);
    tgtY = Integer.parseInt(parmArray[1]);

    init(baseBean.getX(), baseBean.getY(), speed, levelExec);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void execStepOn(BaseSprite source) {
    SpriteMatrix spriteMatrix = levelExec.getSpriteMatrix();

    spriteMatrix.del(source.getGrdCurr().x, source.getGrdCurr().y, source);
    source.init(tgtX, tgtY);
    spriteMatrix.add(source.getGrdCurr().x, source.getGrdCurr().y, source);
  }
}
