package boxpush4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cyrano.util.PointDbl;
import org.cyrano.util.PointInt;

import boxpush1.MultiPanel;

public class Sprite implements Box {

  protected PointDbl scrCurr = new PointDbl();
  protected PointDbl scrNext = new PointDbl();
  protected PointDbl scrLook = new PointDbl();

  protected PointInt grdCurr = new PointInt();
  protected PointInt grdNext = new PointInt();

  protected char mapChar;

  protected int grdW;
  protected int grdH;

  protected Map<Character, Sprite> spriteMap;

  protected TextMap textMap;

  protected int direction;

  protected Color color;

  // --------------------------------------------------------------------------------

  protected Set<Sprite> spritePushSet = new HashSet<Sprite>();

  protected int speed = 75;

  protected ScrollInfo scrollInfo;

  // --------------------------------------------------------------------------------

  public Sprite() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public char getMapChar() {
    return mapChar;
  }

  public void setMapChar(char mapChar) {
    this.mapChar = mapChar;
  }

  // --------------------------------------------------------------------------------

  public int getGrdW() {
    return grdW;
  }

  public void setGrdW(int grdW) {
    this.grdW = grdW;
  }

  // --------------------------------------------------------------------------------

  public int getGrdH() {
    return grdH;
  }

  public void setGrdH(int grdH) {
    this.grdH = grdH;
  }

  // --------------------------------------------------------------------------------

  public Map<Character, Sprite> getSpriteMap() {
    return spriteMap;
  }

  public void setSpriteMap(Map<Character, Sprite> spriteMap) {
    this.spriteMap = spriteMap;
  }

  // --------------------------------------------------------------------------------

  public TextMap getTextMap() {
    return textMap;
  }

  public void setTextMap(TextMap textMap) {
    this.textMap = textMap;
  }

  // --------------------------------------------------------------------------------

  public synchronized int getDirection() {
    return direction;
  }

  public synchronized void setDirection(int direction) {
    this.direction = direction;
  }

  // --------------------------------------------------------------------------------

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  // --------------------------------------------------------------------------------

  public void setGrdX(int x) {
    grdCurr.x = x;
    grdNext.x = x;
    scrCurr.x = x * MultiPanel.TILE_W;
    scrNext.x = x * MultiPanel.TILE_W;
  }

  public int getGrdX() {
    return grdCurr.x;
  }

  // --------------------------------------------------------------------------------

  public void setGrdY(int y) {
    grdCurr.y = y;
    grdNext.y = y;
    scrCurr.y = y * MultiPanel.TILE_H;
    scrNext.y = y * MultiPanel.TILE_H;
  }

  public int getGrdY() {
    return grdCurr.y;
  }

  // --------------------------------------------------------------------------------

  protected int minGrdX() {
    return grdCurr.x;
  }

  protected int minGrdY() {
    return grdCurr.y;
  }

  protected int maxGrdX() {
    return grdCurr.x + grdW - 1;
  }

  protected int maxGrdY() {
    return grdCurr.y + grdH - 1;
  }

  // --------------------------------------------------------------------------------

  public void initMap(char initChar, char chckChar) {
    for (int y = 0; y < grdH; y++) {
      for (int x = 0; x < grdW; x++) {
        if (textMap.getData()[grdCurr.x + x][grdCurr.y + y] == chckChar || //
            chckChar == Character.MAX_VALUE) {
          textMap.getData()[grdCurr.x + x][grdCurr.y + y] = initChar;
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics2D g2d) {
    for (int y = 0; y < grdH; y++) {
      for (int x = 0; x < grdW; x++) {
        g2d.setColor(color);
        g2d.fillRect(//
            (int) (scrCurr.x + x * MultiPanel.TILE_W), //
            (int) (scrCurr.y + y * MultiPanel.TILE_H), //
            MultiPanel.TILE_W, MultiPanel.TILE_H);
        g2d.setColor(Color.GREEN);
        g2d.drawRect(//
            (int) (scrCurr.x + x * MultiPanel.TILE_W), //
            (int) (scrCurr.y + y * MultiPanel.TILE_H), //
            MultiPanel.TILE_W, MultiPanel.TILE_H);
      }
    }
  }

  // --------------------------------------------------------------------------------

  protected boolean needToMove() {
    return scrCurr.x != scrNext.x || scrCurr.y != scrNext.y;
  }

  // --------------------------------------------------------------------------------

  public synchronized void updatePos(double dt) {

    if (!needToMove()) {
      if (direction == MultiPanel.NO) {
        return;
      }

      internalCalcNext(direction);

      if (!needToMove()) {
        return;
      }
    }

    for (Sprite pushedSprite : spritePushSet) {
      pushedSprite.updatePos(dt);
    }

    // ----------------------------------------

    while (dt > 0 && needToMove()) {
      double dst2go = speed * dt;

      double dstTgt = Math.sqrt( //
          /**/(scrCurr.x - scrNext.x) * (scrCurr.x - scrNext.x) + //
              (scrCurr.y - scrNext.y) * (scrCurr.y - scrNext.y));

      if (dstTgt == 0) {
        internalCalcNext(direction);

        for (Sprite pushedSprite : spritePushSet) {
          pushedSprite.updatePos(dt);
        }

        continue;
      }

      if (dstTgt < dst2go) {
        dt -= dstTgt / speed;

        scrCurr.x = scrNext.x;
        scrCurr.y = scrNext.y;

        if (scrollInfo != null) {
          scrollInfo.updateScrollInfo(this);
        }

        initMap(' ', mapChar);

        grdCurr.x = grdNext.x;
        grdCurr.y = grdNext.y;

        initMap(mapChar, Character.MAX_VALUE);

        internalCalcNext(direction);

        for (Sprite pushedSprite : spritePushSet) {
          pushedSprite.updatePos(dt);
        }
      } else {
        double dx = (scrNext.x - scrCurr.x) / dstTgt;
        double dy = (scrNext.y - scrCurr.y) / dstTgt;

        scrCurr.x += speed * dx * dt;
        scrCurr.y += speed * dy * dt;

        if (scrollInfo != null) {
          scrollInfo.updateScrollInfo(this);
        }

        dt = 0;
      }
    }
  }

  // --------------------------------------------------------------------------------

  protected void updateSpriteDirection(int direction2) {

    switch (direction2) {
      case MultiPanel.LF :
        grdNext.x = grdCurr.x - 1;
        grdNext.y = grdCurr.y;
        break;
      case MultiPanel.RG :
        grdNext.x = grdCurr.x + 1;
        grdNext.y = grdCurr.y;
        break;
      case MultiPanel.UP :
        grdNext.x = grdCurr.x;
        grdNext.y = grdCurr.y - 1;
        break;
      case MultiPanel.DW :
        grdNext.x = grdCurr.x;
        grdNext.y = grdCurr.y + 1;
        break;
      default :
        // throw new IllegalArgumentException();
    }

    scrCurr.x = grdCurr.x * MultiPanel.TILE_W;
    scrCurr.y = grdCurr.y * MultiPanel.TILE_H;

    if (scrollInfo != null) {
      scrollInfo.updateScrollInfo(this);
    }

    scrNext.x = grdNext.x * MultiPanel.TILE_W;
    scrNext.y = grdNext.y * MultiPanel.TILE_H;
  }

  // --------------------------------------------------------------------------------

  protected void updateSpritePushSetDirection(Set<Sprite> spritePushSet, int direction) {
    for (Sprite sprite : spritePushSet) {
      sprite.updateSpriteDirection(direction);
    }

    updateSpriteDirection(direction);
  }

  // --------------------------------------------------------------------------------

  protected boolean createSpritePushSet(Set<Sprite> spritePushSet, int direction) {
    switch (direction) {
      case MultiPanel.LF :
        for (int i = minGrdY(); i <= maxGrdY(); i++) {
          char nextChar = textMap.getData()[minGrdX() - 1][i];

          if (nextChar == 'X') {
            return false;
          }

          if (nextChar == ' ') {
            continue;
          }

          Sprite sprite = spriteMap.get(nextChar);
          spritePushSet.add(sprite);

          if (!sprite.createSpritePushSet(spritePushSet, direction)) {
            return false;
          }
        }
        break;
      case MultiPanel.RG :
        for (int i = minGrdY(); i <= maxGrdY(); i++) {
          char nextChar = textMap.getData()[maxGrdX() + 1][i];

          if (nextChar == 'X') {
            return false;
          }

          if (nextChar == ' ') {
            continue;
          }

          Sprite sprite = spriteMap.get(nextChar);
          spritePushSet.add(sprite);

          if (!sprite.createSpritePushSet(spritePushSet, direction)) {
            return false;
          }
        }
        break;
      case MultiPanel.UP :
        for (int i = minGrdX(); i <= maxGrdX(); i++) {
          char nextChar = textMap.getData()[i][minGrdY() - 1];

          if (nextChar == 'X') {
            return false;
          }

          if (nextChar == ' ') {
            continue;
          }

          Sprite sprite = spriteMap.get(nextChar);
          spritePushSet.add(sprite);

          if (!sprite.createSpritePushSet(spritePushSet, direction)) {
            return false;
          }
        }
        break;
      case MultiPanel.DW :
        for (int i = minGrdX(); i <= maxGrdX(); i++) {
          char nextChar = textMap.getData()[i][maxGrdY() + 1];

          if (nextChar == 'X') {
            return false;
          }

          if (nextChar == ' ') {
            continue;
          }

          Sprite sprite = spriteMap.get(nextChar);
          spritePushSet.add(sprite);

          if (!sprite.createSpritePushSet(spritePushSet, direction)) {
            return false;
          }
        }
        break;
      default :
        // throw new IllegalArgumentException();
    }

    return true;
  }

  // --------------------------------------------------------------------------------

  protected boolean internalCalcNext(int direction) {
    spritePushSet.clear();

    if (!createSpritePushSet(spritePushSet, direction)) {
      return false;
    }

    updateSpritePushSetDirection(spritePushSet, direction);

    return true;
  }

  // --------------------------------------------------------------------------------

  @Override
  public String toString() {
    return mapChar + "-->" + super.toString();
  }

  // --------------------------------------------------------------------------------
  // Box
  // --------------------------------------------------------------------------------

  @Override
  public int getW() {
    return grdW * MultiPanel.TILE_W;
  }

  @Override
  public int getH() {
    return grdW * MultiPanel.TILE_H;
  }

  @Override
  public int maxX() {
    return (int) (scrCurr.x + grdW * MultiPanel.TILE_W);
  }

  @Override
  public int maxY() {
    return (int) (scrCurr.y + grdH * MultiPanel.TILE_H);
  }

  @Override
  public int minX() {
    return (int) scrCurr.x;
  }

  @Override
  public int minY() {
    return (int) scrCurr.y;
  }

  @Override
  public int velX() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int velY() {
    throw new UnsupportedOperationException();
  }
}
