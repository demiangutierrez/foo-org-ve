package org.cyrano.rules.accept;

import org.cyrano.common.AcceptRule;
import org.cyrano.objects.Stack;
import org.cyrano.objects.CardEnums.CardNumb;

public class AceOnEmptyAcceptRule implements AcceptRule {

  @Override
  public boolean acceptCards(Stack tgt, Stack tmp) {
    if (!tgt.getCardList().isEmpty()) {
      return true;
    }

    if (tmp.getBotCard().getCardNumb() == CardNumb.N0A) {
      return true;
    }

    return false;
  }
}
