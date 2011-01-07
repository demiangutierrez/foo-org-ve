package org.cyrano.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.common.Drawable;
import org.cyrano.main.Settings;

/**
 * @author Demián Gutierrez
 */
public class Stack implements Drawable {

  private static final int BASE_BORDER_GAP = 10;

  // --------------------------------------------------------------------------------

  private List<Card> cardList = new ArrayList<Card>();

  private Point coords = new Point();

  private int dx;
  private int dy;

  // --------------------------------------------------------------------------------

  public Stack(int cx, int cy, int dx, int dy) {
    coords.x/**/= cx;
    coords.y/**/= cy;
    this.dx/* */= dx;
    this.dy/* */= dy;
  }

  // --------------------------------------------------------------------------------

  public void updateCardList() {
    Card[] cardArray = cardList.toArray(new Card[0]);

    for (int i = 0; i < cardArray.length; i++) {
      cardArray[i].moveTo(coords.x + dx * i, coords.y + dy * i);
    }
  }

  // --------------------------------------------------------------------------------

  public List<Card> getCardList() {
    return cardList;
  }

  // --------------------------------------------------------------------------------
  // Drawable
  // --------------------------------------------------------------------------------

  public boolean contains(Point point) {
    return getRectangle().contains(point);
  }

  // --------------------------------------------------------------------------------

  public void draw(Graphics2D g2d) {
    if (!cardList.isEmpty()) {
      for (Drawable drawable : cardList) {
        drawable.draw(g2d);
      }
    } else {
      g2d.setColor(Color.GREEN.darker());
      g2d.drawRoundRect( //
          coords.x + /*       */BASE_BORDER_GAP, //
          coords.y + /*       */BASE_BORDER_GAP, //
          Settings.CARD_W - 2 * BASE_BORDER_GAP, //
          Settings.CARD_H - 2 * BASE_BORDER_GAP, //
          /*                  */BASE_BORDER_GAP, //
          /*                  */BASE_BORDER_GAP);
    }
  }

  // --------------------------------------------------------------------------------

  public void moveDt(int dx, int dy) {
    coords.x += dx;
    coords.y += dy;
    updateCardList();
  }

  public void moveTo(int cx, int cy) {
    coords.x = cx;
    coords.y = cy;
    updateCardList();
  }

  // --------------------------------------------------------------------------------

  @Override
  public Rectangle getRectangle() {
    Rectangle rect = new Rectangle(coords.x, coords.y, //
        Settings.CARD_W, Settings.CARD_H);

    for (Drawable drawable : cardList) {
      rect.add(drawable.getRectangle());
    }

    return rect;
  }

  // --------------------------------------------------------------------------------
  // borrow / return / accept stuff
  // --------------------------------------------------------------------------------

  public Stack/**/borrowCards(Point point) {
    Card[] cardArray = cardList.toArray(new Card[0]);

    int index = -1;

    for (int i = cardArray.length - 1; i >= 0; i--) {
      if (cardArray[i].contains(point)) {
        index = i;
        break;
      }
    }

    if (index == -1) {
      return null;
    }

    // ----------------------------------------

    Stack ret = new Stack( //
        cardArray[index].getRectangle().x, //
        cardArray[index].getRectangle().y, //
        dx, dy);

    for (int i = index; i < cardArray.length; i++) {
      cardList.remove(cardArray[i]);
      ret.getCardList().add(cardArray[i]);
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void/* */returnCards(Stack stack) {
    cardList.addAll(stack.getCardList());
    stack.getCardList().clear();
    updateCardList();
  }

  // --------------------------------------------------------------------------------

  public void/* */acceptCards(Stack stack) {
    cardList.addAll(stack.getCardList());
    stack.getCardList().clear();
    updateCardList();
  }
}
