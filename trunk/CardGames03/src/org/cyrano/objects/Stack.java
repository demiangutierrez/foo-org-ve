package org.cyrano.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.common.AcceptRule;
import org.cyrano.common.BorrowRule;
import org.cyrano.common.Drawable;
import org.cyrano.main.Settings;

public class Stack implements Drawable {

  protected static final int BASE_BORDER_GAP = 10;

  // --------------------------------------------------------------------------------

  protected List<AcceptRule> acceptRuleList = new ArrayList<AcceptRule>();
  protected List<BorrowRule> borrowRuleList = new ArrayList<BorrowRule>();

  protected List<Card> cardList = new ArrayList<Card>();

  protected Point coords = new Point();

  protected int dx;
  protected int dy;

  // --------------------------------------------------------------------------------

  protected Stroke/**/baseBorderStr = new BasicStroke(3);
  protected Color/* */baseBorderCol = Color.GREEN.darker();

  protected int show;

  // --------------------------------------------------------------------------------

  public Stack(int cx, int cy, int dx, int dy) {
    coords.x/**/= cx;
    coords.y/**/= cy;
    this.dx/* */= dx;
    this.dy/* */= dy;
  }

  // --------------------------------------------------------------------------------

  protected int getShowCardListIndex() {
    int ret;

    if (show != 0) {
      ret = cardList.size() - show;

      if (ret < 0) {
        ret = 0;
      }
    } else {
      ret = 0;
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void addAcceptRule(AcceptRule acceptRule) {
    acceptRuleList.add(acceptRule);
  }

  // --------------------------------------------------------------------------------

  public void delAcceptRule(AcceptRule acceptRule) {
    acceptRuleList.remove(acceptRule);
  }

  // --------------------------------------------------------------------------------

  public AcceptRule[] getAcceptRuleArray() {
    return acceptRuleList.toArray(new AcceptRule[0]);
  }

  // --------------------------------------------------------------------------------

  public boolean testAcceptCards(Stack tmp) {
    for (AcceptRule acceptRule : acceptRuleList) {
      if (!acceptRule.acceptCards(this, tmp)) {
        return false;
      }
    }

    return true;
  }

  // --------------------------------------------------------------------------------

  public void addBorrowRule(BorrowRule borrowRule) {
    borrowRuleList.add(borrowRule);
  }

  // --------------------------------------------------------------------------------

  public void delBorrowRule(BorrowRule borrowRule) {
    borrowRuleList.remove(borrowRule);
  }

  // --------------------------------------------------------------------------------

  public boolean testBorrowCards(int index) {
    for (BorrowRule borrowRule : borrowRuleList) {
      if (!borrowRule.borrowCards(this, index)) {
        return false;
      }
    }

    return true;
  }

  // --------------------------------------------------------------------------------

  public void execAcceptCards(Stack tmp) {
    cardList.addAll(tmp.getCardList());
  }

  // --------------------------------------------------------------------------------

  public Stack execBorrowCards(int index) {
    Stack ret = new Stack( //
        cardList.get(index).getRectangle().x, //
        cardList.get(index).getRectangle().y, //
        dx, dy);

    List<Card> sublist = cardList.subList(index, cardList.size());

    ret.getCardList().addAll(sublist);

    sublist.clear();

    return ret;
  }

  // --------------------------------------------------------------------------------

  public int getCardIndexAt(Point point) {
    Card[] cardArray = cardList.toArray(new Card[0]);

    int index = -1;

    for (int i = cardArray.length - 1; i >= 0; i--) {
      if (cardArray[i].contains(point)) {
        index = i;
        break;
      }
    }

    return index;
  }

  // --------------------------------------------------------------------------------

  public void updateCards() {
    Card[] cardArray = cardList.toArray(new Card[0]);

    int index = getShowCardListIndex();

    for (int i = 0; i < cardArray.length; i++) {
      if (i < index) {
        cardArray[i].moveTo(coords.x, coords.y);
      } else {
        cardArray[i].moveTo(coords.x + dx * (i - index), coords.y + dy * (i - index));
      }
    }

    //    Card[] cardArray = cardList.toArray(new Card[0]);
    //
    //    for (int i = 0; i < cardArray.length; i++) {
    //      cardArray[i].moveTo(coords.x + dx * i, coords.y + dy * i);
    //    }
  }

  // --------------------------------------------------------------------------------

  public List<Card> getCardList() {
    return cardList;
  }

  // --------------------------------------------------------------------------------

  public int getShow() {
    return show;
  }

  public void setShow(int show) {
    this.show = show;
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
      g2d.setStroke/**/(baseBorderStr);
      g2d.setColor/* */(baseBorderCol);

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
    updateCards();
  }

  public void moveTo(int cx, int cy) {
    coords.x = cx;
    coords.y = cy;
    updateCards();
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

  //  public Stack/*  */testBorrowCards() {
  //  }
  //
  //  public Stack/*  */execBorrowCards(Point point) {
  //    Card[] cardArray = cardList.toArray(new Card[0]);
  //
  //    int index = -1;
  //
  //    for (int i = cardArray.length - 1; i >= 0; i--) {
  //      if (cardArray[i].contains(point)) {
  //        index = i;
  //        break;
  //      }
  //    }
  //
  //    if (index == -1) {
  //      return null;
  //    }
  //
  //    // ----------------------------------------
  //
  //    Stack ret = new Stack( //
  //        cardArray[index].getRectangle().x, //
  //        cardArray[index].getRectangle().y, //
  //        dx, dy);
  //
  //    for (int i = index; i < cardArray.length; i++) {
  //      cardList.remove(cardArray[i]);
  //      ret.getCardList().add(cardArray[i]);
  //    }
  //
  //    return ret;
  //  }

  // --------------------------------------------------------------------------------

  //  public void/*   */returnCards(Stack stack) {
  //    cardList.addAll(stack.getCardList());
  //    stack.getCardList().clear();
  //    updateCardList();
  //  }
  //
  //  // --------------------------------------------------------------------------------
  //
  //  public boolean/**/acceptCards(Stack stack) {
  //    cardList.addAll(stack.getCardList());
  //    stack.getCardList().clear();
  //    updateCardList();
  //
  //    return true;
  //  }

  // --------------------------------------------------------------------------------

  public Card getTopCard() {
    if (cardList.isEmpty()) {
      return null;
    }

    return cardList.get( //
        cardList.size() - 1);
  }

  // --------------------------------------------------------------------------------

  public Card getBotCard() {
    if (cardList.isEmpty()) {
      return null;
    }

    return cardList.get(0);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseClicked(MouseEvent evt) {
    // Empty
  }
}
