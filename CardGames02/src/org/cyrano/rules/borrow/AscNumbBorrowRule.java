package org.cyrano.rules.borrow;

import org.cyrano.common.BorrowRule;
import org.cyrano.objects.Stack;
import org.cyrano.objects.CardEnums.CardNumb;

public class AscNumbBorrowRule implements BorrowRule {

  @Override
  public boolean borrowCards(Stack src, int index) {
    int prevNumb = -1;
    int currNumb = -1;

    for (int i = index; i < src.getCardList().size(); i++) {
      if (prevNumb == -1) {
        prevNumb = CardNumb.cardNumbValToInt(src.getCardList().get(i).getCardNumb());
        continue;
      }

      currNumb = CardNumb.cardNumbValToInt(src.getCardList().get(i).getCardNumb());

      if (currNumb != prevNumb + 1) {
        return false;
      }

      prevNumb = currNumb;
    }

    return true;
  }
}
