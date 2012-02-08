package org.cyrano.rules.accept;

import org.cyrano.common.AcceptRule;
import org.cyrano.objects.CardEnums.CardNumb;
import org.cyrano.objects.Stack;

public class AscNumbAcceptRule implements AcceptRule {

  @Override
  public boolean acceptCards(Stack tgt, Stack tmp) {
    if (tgt.getCardList().isEmpty()) {
      return true;
    }

    int tgtTop = CardNumb.cardNumbValToInt(tgt.getTopCard().getCardNumb());
    int tmpBot = CardNumb.cardNumbValToInt(tmp.getBotCard().getCardNumb());

    if (/**/tgtTop == //
    /*   */(tmpBot - 1)) {
      return true;
    }

    return false;
  }
}
