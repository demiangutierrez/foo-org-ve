package org.cyrano.objects;

import java.awt.Color;
import java.awt.event.MouseEvent;

import org.cyrano.objects.CardEnums.CardFace;

/**
 * @author Demián Gutierrez
 */
public class DeckStack extends Stack {

  private Stack target;

  private int step;

  // --------------------------------------------------------------------------------

  public DeckStack(int cx, int cy, int step, Stack target) {
    super(cx, cy, 0, 0);

    this.step/*  */= step;
    this.target/**/= target;

    baseBorderCol = Color.RED.darker();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void updateCards() {
    super.updateCards();

    Card[] cardArray = cardList.toArray(new Card[0]);

    int index = getShowCardListIndex();

    for (int i = 0; i + index < cardArray.length; i++) {
      cardArray[i + index].setCardFace(CardFace.DW);
    }
  }

  // --------------------------------------------------------------------------------
  // Drawable
  // --------------------------------------------------------------------------------

  private void mouseClickedIfEmpty(MouseEvent evt) {
    cardList.addAll(target.getCardList());
    target.getCardList().clear();
    updateCards();
  }

  // --------------------------------------------------------------------------------

  private void mouseClickedNtEmpty(MouseEvent evt) {
    for (int i = 0; i < step; i++) {
      if (cardList.isEmpty()) {
        break;
      }

      Card card = cardList.remove(0);
      card.setCardFace(CardFace.UP);

      target.getCardList().add(card);
    }

    target.updateCards();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseClicked(MouseEvent evt) {
    if (cardList.isEmpty()) {
      mouseClickedIfEmpty(evt);
    } else {
      mouseClickedNtEmpty(evt);
    }
  }

  // --------------------------------------------------------------------------------

  //  @Override
  //  public Stack borrowCards(Point point) {
  //    return null;
  //  }

  // --------------------------------------------------------------------------------

  //  @Override
  //  public boolean acceptCards(Stack stack) {
  //    return false;
  //  }
}
