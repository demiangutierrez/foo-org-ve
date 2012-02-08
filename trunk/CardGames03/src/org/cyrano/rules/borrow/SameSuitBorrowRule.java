package org.cyrano.rules.borrow;

import org.cyrano.common.BorrowRule;
import org.cyrano.objects.CardEnums.CardSuit;
import org.cyrano.objects.Stack;

public class SameSuitBorrowRule implements BorrowRule {

  @Override
  public boolean borrowCards(Stack src, int index) {
    CardSuit cardSuit = src.getCardList().get(index).getCardSuit();

    for (int i = index; i < src.getCardList().size(); i++) {
      if (src.getCardList().get(i).getCardSuit() != cardSuit) {
        return false;
      }
    }

    return true;
  }
}
