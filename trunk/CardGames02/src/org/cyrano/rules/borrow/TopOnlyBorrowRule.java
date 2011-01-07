package org.cyrano.rules.borrow;

import org.cyrano.common.BorrowRule;
import org.cyrano.objects.Stack;

public class TopOnlyBorrowRule implements BorrowRule {

  @Override
  public boolean borrowCards(Stack src, int index) {

    if (src.getTopCard() == src.getCardList().get(index)) {
      return true;
    }

    return false;
  }
}
