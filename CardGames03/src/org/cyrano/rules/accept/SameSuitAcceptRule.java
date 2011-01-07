package org.cyrano.rules.accept;

import org.cyrano.common.AcceptRule;
import org.cyrano.objects.Stack;

public class SameSuitAcceptRule implements AcceptRule {

  @Override
  public boolean acceptCards(Stack tgt, Stack tmp) {
    if (tgt.getCardList().isEmpty()) {
      return true;
    }

    if (tgt.getTopCard().getCardSuit() == //
    /**/tmp.getBotCard().getCardSuit()) {
      return true;
    }

    return false;
  }
}
