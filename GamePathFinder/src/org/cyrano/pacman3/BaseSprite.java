package org.cyrano.pacman3;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.cyrano.editor.ActionListenerProxy;

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

  protected DblPoint scrCurr;
  protected DblPoint scrNext;
  protected DblPoint scrLook;

  protected IntPoint grdCurr;
  protected IntPoint grdNext;

  protected TextMap textMap;

  protected int speed = 150;

  // --------------------------------------------------------------------------------

  protected double steps = 2;
  protected double delta;

  protected int index;

  // --------------------------------------------------------------------------------

  public BaseSprite(int grdX, int grdY, int speed, TextMap textMap) {
    scrCurr = new DblPoint();
    scrCurr.x = grdX * Constants.TILE_W;
    scrCurr.y = grdY * Constants.TILE_H;

    scrNext = new DblPoint();
    scrNext.x = grdX * Constants.TILE_W;
    scrNext.y = grdY * Constants.TILE_H;

    scrLook = new DblPoint();
    scrLook.x = grdX * Constants.TILE_W;
    scrLook.y = grdY * Constants.TILE_H;

    grdCurr = new IntPoint();
    grdCurr.x = grdX;
    grdCurr.y = grdY;

    grdNext = new IntPoint();
    grdNext.x = grdX;
    grdNext.y = grdY;

    this.speed = speed;

    this.textMap = textMap;

    try {
      loadImgs();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  // --------------------------------------------------------------------------------

  public ActionListenerProxy getActionListenerProxy() {
    return actionListenerProxy;
  }

  // --------------------------------------------------------------------------------

  public DblPoint getScrCurr() {
    return scrCurr;
  }

  // --------------------------------------------------------------------------------

  public IntPoint getGrdCurr() {
    return grdCurr;
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics2D g2d) {
    BufferedImage curr = bimgList.get(index % bimgList.size());

    // ---------------------------------------------------------------
    // Perform the rotation so x-axis is aligned with direction vector
    // ---------------------------------------------------------------

    AffineTransform prev = g2d.getTransform();

    AffineTransform at = AffineTransform.getRotateInstance( //
        scrLook.x - scrCurr.x, //
        scrLook.y - scrCurr.y, //
        scrCurr.x + Constants.SPRITE_W / 2, //
        scrCurr.y + Constants.SPRITE_H / 2);
    g2d.transform(at);

    // ---------------------------------------------------------------

    g2d.drawImage(curr, //
        (int) scrCurr.x, (int) scrCurr.y, //
        Constants.TILE_W, Constants.TILE_H, //
        null);

    g2d.setTransform(prev);
  }

  // --------------------------------------------------------------------------------

  public void updateSpr(double dt) {
    int st;

    delta += dt;

    st = (int) (delta * steps);
    delta = delta * steps - st;

    index += st;
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

        internalCalcNext();
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
    execNext();
  }

  // --------------------------------------------------------------------------------

  protected void execNext() {
    actionListenerProxy.fireActionEvent(new ActionEvent(this, 0, null));
  }

  // --------------------------------------------------------------------------------

  protected abstract void loadImgs() throws IOException;

  // --------------------------------------------------------------------------------

  protected abstract void calcNext();

  protected abstract void calcLook();
}
