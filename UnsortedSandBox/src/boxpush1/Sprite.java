package boxpush1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cyrano.util.PointDbl;
import org.cyrano.util.PointInt;

public class Sprite {

  protected PointDbl scrCurr = new PointDbl();
  protected PointDbl scrNext = new PointDbl();
  protected PointDbl scrLook = new PointDbl();

  protected PointInt grdCurr = new PointInt();
  protected PointInt grdNext = new PointInt();

  //  protected Sprite pushedSprite;
  protected Set<Sprite> pushedSpriteList = new HashSet<Sprite>();

  protected Map<Character, Sprite> spriteMap;

  protected TextMap textMap;

  protected char mapChar;

  protected int speed = 60;

  protected int w;
  protected int h;

  protected int direction;

  protected Color color;

  // --------------------------------------------------------------------------------

  public Sprite(int w, int h) {
    this.w = w;
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

  public char getMapChar() {
    return mapChar;
  }

  public void setMapChar(char mapChar) {
    this.mapChar = mapChar;
  }

  // --------------------------------------------------------------------------------

  public int getDirection() {
    return direction;
  }

  public void setDirection(int direction) {
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

  // --------------------------------------------------------------------------------

  public void setY(int y) {
    grdCurr.y = y;
    grdNext.y = y;
    scrCurr.y = y * MultiPanel.TILE_H;
    scrNext.y = y * MultiPanel.TILE_H;
  }

  // --------------------------------------------------------------------------------

  public void initMap(char initChar) {
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        textMap.getData()[grdCurr.x + x][grdCurr.y + y] = initChar;
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

  //  private boolean updatedFlag = false;
  //
  //  public void resetUpdateFlag() {
  //    updatedFlag = false;
  //    for (Sprite pushedSprite : pushedSpriteList) {
  //      pushedSprite.resetUpdateFlag();
  //    }
  //  }
  //  private long updateTimeMark = Long.MAX_VALUE;

  public void updatePos(double dt, long updateTimeMark) {
    //    if (this.updateTimeMark == updateTimeMark) {
    //      return;
    //    }
    //    this.updateTimeMark = updateTimeMark;

    //    if (updatedFlag) {
    //      return;
    //    }
    //    updatedFlag = true;

    if (!needToMove()) {
      internalCalcNext(direction);
      removeDuplicatedSprites(null);
    }

    //    if (pushedSprite != null) {
    //      pushedSprite.updatePos(dt);
    //    }
    if (!pushedSpriteList.isEmpty()) {
      for (Sprite pushedSprite : pushedSpriteList) {
        pushedSprite.updatePos(dt, updateTimeMark);
      }
    }

    while (dt > 0 && needToMove()) {
      double dst2go = speed * dt;

      double dstTgt = Math.sqrt( //
          /**/(scrCurr.x - scrNext.x) * (scrCurr.x - scrNext.x) + //
              (scrCurr.y - scrNext.y) * (scrCurr.y - scrNext.y));

      // ----------------------------------------
      // If I've reached the target...
      // Where do I go now?
      // ----------------------------------------
      if (dstTgt == 0) {
        internalCalcNext(direction);
        removeDuplicatedSprites(null);

        //        if (pushedSprite != null) {
        //          pushedSprite.updatePos(dt);
        //        }
        if (!pushedSpriteList.isEmpty()) {
          for (Sprite pushedSprite : pushedSpriteList) {
            pushedSprite.updatePos(dt, updateTimeMark);
          }
        }

        continue;
      }

      if (dstTgt < dst2go) {
        dt -= dstTgt / speed;

        scrCurr.x = scrNext.x;
        scrCurr.y = scrNext.y;

        initMap(' ');

        grdCurr.x = grdNext.x;
        grdCurr.y = grdNext.y;

        initMap(mapChar);

        // ----------------------------------------
        // If I've reached the target...
        // Where do I go now?
        // ----------------------------------------
        internalCalcNext(direction);
        removeDuplicatedSprites(null);

        //        if (pushedSprite != null) {
        //          pushedSprite.updatePos(dt);
        //        }
        if (!pushedSpriteList.isEmpty()) {
          for (Sprite pushedSprite : pushedSpriteList) {
            pushedSprite.updatePos(dt, updateTimeMark);
          }
        }

        // ----------------------------------------
        // What if I step on fire?
        // internalExecNext();
        // ----------------------------------------
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

  protected boolean checkForWallInSpriteList(Set<Sprite> spriteList) {
    for (Sprite sprite : spriteList) {
      // This is a hack, the problem here is that
      // walls don't have a sprite related (only
      // the X entry in the matrix

      //if (sprite.getMapChar() == 'X') {
      if (sprite == null) {
        return true;
      }
    }

    return false;
  }

  protected boolean internalCalcNext(int direction) {
    //    pushedSprite = null;
    pushedSpriteList.clear();

    if (direction == MultiPanel.NO) {
      grdNext.x = grdCurr.x;
      grdNext.y = grdCurr.y;

      scrCurr.x = grdCurr.x * MultiPanel.TILE_W;
      scrCurr.y = grdCurr.y * MultiPanel.TILE_H;

      scrNext.x = grdNext.x * MultiPanel.TILE_W;
      scrNext.y = grdNext.y * MultiPanel.TILE_H;

      return false;
    }

    boolean move;
    //char next = 'X';

    switch (direction) {
      // --------------------------------------------------------------------------------
      case MultiPanel.LF :
        move = true;

        for (int y = 0; y < h; y++) {
          if (textMap.getData()[grdCurr.x - 1][grdCurr.y + y] != ' ') {
            move = false;
            char next = textMap.getData()[grdCurr.x - 1][grdCurr.y + y];

            Sprite sprite = spriteMap.get(next);
            //pushedSprite = sprite;
            pushedSpriteList.add(sprite);
          }
        }

        if (move) {
          grdNext.x = grdCurr.x - 1;
          grdNext.y = grdCurr.y;
        } else {
          //if (next != 'X') {
          if (!checkForWallInSpriteList(pushedSpriteList)) {
            //            Sprite sprite = spriteMap.get(next);
            //            //pushedSprite = sprite;
            //            pushedSpriteList.add(sprite);

            boolean checkAllMove = true;
            for (Sprite sprite : pushedSpriteList) {
              if (!sprite.internalCalcNext(direction)) {
                checkAllMove = false;
                break;
              }
            }

            if (checkAllMove) {
              grdNext.x = grdCurr.x - 1;
              grdNext.y = grdCurr.y;
            } else {
              for (Sprite sprite : pushedSpriteList) {
                sprite.internalCalcNext(MultiPanel.NO);
              }
              pushedSpriteList.clear();
            }
          } else {
            pushedSpriteList.clear();
          }
        }
        break;

      // --------------------------------------------------------------------------------
      case MultiPanel.RG :
        move = true;

        for (int y = 0; y < h; y++) {
          if (textMap.getData()[grdCurr.x + w][grdCurr.y + y] != ' ') {
            move = false;
            char next = textMap.getData()[grdCurr.x + w][grdCurr.y + y];

            Sprite sprite = spriteMap.get(next);
            //pushedSprite = sprite;
            pushedSpriteList.add(sprite);
          }
        }

        if (move) {
          grdNext.x = grdCurr.x + 1;
          grdNext.y = grdCurr.y;
        } else {
          //if (next != 'X') {
          if (!checkForWallInSpriteList(pushedSpriteList)) {
            //            Sprite sprite = spriteMap.get(next);
            //            //pushedSprite = sprite;
            //            pushedSpriteList.add(sprite);

            boolean checkAllMove = true;
            for (Sprite sprite : pushedSpriteList) {
              if (!sprite.internalCalcNext(direction)) {
                checkAllMove = false;
                break;
              }
            }

            if (checkAllMove) {
              grdNext.x = grdCurr.x + 1;
              grdNext.y = grdCurr.y;
            } else {
              for (Sprite sprite : pushedSpriteList) {
                sprite.internalCalcNext(MultiPanel.NO);
              }
              pushedSpriteList.clear();
            }
          } else {
            pushedSpriteList.clear();
          }
        }
        break;

      // --------------------------------------------------------------------------------
      case MultiPanel.UP :
        move = true;
        for (int x = 0; x < w; x++) {
          if (textMap.getData()[grdCurr.x + x][grdCurr.y - 1] != ' ') {
            move = false;
            char next = textMap.getData()[grdCurr.x + x][grdCurr.y - 1];

            Sprite sprite = spriteMap.get(next);
            //pushedSprite = sprite;
            pushedSpriteList.add(sprite);
          }
        }

        if (move) {
          grdNext.x = grdCurr.x;
          grdNext.y = grdCurr.y - 1;
        } else {
          //if (next != 'X') {
          if (!checkForWallInSpriteList(pushedSpriteList)) {
            //            Sprite sprite = spriteMap.get(next);
            //            //pushedSprite = sprite;
            //            pushedSpriteList.add(sprite);

            boolean checkAllMove = true;
            for (Sprite sprite : pushedSpriteList) {
              if (!sprite.internalCalcNext(direction)) {
                checkAllMove = false;
                break;
              }
            }

            if (checkAllMove) {
              grdNext.x = grdCurr.x;
              grdNext.y = grdCurr.y - 1;
            } else {
              for (Sprite sprite : pushedSpriteList) {
                sprite.internalCalcNext(MultiPanel.NO);
              }
              pushedSpriteList.clear();
            }
          } else {
            pushedSpriteList.clear();
          }
        }
        break;

      // --------------------------------------------------------------------------------
      case MultiPanel.DW :
        move = true;
        for (int x = 0; x < w; x++) {
          if (textMap.getData()[grdCurr.x + x][grdCurr.y + h] != ' ') {
            move = false;
            char next = textMap.getData()[grdCurr.x + x][grdCurr.y + h];

            Sprite sprite = spriteMap.get(next);
            //pushedSprite = sprite;
            pushedSpriteList.add(sprite);
          }
        }

        if (move) {
          grdNext.x = grdCurr.x;
          grdNext.y = grdCurr.y + 1;
        } else {
          //if (next != 'X') {
          if (!checkForWallInSpriteList(pushedSpriteList)) {
            //            Sprite sprite = spriteMap.get(next);
            //            //pushedSprite = sprite;
            //            pushedSpriteList.add(sprite);

            boolean checkAllMove = true;
            for (Sprite sprite : pushedSpriteList) {
              if (!sprite.internalCalcNext(direction)) {
                checkAllMove = false;
                break;
              }
            }

            if (checkAllMove) {
              grdNext.x = grdCurr.x;
              grdNext.y = grdCurr.y + 1;
            } else {
              for (Sprite sprite : pushedSpriteList) {
                sprite.internalCalcNext(MultiPanel.NO);
              }
              pushedSpriteList.clear();
            }
          } else {
            pushedSpriteList.clear();
          }
        }

        break;
    }

    // --------------------------------------------------------------------------------

    scrCurr.x = grdCurr.x * MultiPanel.TILE_W;
    scrCurr.y = grdCurr.y * MultiPanel.TILE_H;

    scrNext.x = grdNext.x * MultiPanel.TILE_W;
    scrNext.y = grdNext.y * MultiPanel.TILE_H;

    return grdCurr.x != grdNext.x || grdCurr.y != grdNext.y;
  }

  protected void removeDuplicatedSprites(Set<Sprite> checkedSpriteSet) {
    if (checkedSpriteSet == null) {
      checkedSpriteSet = new HashSet<Sprite>();
    }

    Iterator<Sprite> itt = pushedSpriteList.iterator();

    while (itt.hasNext()) {
      Sprite sprite = (Sprite) itt.next();

      if (checkedSpriteSet.contains(sprite)) {
        itt.remove();
      } else {
        checkedSpriteSet.add(sprite);
      }

      sprite.removeDuplicatedSprites(checkedSpriteSet);
    }
  }
}
