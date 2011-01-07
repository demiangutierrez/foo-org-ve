package org.cyrano.rules.borrow;

import org.cyrano.common.BorrowRule;
import org.cyrano.objects.Stack;

public class NoneBorrowRule implements BorrowRule {

  @Override
  public boolean borrowCards(Stack src, int index) {
    return false; // Dummy borrow no cards rule
  }
}
