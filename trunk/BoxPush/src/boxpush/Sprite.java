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
  protected PointDbl scrLook = new PointDbl();

  protected PointInt grdCurr = new PointInt();
  protected PointInt grdNext = new PointInt();

  protected char playChar;

  //  protected int grdW;
  //  protected int grdH;

  protected Map<Character, Sprite> spriteMap;

  protected TextMap textMap;

  protected TextMap playMap;

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

  //  public void setGrdW(int grdW) {
  //    this.grdW = grdW;
  //  }

  // --------------------------------------------------------------------------------

  public int getGrdH() {
    return playMap.getH();
  }

  //  public void setGrdH(int grdH) {
  //    this.grdH = grdH;
  //  }

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

  public TextMap getPlayMap() {
    return playMap;
  }

  public void setPlayMap(TextMap playMap) {
    this.playMap = playMap;
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

  public double internalUpdatePos(double dt) {
    double dst2go = speed * dt;

    double dstTgt = Math.sqrt( //
        /**/(scrCurr.x - scrNext.x) * (scrCurr.x - scrNext.x) + //
            (scrCurr.y - scrNext.y) * (scrCurr.y - scrNext.y));

    if (dstTgt < dst2go) {
      dt -= dstTgt / speed;

      scrCurr.x = scrNext.x;
      scrCurr.y = scrNext.y;

      if (scrollInfo != null) {
        scrollInfo.updateScrollInfo(this);
      }

      initMap(' ', playChar);

      grdCurr.x = grdNext.x;
      grdCurr.y = grdNext.y;

      initMap(playChar, Character.MAX_VALUE);
    } else {
      double dx = (scrNext.x - scrCurr.x) / dstTgt;
      double dy = (scrNext.y - scrCurr.y) / dstTgt;

      System.err.println(speed * dx * dt + ";" + speed * dy * dt);

      scrCurr.x += speed * dx * dt;
      scrCurr.y += speed * dy * dt;

      System.err.println(scrCurr);

      if (scrollInfo != null) {
        scrollInfo.updateScrollInfo(this);
      }

      dt = 0;
    }
    //    }
    return dt;

  }

  // --------------------------------------------------------------------------------

  public synchronized void updatePos(double dt) {
    do {
      if (!needToMove()) {
        internalCalcNext(direction);
      }

      System.err.println("************" + spritePushSet.size());

      if (spritePushSet.size() > 0) {
        System.err.println("jojojo");
      }

      if (needToMove()) {
        for (Sprite sprite : spritePushSet) {
          sprite.internalUpdatePos(dt);
        }

        dt = internalUpdatePos(dt);
      }
    } while (dt > 0 && needToMove());
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

    //    scrCurr.x = grdCurr.x * MultiPanel.TILE_W;
    //    scrCurr.y = grdCurr.y * MultiPanel.TILE_H;

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

  // XXX: I think DX, DY can be calculated from direction
  protected boolean createSpritePushSetXY( //
      Set<Sprite> spritePushSet, int direction, int dx, int dy) {

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

          if (!sprite.createSpritePushSet(spritePushSet, direction)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  // --------------------------------------------------------------------------------

  protected boolean createSpritePushSet(Set<Sprite> spritePushSet, int direction) {
    System.err.println("createSpritePushSet: " + playChar);
    switch (direction) {
      case MultiPanel.LF :
        return createSpritePushSetXY(spritePushSet, direction, -1, +0);
      case MultiPanel.RG :
        return createSpritePushSetXY(spritePushSet, direction, +1, +0);
      case MultiPanel.UP :
        return createSpritePushSetXY(spritePushSet, direction, +0, -1);
      case MultiPanel.DW :
        return createSpritePushSetXY(spritePushSet, direction, +0, +1);
      default :
        // throw new IllegalArgumentException();
    }

    return true;
  }

  // --------------------------------------------------------------------------------

  protected boolean internalCalcNext(int direction) {
    System.err.println(direction);
    spritePushSet.clear();

    if (!createSpritePushSet(spritePushSet, direction)) {
      return false;
    }

    updateSpritePushSetDirection(spritePushSet, direction);

    // ----------------------------------------------------
    // remove the player sprite to avoid infinite recursion 
    // ----------------------------------------------------

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
