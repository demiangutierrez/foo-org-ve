package org.cyrano.pacman.base;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.cyrano.util.ActionListenerProxy;
import org.cyrano.util.PointDbl;
import org.cyrano.util.PointInt;

public abstract class BaseSprite {

  protected EventListenerList eventListenerList = //
  new EventListenerList();

  protected ActionListenerProxy actionListenerProxy = //
  new ActionListenerProxy(eventListenerList);

  // --------------------------------------------------------------------------------

  protected List<BufferedImage> bimgList = //
  new ArrayList<BufferedImage>();

  // --------------------------------------------------------------------------------

  protected boolean moving = false;

  protected PointDbl scrCurr;
  protected PointDbl scrNext;
  protected PointDbl scrLook;

  protected PointInt grdCurr;
  protected PointInt grdNext;

  protected LevelExec levelExec;

  public LevelExec getLevelExec() {
    return levelExec;
  }

  protected int speed = 150;

  protected boolean lookRotate = true;

  // --------------------------------------------------------------------------------

  protected double stepsPerSec = 16;
  protected double delta;

  protected int index;

  // --------------------------------------------------------------------------------

  public BaseSprite() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void init(int grdX, int grdY, int speed, LevelExec levelExec) {
    this.levelExec = levelExec;

    this.speed = speed;

    init(grdX, grdY);

    try {
      loadImgs();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void init(int grdX, int grdY) {
    scrCurr = new PointDbl();
    scrCurr.x = grdX * Constants.TILE_W;
    scrCurr.y = grdY * Constants.TILE_H;

    scrNext = new PointDbl();
    scrNext.x = grdX * Constants.TILE_W;
    scrNext.y = grdY * Constants.TILE_H;

    scrLook = new PointDbl();
    scrLook.x = grdX * Constants.TILE_W;
    scrLook.y = grdY * Constants.TILE_H;

    grdCurr = new PointInt();
    grdCurr.x = grdX;
    grdCurr.y = grdY;

    grdNext = new PointInt();
    grdNext.x = grdX;
    grdNext.y = grdY;
  }

  // --------------------------------------------------------------------------------

  public ActionListenerProxy getActionListenerProxy() {
    return actionListenerProxy;
  }

  // --------------------------------------------------------------------------------

  public PointDbl getScrCurr() {
    return scrCurr;
  }

  // --------------------------------------------------------------------------------

  public PointInt getGrdCurr() {
    return grdCurr;
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics2D g2d) {
    BufferedImage curr = bimgList.get(index % bimgList.size());

    // ---------------------------------------------------------------
    // Perform the rotation so x-axis is aligned with direction vector
    // ---------------------------------------------------------------

    AffineTransform prev = g2d.getTransform();

    if (lookRotate) {
      AffineTransform at = AffineTransform.getRotateInstance( //
          scrLook.x - scrCurr.x, //
          scrLook.y - scrCurr.y, //
          scrCurr.x + Constants.SPRITE_W / 2, //
          scrCurr.y + Constants.SPRITE_H / 2);
      g2d.transform(at);
    }

    // ---------------------------------------------------------------

    g2d.drawImage(curr, //
        (int) scrCurr.x, (int) scrCurr.y, //
        Constants.TILE_W, Constants.TILE_H, //
        null);

    g2d.setTransform(prev);
  }

  // --------------------------------------------------------------------------------

  public void updateSpr(double dt) {
    delta += dt;

    double deltaPerStp = 1 / stepsPerSec;

    while (delta > deltaPerStp) {
      index++;
      delta -= deltaPerStp;
    }
  }

  // --------------------------------------------------------------------------------

  public void updatePos(double dt) {
    if (!moving) {
      internalCalcNext();
    }

    while (dt > 0 && moving) {
      double dst2go = speed * dt;

      double dstTgt = Math.sqrt( //
          /**/(scrCurr.x - scrNext.x) * (scrCurr.x - scrNext.x) + //
              (scrCurr.y - scrNext.y) * (scrCurr.y - scrNext.y));

      if (dstTgt < dst2go) {
        dt -= dstTgt / speed;

        scrCurr.x = scrNext.x;
        scrCurr.y = scrNext.y;

        internalExecNext();
      } else {
        double dx = (scrNext.x - scrCurr.x) / dstTgt;
        double dy = (scrNext.y - scrCurr.y) / dstTgt;

        scrCurr.x += speed * dx * dt;
        scrCurr.y += speed * dy * dt;

        dt = 0;
      }
    }
  }

  // --------------------------------------------------------------------------------

  protected void internalCalcNext() {
    calcNext();
    calcLook();

    scrCurr.x = grdCurr.x * Constants.TILE_W;
    scrCurr.y = grdCurr.y * Constants.TILE_H;

    scrNext.x = grdNext.x * Constants.TILE_W;
    scrNext.y = grdNext.y * Constants.TILE_H;

    moving = grdCurr.x != grdNext.x || grdCurr.y != grdNext.y;
  }

  // --------------------------------------------------------------------------------

  protected void internalExecNext() {
    SpriteMatrix spriteMatrix = levelExec.getSpriteMatrix();

    spriteMatrix.del(grdCurr.x, grdCurr.y, this);
    internalCalcNext();
    spriteMatrix.add(grdCurr.x, grdCurr.y, this);

    execNext();
  }

  // --------------------------------------------------------------------------------

  protected void execNext() {
    actionListenerProxy.fireActionEvent(new ActionEvent(this, 0, null));
  }

  // --------------------------------------------------------------------------------

  public boolean isPlayer() {
    return false;
  }

  // --------------------------------------------------------------------------------

  public boolean testStepOn(BaseSprite source) {
    return true;
  }

  public void/**/execStepOn(BaseSprite source) {
    Interaction interaction = levelExec.get(this, source);

    if (interaction != null) {
      interaction.interact(this, source);
    }
  }

  // --------------------------------------------------------------------------------

  protected abstract void loadImgs() throws IOException;

  // --------------------------------------------------------------------------------

  protected abstract void calcNext();

  protected abstract void calcLook();

  // --------------------------------------------------------------------------------

  public abstract void init(LevelExec levelExec, BaseBean baseBean, String[] parmArray);
}
