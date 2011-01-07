package org.cyrano.common;

import org.cyrano.objects.Stack;

public interface BorrowRule {

  public boolean borrowCards(Stack src, int index);
}
