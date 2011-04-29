package boxpush;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cyrano.util.PointDbl;
import org.cyrano.util.PointInt;

public class Sprite implements Box {

  protected PointDbl scrCurr = new PointDbl();
  protected PointDbl scrNext = new PointDbl();

  protected PointInt grdCurr = new PointInt();
  protected PointInt grdNext = new PointInt();

  protected Map<Character, Sprite> spriteMap;

  protected TextMap textMap;
  protected TextMap playMap;

  protected char playChar;

  protected int instDirection;

  protected Color color;

  // --------------------------------------------------------------------------------

  protected Set<Sprite> spritePushSet = new HashSet<Sprite>();

  protected ScrollInfo scrollInfo;

  protected int speed = 75;

  // --------------------------------------------------------------------------------

  public Sprite() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public char getPlayChar() {
    return playChar;
  }

  public void setPlayChar(char playChar) {
    this.playChar = playChar;
  }

  // --------------------------------------------------------------------------------

  public int getGrdW() {
    return playMap.getW();
  }

  // --------------------------------------------------------------------------------

  public int getGrdH() {
    return playMap.getH();
  }

  // --------------------------------------------------------------------------------

  public void setSpriteMap(Map<Character, Sprite> spriteMap) {
    this.spriteMap = spriteMap;
  }

  public void setTextMap(TextMap textMap) {
    this.textMap = textMap;
  }

  public void setPlayMap(TextMap playMap) {
    this.playMap = playMap;
  }

  // --------------------------------------------------------------------------------

  public synchronized void setInstDirection(int instDirection) {
    this.instDirection = instDirection;
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

  // --------------------------------------------------------------------------------

  public void setGrdY(int y) {
    grdCurr.y = y;
    grdNext.y = y;
    scrCurr.y = y * MultiPanel.TILE_H;
    scrNext.y = y * MultiPanel.TILE_H;
  }

  // --------------------------------------------------------------------------------

  protected int minGrdX() {
    return grdCurr.x;
  }

  protected int minGrdY() {
    return grdCurr.y;
  }

  protected int maxGrdX() {
    return grdCurr.x + getGrdW() - 1;
  }

  protected int maxGrdY() {
    return grdCurr.y + getGrdH() - 1;
  }

  // --------------------------------------------------------------------------------

  public void initMap(char initChar, char chckChar) {
    for (int y = 0; y < getGrdH(); y++) {
      for (int x = 0; x < getGrdW(); x++) {
        if (playMap.getData()[x][y] != playChar) {
          continue;
        }

        if (textMap.getData()[grdCurr.x + x][grdCurr.y + y] == chckChar || //
            chckChar == Character.MAX_VALUE) {
          textMap.getData()[grdCurr.x + x][grdCurr.y + y] = initChar;
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics2D g2d) {
    for (int y = 0; y < playMap.getH(); y++) {
      for (int x = 0; x < playMap.getW(); x++) {
        if (playMap.getData()[x][y] != playChar) {
          continue;
        }

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

  protected double internalUpdatePos(double dt) {
    double dst2go = speed * dt;

    double dstTgt = Math.sqrt( //
        /**/(scrCurr.x - scrNext.x) * (scrCurr.x - scrNext.x) + //
            (scrCurr.y - scrNext.y) * (scrCurr.y - scrNext.y));

    if (dstTgt < dst2go) {

      // ----------------------------------------
      // more time than distance
      // ----------------------------------------

      dt -= dstTgt / speed;

      scrCurr.x = scrNext.x;
      scrCurr.y = scrNext.y;

      initMap(' ', playChar);

      grdCurr.x = grdNext.x;
      grdCurr.y = grdNext.y;

      initMap(playChar, Character.MAX_VALUE);

    } else {

      // ----------------------------------------
      // less time than distance
      // ----------------------------------------

      double dx = (scrNext.x - scrCurr.x) / dstTgt;
      double dy = (scrNext.y - scrCurr.y) / dstTgt;

      scrCurr.x += speed * dx * dt;
      scrCurr.y += speed * dy * dt;

      dt = 0;
    }

    if (scrollInfo != null) {
      scrollInfo.updateScrollInfo(this);
    }

    return dt;
  }

  // --------------------------------------------------------------------------------

  public synchronized void updatePos(double dt) {
    do {
      if (needToMove()) {
        for (Sprite sprite : spritePushSet) {
          sprite.internalUpdatePos(dt);
        }

        dt = internalUpdatePos(dt);
      } else {
        internalCalcNext(instDirection);
      }
    } while (dt > 0 && needToMove());
  }

  // --------------------------------------------------------------------------------

  protected void updateSpriteDirection(int currDirection) {

    switch (currDirection) {
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

    if (scrollInfo != null) {
      scrollInfo.updateScrollInfo(this);
    }

    scrNext.x = grdNext.x * MultiPanel.TILE_W;
    scrNext.y = grdNext.y * MultiPanel.TILE_H;
  }

  // --------------------------------------------------------------------------------

  protected boolean createSpritePushSet(Set<Sprite> spritePushSet, int currDirection) {
    int dx = 0;
    int dy = 0;

    switch (currDirection) {
      case MultiPanel.LF :
        dx = -1;
        dy = +0;
        break;
      case MultiPanel.RG :
        dx = +1;
        dy = +0;
        break;
      case MultiPanel.UP :
        dx = +0;
        dy = -1;
        break;
      case MultiPanel.DW :
        dx = +0;
        dy = +1;
        break;
      default :
        // throw new IllegalArgumentException();
    }

    // ---------------------------------------------
    // goes through all the rectangle of this sprite
    // ---------------------------------------------

    for (int y = minGrdY(); y < minGrdY() + playMap.getH(); y++) {
      for (int x = minGrdX(); x < minGrdX() + playMap.getW(); x++) {

        char nextChar = textMap.getData()[x + dx][y + dy];
        char currChar = textMap.getData()[x/* */][y/* */];

        if (currChar != playChar) {
          continue;
        }

        if (nextChar == playChar) {
          continue;
        }

        if (nextChar == 'X') {
          return false;
        }

        if (nextChar == ' ') {
          continue;
        }

        Sprite sprite = spriteMap.get(nextChar);

        if (!spritePushSet.contains(sprite)) {
          spritePushSet.add(sprite);

          // ---------------------------------------------
          // recursively check the pushed sprite
          // ---------------------------------------------

          if (!sprite.createSpritePushSet(spritePushSet, currDirection)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  // --------------------------------------------------------------------------------

  protected boolean internalCalcNext(int currDirection) {
    spritePushSet.clear();

    if (!createSpritePushSet(spritePushSet, currDirection)) {
      return false;
    }

    for (Sprite sprite : spritePushSet) {
      sprite.updateSpriteDirection(currDirection);
    }

    updateSpriteDirection(currDirection);

    // ---------------------------------------------
    // remove the player to avoid infinite recursion 
    // ---------------------------------------------

    spritePushSet.remove(this);

    return true;
  }

  // --------------------------------------------------------------------------------

  @Override
  public String toString() {
    return playChar + "-->" + super.toString();
  }

  // --------------------------------------------------------------------------------
  // Box
  // --------------------------------------------------------------------------------

  @Override
  public int getW() {
    return getGrdW() * MultiPanel.TILE_W;
  }

  @Override
  public int getH() {
    return getGrdH() * MultiPanel.TILE_H;
  }

  @Override
  public int maxX() {
    return (int) (scrCurr.x + getGrdW() * MultiPanel.TILE_W);
  }

  @Override
  public int maxY() {
    return (int) (scrCurr.y + getGrdH() * MultiPanel.TILE_H);
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
