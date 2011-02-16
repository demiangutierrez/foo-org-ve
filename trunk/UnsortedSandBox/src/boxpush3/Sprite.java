package boxpush3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cyrano.util.PointDbl;
import org.cyrano.util.PointInt;

import boxpush1.MultiPanel;

public class Sprite {

  protected PointDbl scrCurr = new PointDbl();
  protected PointDbl scrNext = new PointDbl();
  protected PointDbl scrLook = new PointDbl();

  protected PointInt grdCurr = new PointInt();
  protected PointInt grdNext = new PointInt();

  protected char mapChar;

  protected int w;
  protected int h;

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

  public int getW() {
    return w;
  }

  public void setW(int w) {
    this.w = w;
  }

  // --------------------------------------------------------------------------------

  public int getH() {
    return h;
  }

  public void setH(int h) {
    this.h = h;
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

  public void setX(int x) {
    grdCurr.x = x;
    grdNext.x = x;
    scrCurr.x = x * MultiPanel.TILE_W;
    scrNext.x = x * MultiPanel.TILE_W;
  }

  public int getX() {
    return grdCurr.x;
  }

  // --------------------------------------------------------------------------------

  public void setY(int y) {
    grdCurr.y = y;
    grdNext.y = y;
    scrCurr.y = y * MultiPanel.TILE_H;
    scrNext.y = y * MultiPanel.TILE_H;
  }

  public int getY() {
    return grdCurr.y;
  }

  // --------------------------------------------------------------------------------

  protected int minX() {
    return grdCurr.x;
  }

  protected int minY() {
    return grdCurr.y;
  }

  protected int maxX() {
    return grdCurr.x + w - 1;
  }

  protected int maxY() {
    return grdCurr.y + h - 1;
  }

  // --------------------------------------------------------------------------------

  public void initMap(char initChar, char chckChar) {
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        if (textMap.getData()[grdCurr.x + x][grdCurr.y + y] == chckChar || //
            chckChar == Character.MAX_VALUE) {
          textMap.getData()[grdCurr.x + x][grdCurr.y + y] = initChar;
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics2D g2d) {
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
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
          scrollInfo.updateScrollInfo(scrCurr);
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
          scrollInfo.updateScrollInfo(scrCurr);
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
        //throw new IllegalArgumentException();
    }

    scrCurr.x = grdCurr.x * MultiPanel.TILE_W;
    scrCurr.y = grdCurr.y * MultiPanel.TILE_H;
    if (scrollInfo != null) {
      scrollInfo.updateScrollInfo(scrCurr);
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
        for (int i = minY(); i <= maxY(); i++) {
          char nextChar = textMap.getData()[minX() - 1][i];

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
        for (int i = minY(); i <= maxY(); i++) {
          char nextChar = textMap.getData()[maxX() + 1][i];

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
        for (int i = minX(); i <= maxX(); i++) {
          char nextChar = textMap.getData()[i][minY() - 1];

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
        for (int i = minX(); i <= maxX(); i++) {
          char nextChar = textMap.getData()[i][maxY() + 1];

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
        //throw new IllegalArgumentException();
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
}
